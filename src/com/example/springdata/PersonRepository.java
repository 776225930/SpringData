package com.example.springdata;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.Repository;
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
}
