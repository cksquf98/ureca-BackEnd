package com.ureca.person.dao;

import com.ureca.person.dto.Person;

public interface PersonDAO {

	public void insertPerson(Person person);
	public void updatePerson(Person person);
	public void deletePerson(Person person);
	public void selectAllPeople();
	public void selectPerson();
}