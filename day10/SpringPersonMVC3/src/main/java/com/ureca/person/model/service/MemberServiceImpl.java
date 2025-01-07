package com.ureca.person.model.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ureca.person.dto.Member;
import com.ureca.person.model.dao.MemberDAO;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberDAO dao;
	
	@Override
	public int add(Member member) throws SQLException {
		return dao.insert(member);
	}

	@Override
	public int edit(Member member) throws SQLException {
		return dao.update(member);
	}

	@Override
	public int remove(int member_idx) throws SQLException {
		return dao.delete(member_idx);
	}

	@Override
	public Member read(int member_idx) throws SQLException {
		return dao.select(member_idx);
	}

	@Override
	public List<Member> readAll() throws SQLException {
		return dao.selectAll();
	}

	@Override
	public int loginCheck(Member member) throws SQLException {
		return dao.login(member);
	}
}
