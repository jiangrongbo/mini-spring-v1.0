package com.csii.minispring.beans.xml;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.csii.minispring.beans.BeanDefinition;
import com.csii.minispring.beans.PropertyDefinition;
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
	Map beanMap = null;
	Map beanDefinitionMap = null;
	private static final String REF = "ref";
	private static final String VALUE = "value";
	private Log logger = LogFactory.getLog(BeanDefinitionParser.class);

	public Map parseBeanDefinitions(Map beanDefinationMap) {
		this.beanDefinitionMap = beanDefinationMap;
		return doParseBeanDefinition();
	}

	private Map doParseBeanDefinition() {

		Set<String> beanNameSet = beanDefinitionMap.keySet();
		beanMap = new HashMap();
		for (String beanName : beanNameSet) {
			BeanDefinition beanDefiniton = (BeanDefinition) beanDefinitionMap.get(beanName);
			initializeBean(beanDefiniton);
		}
		return beanMap;
	}

	@SuppressWarnings("unchecked")
	private void initializeBean(BeanDefinition beanDefiniton) {
		String beanId = beanDefiniton.getId();
		String className = beanDefiniton.getClassName();
		List<PropertyDefinition> props = beanDefiniton.getProperties();
		Object bean = null;
		try {
			Class beanClazz = Class.forName(className);
			bean = BeanUtils.instantiateClass(beanClazz);
			for (PropertyDefinition prop : props) {

				String propValue = prop.getPropValue();
				String refOrValue = prop.getRefOrValue();

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
			}
			beanMap.put(beanId, bean);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage());
			}
		}

	}
}
