package com.example.springdata;

import org.springframework.data.repository.Repository;

public interface PersonRepository extends Repository<Person, Integer>{
	//根据 lastName 来获取对应的 Person
	Person getByLastName(String lastName);
}
