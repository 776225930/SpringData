package com.example.springdata;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class PersonRepositoryImpl implements PersonDao {
    @PersistenceContext
	private EntityManager entityManager;
	@Override
	public void test() {
         Person person=entityManager.find(Person.class, 1);
	     System.out.println("--->"+person);
	}

}
