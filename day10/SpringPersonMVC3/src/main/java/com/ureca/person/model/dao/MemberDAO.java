package com.ureca.person.model.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.ureca.person.dto.Member;

@Mapper
public interface MemberDAO {

	
    @Insert("insert into person (id,name,password,phone) values (#{id},#{name},#{password},#{phone})")
    public int insert(Member member) throws SQLException; // 나는 sql 실행에만 전념할래

    @Update("update person set password=#{password}, name=#{name}, phone=#{phone} where member_idx=#{member_idx}")
    public int update(Member member) throws SQLException;

    @Delete("delete from members where member_idx=#{member_idx}")
    public int delete(int member_idx) throws SQLException;

    @Select("select * from person where member_idx=#{member_idx}")
    public Member select(int member_idx) throws SQLException;

    @Select("select member_idx, id, name, phone from members")
    public List<Member> selectAll() throws SQLException;
	
    @Select("select member_idx from members where id = #{id} and password=#{password}")
    public int login(Member member) throws SQLException;
}


















