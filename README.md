钻研Spring 源码也有一段时间了，对Spring IoC的实现原理理解算是比较透彻了，要实现一款IoC容器，简单的概括无非需要以下几个步骤：
1.定义用来描述bean的配置的Java类，例如我们有下面的配置文件：

```
<?xml version="1.0" encoding="UTF-8"?>
<beans> 
	<bean id="person2" class="com.csii.test.pojo.Person">
	</bean> 
</beans>
```
描述该配置的Java类可以如下定义：

```
public class BeanDefinition {
	private String id;
	private String className;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}

}
```
当然Spring的bean的配置要远比这个复杂，这里只是举个例子。
2.解析bean的配置，將bean的配置信息转换为上面的BeanDefinition对象保存在内存中，spring中采用HashMap进行对象存储，其中会用到一些xml解析技术，读者可以参考笔者翻译的[《Java&XML教程》](http://blog.csdn.net/column/details/java-and-xml.html)专栏。

3.遍历存放BeanDefinition的HashMap对象，逐条取出BeanDefinition对象，获取bean的配置信息，利用Java的反射机制实例化对象，將实例化后的对象保存在另外一个Map中即可。

大家不用怀疑，其实就是这么简单，笔者在深入学习了Spring的源码后，自己动手写了一款Mini版的Spring。

下面是mini spring 的功能演示：
定义bean的配置文件beans.xml，内容如下：

```
<?xml version="1.0" encoding="UTF-8"?>
<beans>
	<!-- 测试注入字面量 -->
	<bean id="person1" class="com.csii.test.pojo.Person">
		<property name="name" value="Jack"/>
	</bean>
	
	<bean id="person2" class="com.csii.test.pojo.Person">
		<property name="name" value="Jane"/>
	</bean>
	
	<!-- 测试注入引用类型 -->
	<bean id="company" class="com.csii.test.pojo.Company">
		<property name="person" ref="person1"/>
	</bean>
	
</beans>

```
新建测试用例ClassPathXmlApplicationContextTest.java内容如下：

```
package com.csii.minispring.context;

import org.junit.Test;

import com.csii.test.pojo.Company;
import com.csii.test.pojo.Person;

public class ClassPathXmlApplicationContextTest {
	@Test
	public void testStartIoc() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:beans.xml");
		Person person1 = (Person) context.getBean("person1");
		System.out.println(person1.getName());
		
		Person person2 = (Person) context.getBean("person2");
		System.out.println(person2.getName());
		
		Company company = (Company) context.getBean("company");
		
		System.out.println(company.getPerson().getName());
		
		
	}
}
```
笔者采用JUnit作为单元测试工具，运行testStartIoc测试方法，程序运行正常，控制台输出：

```
Jack
Jane
Jack
```
下图是mini spring框架的工程结构图，源码已托管至github,希望对大家能有参考价值。
源码地址：https://github.com/rongbo-j/mini-spring-v1.0

![这里写图片描述](http://img.blog.csdn.net/20160124215314165)
下面对源代码进行简单的说明：
该工作空间用到的所有jar包都存放在mini-spring-libs工程下。
mini-spring-v1.0工程为mini spring的源码，分为三个源码包，resource、java、test；resource源码包下存放所有资源文件，test源码包存放测试用例，java源码包下为核心源代码。
该开源项目会长期维护，感兴趣的朋友在github上fork一下源码即可。


