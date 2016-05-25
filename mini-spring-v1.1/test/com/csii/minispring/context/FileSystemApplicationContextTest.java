package com.csii.minispring.context;

import org.junit.Test;

import com.csii.test.pojo.Company;
import com.csii.test.pojo.Person;

public class FileSystemApplicationContextTest {
	@Test
	public void testStartIoc() {
		FileSystemApplicationContext context = new FileSystemApplicationContext("C:\\中文路径\\beans-dtd.xml");
		Person person1 = (Person) context.getBean("person1");
		System.out.println(person1.getName());

		Person person2 = (Person) context.getBean("person2");
		System.out.println(person2.getName());

		Company company = (Company) context.getBean("company");

		System.out.println(company.getPerson().getName());

	}
}
