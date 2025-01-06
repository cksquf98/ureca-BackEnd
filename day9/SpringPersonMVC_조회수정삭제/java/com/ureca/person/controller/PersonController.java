package com.ureca.person.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ureca.person.dto.Person;
import com.ureca.person.service.PersonService;


@Controller // ==Spring Container로 객체 관리 받고 싶어요!
@RequestMapping("/person")
public class PersonController {
	
	@Autowired
	PersonService service;
	
	
	/*
	   controller의 역할
	   1. 요청 분석
	   2. 데이터 얻어오기
	   3. 모델 호출
	   4. 모델 호출 결과를 영역에 저장
	   5. 페이지 이동 << 맨 마지막에 정의: return을 통해서!
	 */
	
	
//	@RequestMapping(value="/form", method=RequestMethod.GET)
	@GetMapping("/form")
	public String form() {
		//입력폼을 브라우저에 노출
		return "form"; //5. 페이지 이동 (포워드 이동)
	}
	
	
	
	@PostMapping("/form")
//	public String regist(@RequestParam("name") String name,
//			@RequestParam("age") int age,
//			@RequestParam("job") String job
//			) {

	public String regist(Person person) {
	//regist(HttpServletRequest request) 서블릿 방식과 비교해보자
		
		//DB에 데이터 insert
		System.out.println("param >>> " + person);
		try {
			service.add(person); //3번 수행
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return "redirect:list"; //5번 수행
	}
	
	
	
	@GetMapping("/list")
	public String list(Model model) {//Model은 영역객체 중 request와 같음
		//목록테이블에 얻어올 데이터 조회
		try {
			List<Person> people = service.readAll(); //3번 수행
			
			//4번 수행
			model.addAttribute("people",people);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return "list";  //5번 수행
	}
	
	
	@GetMapping("/upform") // person/upform?no=1
	public String upform(@RequestParam int no,
			Model model) {//수정폼 보이도록
		
		try {
			Person person = service.read(no);
			model.addAttribute("person", person);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return "upform";
	}
	
	
	@PostMapping("/upform")
	public String modify(Person person) {//DB수정 요청
		try {
			service.edit(person);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "redirect:list";
	}
	
	@GetMapping("/delete")
	public String remove(@RequestParam int no) {//DB삭제 요청
		try {
			service.remove(no);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "redirect:list";
	}
	
}