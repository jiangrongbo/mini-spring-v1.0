package com.csii.minispring.beans.xml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.csii.minispring.beans.BeanDefinition;
import com.csii.minispring.beans.PropertyDefinition;
import com.csii.minispring.io.Resource;

/**
 * transfer bean config to BeanDefinition object.
 * 
 * @author CSII Rongbo
 * 
 */
public class BeanDefinitionReader {
	private Resource configResource;
	private HashMap<String, BeanDefinition> beanDefinitionMap;
	private BeansDtdResolver dtdResolver = null;
	private Log logger = LogFactory.getLog(BeanDefinitionReader.class);

	public Resource getConfigResource() {
		return configResource;
	}

	public void setConfigResource(Resource configResource) {
		this.configResource = configResource;
	}

	public BeanDefinitionReader(Resource configResource) {
		this.configResource = configResource;
		this.beanDefinitionMap = new HashMap<String, BeanDefinition>();
		this.dtdResolver = new BeansDtdResolver();
	}

	public Map readBeanDefiniton() {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(true);
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			//resolve dtd location
			dBuilder.setEntityResolver(dtdResolver);
			dBuilder.setErrorHandler(new ErrorHandler() {
				public void error(SAXParseException exception) throws SAXException {
					throw exception;
				}

				public void fatalError(SAXParseException exception) throws SAXException {
					throw exception;
				}

				public void warning(SAXParseException exception) throws SAXException {
					logger.warn(exception.getMessage());
				}
			});
			
			Document doc = dBuilder.parse(configResource.getFile());
			doc.getDocumentElement().normalize();
			NodeList nodeList = doc.getElementsByTagName("bean");
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				doLoadBeanDefinition(node);
			}
		} catch (SAXException e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage());
			}
		} catch (ParserConfigurationException e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage());
			}
		} catch (IOException e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage());
			}
		}
		
		return beanDefinitionMap;
	}

	private void doLoadBeanDefinition(Node node) {
		BeanDefinition beanDefinition = new BeanDefinition();
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) node;
			String idAttr = element.getAttribute("id");
			beanDefinition.setId(idAttr);
			beanDefinition.setClassName(element.getAttribute("class"));
			beanDefinition.setProperties(getProperties(element));
			beanDefinitionMap.put(idAttr, beanDefinition);
		}

	}

	private List getProperties(Element node) {

		List<PropertyDefinition> props = new ArrayList<PropertyDefinition>();
		NodeList nodeList = node.getElementsByTagName("property");
		for (int i = 0; i < nodeList.getLength(); i++) {
			PropertyDefinition prop = new PropertyDefinition();
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) nodeList.item(i);
				prop.setPropName(element.getAttribute("name"));
				String ref = element.getAttribute("ref");
				String value = element.getAttribute("value");
				if (ref != null && !"".equals(ref)) {
					prop.setRefOrValue("ref");
					prop.setPropValue(element.getAttribute("ref"));
				} else if (value != null && !"".equals(value)) {
					prop.setRefOrValue("value");
					prop.setPropValue(element.getAttribute("value"));
				}
			}
			props.add(prop);
		}
		return props;
	}
}
