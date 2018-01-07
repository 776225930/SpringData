package com.example.springdata;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
/**
 * 1.Repository��һ���սӿ�,����һ����ǽӿ�
 * 2.���Լ�����Ľӿڼ̳���Repository,��ýӿڻᱻIOC����ʶ��Ϊһ�� Repository Bean.
 * ���뵽IOC������.���������ڸýӿ��ж�������һ���淶�ķ���
 * 3.ʵ����Ҳ����ͨ��ע��@RepositoryDefinition������̳�Repository�ӿ�
 * �� Repository �ӽӿ�����������
 * 1. �������������. ����Ҫ����һ���Ĺ淶
 * 2. ��ѯ������ find | read | get ��ͷ
 * 3. �漰������ѯʱ�������������������ؼ�������
 * 4. Ҫע����ǣ���������������ĸ��д��
 * 5. ֧�����Եļ�����ѯ. ����ǰ���з�������������, ������ʹ��, ����ʹ�ü�������. 
 * ����Ҫʹ�ü�������, ������֮��ʹ�� _ ��������. 
 * @author JHao
 *
 */
//@RepositoryDefinition(domainClass=Person.class,idClass=Integer.class)
public interface PersonRepository extends Repository<Person, Integer> {
	//���� lastName ����ȡ��Ӧ�� Person
	Person getByLastName(String lastName);
	
	//WHERE lastName LIKE ?% AND id<?
	List<Person> getByLastNameStartingWithAndIdLessThan(String lastName,Integer id);
	
	//WHERE lastName LIKE %? AND id<?
	List<Person> getByLastNameEndingWithAndIdLessThan(String lastName,Integer id);
	
	//WHERE email IN (?,?,?) OR birth<?
	List<Person> getByEmailInOrBirthLessThan(List<String> emails,Date birth);

	//WHERE a.id>?
	List<Person> getByAddress_IdGreaterThan(Integer id);
	
	//��ѯ id ֵ�����Ǹ� Person
	//ʹ�� @Query ע������Զ��� JPQL �����ʵ�ָ����Ĳ�ѯ
	@Query("SELECT p FROM Person p WHERE p.id=(SELECT max(p2.id) FROM Person p2)")
	Person getMaxIdPerson();
	
	//Ϊ@Queryע�⴫�ݲ����ķ�ʽ1:ʹ��ռλ��.
	@Query("SELECT p FROM Person p WHERE p.lastName = ?1 AND p.email=?2")
	List<Person> testQueryAnnotationParams1(String lastName,String email);
	
	//Ϊ@Queryע�⴫�ݲ����ķ�ʽ1:ʹ����������.
	@Query("SELECT p FROM Person p WHERE p.lastName = :lastName AND p.email=:email")
	List<Person> testQueryAnnotationParams2(@Param("email")String email,@Param("lastName")String lastName);

	//SpringData ������ռλ������� %%. 
	@Query("SELECT p FROM Person p WHERE p.lastName LIKE %?1% OR p.email LIKE %?2%")
	List<Person> testQueryAnnotationParams3(String lastName,String email);
	
	@Query("SELECT p FROM Person p WHERE p.lastName LIKE %:lastName% OR p.email LIKE %:email%")
	List<Person> testQueryAnnotationParams4(@Param("email")String email,@Param("lastName")String lastName);

	@Query(value="SELECT count(id) FROM SD_PERSONS",nativeQuery=true)
    long getTotalCount();
	
	//����ͨ���Զ���� JPQL ��� UPDATE �� DELETE ����. ע��: JPQL ��֧��ʹ�� INSERT
	//�� @Query ע���б�д JPQL ���, ������ʹ�� @Modifying ��������. 
	//��֪ͨ SpringData, ����һ�� UPDATE �� DELETE ����
	//UPDATE �� DELETE ������Ҫʹ������, ��ʱ��Ҫ���� Service ��. �� Service ��ķ���������������. 
	//Ĭ�������, SpringData ��ÿ��������������, ������һ��ֻ������. ���ǲ�������޸Ĳ���!
	@Modifying
    @Query("UPDATE Person p SET p.email=:email WHERE p.id=:id")
	void updatePersonEmail(@Param("id")Integer id,@Param("email")String email);


}
