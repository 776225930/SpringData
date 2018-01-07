package com.example.springdata.test;

import static org.junit.Assert.*;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringDataTest {
   private ApplicationContext ctx;
   {
	   ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
   }
	@Test
	public void test() throws SQLException {
		DataSource dataSource=(DataSource) ctx.getBean("dataSource");
	    System.out.println(dataSource.getConnection());
	}
	@Test
	public void testJPA(){

	}

}
