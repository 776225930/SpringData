package com.example.springdata;

import org.springframework.data.repository.Repository;

public interface PersonRepository extends Repository<Person, Integer>{
	//���� lastName ����ȡ��Ӧ�� Person
	Person getByLastName(String lastName);
}
