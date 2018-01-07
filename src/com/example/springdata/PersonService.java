package com.example.springdata;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonService {
	 @Autowired
     private PersonRepository personRepository;
     @Transactional
	 public void updatePersonEmail(Integer id,String email){
		 
		 personRepository.updatePersonEmail(id,email);
	 }
     @Transactional
     public void savePersons(List<Person> persons){
    	 personRepository.save(persons);
     }
}
