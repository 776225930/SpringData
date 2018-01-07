package com.example.springdata.test;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.example.springdata.Person;
import com.example.springdata.PersonRepository;
import com.example.springdata.PersonService;

public class SpringDataTest {
	private ApplicationContext ctx;
	private PersonRepository personRepository;
	private PersonService personService;
	{
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		personRepository = ctx.getBean(PersonRepository.class);
	    personService=ctx.getBean(PersonService.class);
	}

	@Test
	public void test() throws SQLException {
		DataSource dataSource = (DataSource) ctx.getBean("dataSource");
		System.out.println(dataSource.getConnection());
	}

	@Test
	public void testJPA() {
	}

	@Test
	public void testHelloWorldSpringData() {
		System.out.println(personRepository.getClass().getName());
		Person person = personRepository.getByLastName("AA");
		System.out.println(person);
	}

	@Test
	public void testKeyWords() {
		List<Person> persons = personRepository.getByLastNameStartingWithAndIdLessThan("A", 3);
		System.out.println("getByLastNameStartingWithAndIdLessThan: " + persons);

		persons = personRepository.getByLastNameEndingWithAndIdLessThan("A", 3);
		System.out.println("getByLastNameEndingWithAndIdLessThan: " + persons);

		persons = personRepository.getByEmailInOrBirthLessThan(Arrays.asList("aa@163.com", "bb@163.com"), new Date());
		System.out.println("getByEmailInOrBirthLessThan: " + persons);
	}

	@Test
	public void testKeyWords2() {
		List<Person> persons = personRepository.getByAddress_IdGreaterThan(1);
		System.out.println(persons);
	}

	@Test
	public void testQueryAnnotation() {
		Person person = personRepository.getMaxIdPerson();
		System.out.println(person);
	}

	@Test
	public void testQueryAnnotationParams1() {
		List<Person> persons = personRepository.testQueryAnnotationParams1("aa", "aa@163.com");
		System.out.println(persons);
	}

	@Test
	public void testQueryAnnotationParams2() {
		List<Person> persons = personRepository.testQueryAnnotationParams2("aa@163.com", "aa");
		System.out.println(persons);
	}

	@Test
	public void testQueryAnnotationParams3() {
		List<Person> persons = personRepository.testQueryAnnotationParams3("aa", "aa");
		System.out.println(persons);
	}

	@Test
	public void testQueryAnnotationParams4() {
		List<Person> persons = personRepository.testQueryAnnotationParams4("aa@", "aa");
		System.out.println(persons);
	}

	@Test
	public void testNativeQuery() {
		long totalCount = personRepository.getTotalCount();
		System.out.println(totalCount);
	}

	@Test
	public void testModifying() {
		//personRepository.updatePersonEmail(1, "aaaa@163.com");
	     personService.updatePersonEmail(1, "aaaa@163.com");
	}
}
