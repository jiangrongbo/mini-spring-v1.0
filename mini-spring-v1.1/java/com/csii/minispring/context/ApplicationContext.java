package com.csii.minispring.context;

public interface ApplicationContext {
	void loadBeanDefinitions();

	void parseBeanDefinitions();

	Object getBean(String beanName);
}
