package com.csii.minispring.context;

import java.util.HashMap;
import java.util.Map;

import com.csii.minispring.beans.xml.BeanDefinitionParser;
import com.csii.minispring.beans.xml.BeanDefinitionReader;
import com.csii.minispring.exceptions.BeansException;
import com.csii.minispring.io.Resource;

public abstract class AbstractApplicationContext implements ApplicationContext {
	protected Resource configResource;
	BeanDefinitionReader reader = null;
	BeanDefinitionParser parser = null;
	Map<String, Object> beanMap = new HashMap<String, Object>();

	public void refresh() {
		loadBeanDefinitions();
		parseBeanDefinitions();
	}

	public Resource getConfigResource() {
		return configResource;
	}

	public void setConfigResource(Resource configResource) {
		this.configResource = configResource;
	}

	public void loadBeanDefinitions() {
		reader = new BeanDefinitionReader(configResource);
		reader.readBeanDefiniton();
	}

	public void parseBeanDefinitions() {
		parser = new BeanDefinitionParser();
		parser.parseBeanDefinitions(beanMap, reader);
	}

	public Object getBean(String beanName) {
		Object bean = beanMap.get(beanName);
		if (bean == null) {
			throw new BeansException("bean not found, please check your config file.");
		}
		return bean;
	}
}
