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

public class SpringDataTest {
   private ApplicationContext ctx;
   private PersonRepository personRepository;
   {
	   ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
	   personRepository=ctx.getBean(PersonRepository.class);
   }
	@Test
	public void test() throws SQLException {
		DataSource dataSource=(DataSource) ctx.getBean("dataSource");
	    System.out.println(dataSource.getConnection());
	}
	@Test
	public void testJPA(){
	}
	
	@Test
	public void testHelloWorldSpringData(){
		System.out.println(personRepository.getClass().getName());
	    Person person=personRepository.getByLastName("AA");
	    System.out.println(person);
	}
	@Test
	public void testKeyWords(){
		List<Person> persons=personRepository.getByLastNameStartingWithAndIdLessThan("A", 3);
	    System.out.println("getByLastNameStartingWithAndIdLessThan: "+persons);
	    
	    persons=personRepository.getByLastNameEndingWithAndIdLessThan("A", 3);
	    System.out.println("getByLastNameEndingWithAndIdLessThan: "+persons);
	    
	    persons=personRepository.getByEmailInOrBirthLessThan(Arrays.asList("aa@163.com","bb@163.com"), new Date());
	    System.out.println("getByEmailInOrBirthLessThan: "+persons);
	}
	
	

}
