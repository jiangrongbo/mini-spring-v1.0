package com.csii.minispring.context;

import com.csii.minispring.io.ClassPathResource;

/**
 * ClassPathXmlApplicationContext
 * 
 * @author Rongbo
 * 
 */
public class ClassPathXmlApplicationContext extends AbstractApplicationContext {

	public ClassPathXmlApplicationContext() {
	}
	
	public ClassPathXmlApplicationContext(String configLocation) {
		this.configResource = new ClassPathResource(configLocation);
		refresh();
	}

}
