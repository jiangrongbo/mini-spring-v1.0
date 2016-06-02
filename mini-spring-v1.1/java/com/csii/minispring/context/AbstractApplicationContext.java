package com.csii.minispring.context;

import java.util.Map;

import com.csii.minispring.beans.xml.BeanDefinitionParser;
import com.csii.minispring.beans.xml.BeanDefinitionReader;
import com.csii.minispring.exceptions.BeansException;
import com.csii.minispring.io.Resource;

public abstract class AbstractApplicationContext implements ApplicationContext {
	protected Resource configResource;
	Map beanDefinationMap = null;
	Map<String, Object> beanMap = null;

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
		// read bean definiton.
		BeanDefinitionReader reader = new BeanDefinitionReader(configResource);
		beanDefinationMap = reader.readBeanDefiniton();
	}

	public void parseBeanDefinitions() {
		// parse bean definiton, initialize bean.
		BeanDefinitionParser parser = new BeanDefinitionParser();
		beanMap = parser.parseBeanDefinitions(beanDefinationMap);
	}

	public Object getBean(String beanName) {
		Object bean = beanMap.get(beanName);
		if (bean == null) {
			throw new BeansException("bean not found, please check your config file.");
		}
		return bean;
	}
}
