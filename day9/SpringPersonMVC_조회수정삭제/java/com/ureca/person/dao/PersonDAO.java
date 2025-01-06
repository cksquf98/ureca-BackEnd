package com.ureca.person.dao;

import java.sql.SQLException;
import java.util.List;

import com.ureca.person.dto.Person;

public interface PersonDAO {

	//insert into person(no,name,age,job) values(?,?,?,?) 
	public int insertPerson(Person person) throws SQLException; 
	//excuteUpdate의 리턴값은 int -> boolean, String으로 받아도 됨
	
	//update person set age=?, job=? where no=?
	public int updatePerson(Person person) throws SQLException;
	
	//delete from person where no=?
	public int deletePerson(int no) throws SQLException;
	
	//select * from person
	public List<Person> selectAllPeople() throws SQLException;
	
	//수정폼에서 사용
	//select * from person where no=?
	public Person selectPerson(int no) throws SQLException;
}