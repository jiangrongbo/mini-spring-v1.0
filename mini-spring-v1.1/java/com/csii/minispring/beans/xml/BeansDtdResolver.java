package com.csii.minispring.beans.xml;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.csii.minispring.io.ClassPathResource;
import com.csii.minispring.io.Resource;

public class BeansDtdResolver implements EntityResolver {

	private static final String DTD_EXTENSION = ".dtd";

	private static final String[] DTD_NAMES = {"beans", "minispring-beans"};

	private static final String DTD_LOCATION = "com/csii/minispring/beans/xml";
	
	private static final Log logger = LogFactory.getLog(BeansDtdResolver.class);
	
	@Override
	public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
		if (logger.isTraceEnabled()) {
			logger.trace("Trying to resolve XML entity with public ID [" + publicId +
					"] and system ID [" + systemId + "]");
		}
		if (systemId != null && systemId.endsWith(DTD_EXTENSION)) {
			int lastPathSeparator = systemId.lastIndexOf("/");
			for (int i = 0; i < DTD_NAMES.length; ++i) {
				int dtdNameStart = systemId.indexOf(DTD_NAMES[i]);
				if (dtdNameStart > lastPathSeparator) {
					String dtdFile = DTD_LOCATION + "/" + systemId.substring(dtdNameStart);
					if (logger.isTraceEnabled()) {
						logger.trace("Trying to locate [" + dtdFile + "] in Spring jar");
					}
					try {
						Resource resource = new ClassPathResource(dtdFile);
						InputSource source = new InputSource(resource.getInputStream());
						source.setPublicId(publicId);
						source.setSystemId(systemId);
						if (logger.isDebugEnabled()) {
							logger.debug("Found beans DTD [" + systemId + "] in classpath: " + dtdFile);
						}
						return source;
					}
					catch (IOException ex) {
						if (logger.isDebugEnabled()) {
							logger.debug("Could not resolve beans DTD [" + systemId + "]: not found in class path", ex);
						}
					}
				
				}
			}
		}
		return null;
	}

}
