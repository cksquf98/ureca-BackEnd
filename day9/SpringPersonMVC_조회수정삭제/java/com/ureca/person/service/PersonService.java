package com.ureca.person.service;

import java.sql.SQLException;
import java.util.List;

import com.ureca.person.dto.Person;

public interface PersonService {
	//Person 추가 수정 삭제 조회에 대한 명세 역할
	
	public int add(Person person) throws SQLException;
	public int edit(Person person) throws SQLException;
	public int remove(int no) throws SQLException;
	public Person read(int no) throws SQLException;
	public List<Person> readAll() throws SQLException;
}
