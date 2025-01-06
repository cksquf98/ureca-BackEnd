package com.ureca.person.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ureca.person.dto.Person;
import com.ureca.person.util.DBUtil;


//스프링 컨테이너의 객체 관리를 받고 싶다! => @Component
@Repository
public class personDAOImp implements PersonDAO {
	@Autowired  //스프링 컨테이너에 등록된 객체들 중 일치하는 자료형을 통해 Injection
	DBUtil db;

	@Override
	public int insertPerson(Person person) throws SQLException {
		Connection conn = db.getConnection();

		String sql = "insert into person (name, age, job) values (?,?,?)";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, person.getName());
		pstmt.setInt(2, person.getAge());
		pstmt.setString(3, person.getJob());
		
		return pstmt.executeUpdate(); //이미 전송되어있는 sql문 실행하라고 요청
	}

	@Override
	public int updatePerson(Person person) throws SQLException  {
		Connection conn = db.getConnection();

		String sql = "update person set age=?,job=? where no=?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, person.getAge());
		pstmt.setString(2, person.getJob());
		pstmt.setInt(3, person.getNo());
		
		return pstmt.executeUpdate(); 
	}

	@Override
	public int deletePerson(int no) throws SQLException  {
		Connection conn = db.getConnection();

		String sql = "delete from person where no=?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, no);
		
		return pstmt.executeUpdate();
	}

	@Override
	public List<Person> selectAllPeople() throws SQLException  {
		Connection conn = db.getConnection();
		String sql = "select no,name,age,job from person";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		
		ArrayList<Person> people = new ArrayList<>();
		
		ResultSet rs = pstmt.executeQuery();
		//rs사용법 : 1. 행 얻어오기    2.행안의 열 데이터 얻어오기
		while(rs.next()) {
			//얻어올 행이 존재할때까지 1번 수행
			Person person = new Person(rs.getInt("no"),
					rs.getString("name"),
					rs.getInt("age"),
					rs.getString("job")
					);
			
			people.add(person);
		}
		return people;
	}

	@Override
	public Person selectPerson(int no) throws SQLException  {
		Connection conn = db.getConnection();
		String sql = "select no,name,age,job from person where no=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1,no);
		
		ResultSet rs = pstmt.executeQuery();
		Person person = null;
		//rs사용법 : 1. 행 얻어오기    2.행안의 열 데이터 얻어오기
		if(rs.next()) {
			//얻어올 행이 존재한다면 -> 1번 수행
			person = new Person(rs.getInt("no"),
					rs.getString("name"),
					rs.getInt("age"),
					rs.getString("job")
					);
			}
		return person;
	}

}
