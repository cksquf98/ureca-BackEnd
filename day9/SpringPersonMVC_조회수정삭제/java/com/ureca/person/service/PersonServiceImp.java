package com.ureca.person.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Service;

import com.ureca.person.dao.PersonDAO;
import com.ureca.person.dto.Person;

@Service
public class PersonServiceImp implements PersonService {
	@Autowired
	PersonDAO dao;
	
	
	@Override
	public int add(Person person) throws SQLException {
		return dao.insertPerson(person);
	}

	@Override
	public int edit(Person person) throws SQLException {
		return dao.updatePerson(person);
	}

	@Override
	public int remove(int no) throws SQLException {
		return dao.deletePerson(no);
	}

	@Override
	public Person read(int no) throws SQLException {
		return dao.selectPerson(no);
	}

	@Override
	public List<Person> readAll() throws SQLException {
		return dao.selectAllPeople();
	}

}
