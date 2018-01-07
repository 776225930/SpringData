package com.example.springdata.test;

import static org.junit.Assert.*;

import java.awt.print.Pageable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;

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

	/*-----------------------CrudRepository------------------------*/
	@Test
	public void testCrudRepository(){
		  List<Person> persons=new ArrayList<>();
		  for(int i='a';i<='z';i++){
			  Person person=new Person();
			  person.setAddressId(i+1);
			  person.setBirth(new Date());
			  person.setEmail((char)i+""+(char)i+"@163.com");
			  person.setLastName((char)(i-32)+""+(char)(i-32));
		      persons.add(person);
		  }
		  personService.savePersons(persons);
	}
	/*------------------------PagingAndSortingRepository-----------------------*/
	@Test
	public void testPagingAndSortingRepository(){
		//pageNo �� 0 ��ʼ. 
		int pageNo=3-1;
		int pageSize=5;
		//Pageable �ӿ�ͨ��ʹ�õ��� PageRequest ʵ����. ���з�װ����Ҫ��ҳ����Ϣ
		//������ص�. Sort ��װ���������Ϣ
		//Order �Ǿ��������ĳһ�����Խ��������ǽ���. 
		
		Order order1=new Order(Direction.DESC, "id");
		Order order2=new Order(Direction.ASC, "email");
		Sort sort=new Sort(order1,order2);
		
		PageRequest pageable=new PageRequest(pageNo, pageSize,sort);  
		Page<Person> page = personRepository.findAll(pageable);
		System.out.println("�ܼ�¼��:  "+page.getTotalElements());
		System.out.println("��ǰ�ڼ�ҳ:  "+(page.getNumber()+1));
		System.out.println("��ҳ��:  "+page.getTotalPages());
		System.out.println("��ǰҳ���List:  "+page.getContent());
		System.out.println("��ǰҳ���¼��: "+page.getNumberOfElements());
	}
	
	/*------------------------JpaRepository-----------------------*/
	
	@Test
	public void testJpaRepository(){
		Person person=new Person();
		person.setBirth(new Date());
		person.setLastName("xyz");
		person.setEmail("xyz@163.com");
		
		person.setId(31);
		Person person2 = personRepository.saveAndFlush(person);
	    System.out.println(person==person2);
	}
	
	/*------------------------JpaSpecificationExecutor-----------------------*/
	/**
	 * Ŀ��: ʵ�ִ���ѯ�����ķ�ҳ. id > 5 ������
	 * 
	 * ���� JpaSpecificationExecutor �� Page<T> findAll(Specification<T> spec, Pageable pageable);
	 * Specification: ��װ�� JPA Criteria ��ѯ�Ĳ�ѯ����
	 * Pageable: ��װ�������ҳ����Ϣ: ���� pageNo, pageSize, Sort
	 */
	@Test
	public void testJpaSpecificationExecutor(){
		int pageNo=3-1;
		int pageSize=5;
		PageRequest pagable=new PageRequest(pageNo, pageSize);
	    //ͨ��ʹ��Specification�������ڲ���
		Specification<Person> specification=new Specification<Person>() {
			/**
			 * @param *root: �����ѯ��ʵ����. 
			 * @param query: ���Դ��пɵ� Root ����, ����֪ JPA Criteria ��ѯҪ��ѯ��һ��ʵ����. ������
			 * ����Ӳ�ѯ����, �����Խ�� EntityManager ����õ����ղ�ѯ�� TypedQuery ����. 
			 * @param *cb: CriteriaBuilder ����. ���ڴ��� Criteria ��ض���Ĺ���. ��Ȼ���Դ��л�ȡ�� Predicate ����
			 * @return: *Predicate ����, ����һ����ѯ����. 
			 */
			@Override
			public Predicate toPredicate(Root<Person> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Path path=root.get("id");
				Predicate predicate=cb.gt(path, 5);
				return predicate;
			}
		};
	    Page<Person> page=personRepository.findAll(specification,pagable);
	    System.out.println("�ܼ�¼��:  "+page.getTotalElements());
		System.out.println("��ǰ�ڼ�ҳ:  "+(page.getNumber()+1));
		System.out.println("��ҳ��:  "+page.getTotalPages());
		System.out.println("��ǰҳ���List:  "+page.getContent());
		System.out.println("��ǰҳ���¼��: "+page.getNumberOfElements());
	}
	 @Test
	 public void testCustomerRepositoryMethod(){
		 personRepository.test();
	 }
}
