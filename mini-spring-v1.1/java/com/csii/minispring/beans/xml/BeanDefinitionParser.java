package com.csii.minispring.beans.xml;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.csii.minispring.beans.BeanDefinition;
import com.csii.minispring.beans.Property;
import com.csii.minispring.utils.BeanUtils;
import com.csii.minispring.utils.ClassUtils;
import com.csii.minispring.utils.StringUtils;

/**
 * used the BeanDefinition object map to initialize bean.
 * 
 * @author CSII Rongbo
 * 
 */
public class BeanDefinitionParser {
	BeanDefinitionReader reader = null;
	Map<String, Object> beanMap = null;
	Map beanDefinitionMap = null;
	private static final String REF = "ref";
	private static final String VALUE = "value";
	private Log logger = LogFactory.getLog(BeanDefinitionParser.class);

	public void parseBeanDefinitions(Map<String, Object> beanMap, BeanDefinitionReader reader) {
		this.beanMap = beanMap;
		this.reader = reader;
		doParseBeanDefinition();
	}

	private void doParseBeanDefinition() {
		beanDefinitionMap = reader.getBeanDefinitionMap();
		Set<String> beanNameSet = beanDefinitionMap.keySet();

		for (String beanName : beanNameSet) {
			BeanDefinition beanDefiniton = (BeanDefinition) beanDefinitionMap.get(beanName);
			initializeBean(beanDefiniton);
		}
	}

	@SuppressWarnings("unchecked")
	private void initializeBean(BeanDefinition beanDefiniton) {
		String beanId = beanDefiniton.getId();
		String className = beanDefiniton.getClassName();
		List<Property> props = beanDefiniton.getProperties();

		try {
			Class beanClazz = Class.forName(className);
			Object bean = BeanUtils.instantiateClass(beanClazz);
			for (Property prop : props) {

				String propValue = prop.getPropValue();
				String refOrValue = prop.getRefOrValue();
				try {
					String propName = prop.getPropName();
					String methodName = StringUtils.prop2SetMethodName(propName);
					Method method = ClassUtils.getSetterMethod(methodName, beanClazz);
					if (VALUE.equals(refOrValue)) {
						method.invoke(bean, propValue);
					} else if (REF.equals(refOrValue)) {
						if (beanMap.get(propValue) != null) {
							method.invoke(bean, beanMap.get(propValue));
						} else {
							BeanDefinition refBeanDefinition = (BeanDefinition) beanDefinitionMap.get(propValue);
							initializeBean(refBeanDefinition);
						}
					}
				} catch (NoSuchMethodException e) {
					logger.error(e.getMessage());
				} catch (SecurityException e) {
					logger.error(e.getMessage());
				} catch (IllegalAccessException e) {
					logger.error(e.getMessage());
				} catch (IllegalArgumentException e) {
					logger.error(e.getMessage());
				} catch (InvocationTargetException e) {
					logger.error(e.getMessage());
				}
			}
			beanMap.put(beanId, bean);

		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage());
		}

	}
}
