package com.ureca.person.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ureca.person.dto.Person;

@Controller // ==Spring Container로 객체 관리 받고 싶어요!
@RequestMapping("/person")
public class PersonController {
	
	/*
	   controller의 역할
	   1. 요청 분석
	   2. 데이터 얻어오기
	   3. 모델 호출
	   4. 모델 호출 결과를 영역에 저장
	   5. 페이지 이동 << 맨 마지막에 정의: return을 통해서!
	 */
	
	
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
		
		
		return "list";
	}
	
	@GetMapping("/list")
	public String list() {
		return "list";
	}
}