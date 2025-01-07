package com.ureca.person.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ureca.person.dto.Member;
import com.ureca.person.model.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;

@Controller 
@RequestMapping("/MulcamPortal")
public class MemberController {
	
	@Autowired
	MemberService service;//service=null;기본값
	
	@GetMapping("/")
	public String Home() {
	  return "home"; 
	}
	
	@GetMapping("/login")
	public String login() {
		
		return "login";
	}
	
	@PostMapping("/login")
	public String loginMember(Member member, Model model) {
		//디비 로그인
		System.out.println(member);
		
		try {
			service.loginCheck(member);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return "home";
	}
	
	@GetMapping("/signUp")
	public String signUp() {
		
		return "signUp";
	}
	
	
	
//	@GetMapping("/form")
//	public String form() {//입력폼 보이기
//	  return "form";  //  "/WEB-INF/views/"+ "form"  + ".jsp"    ==> 5. forward이동	
//	}
//	
//	@PostMapping("/form")
//	public String regist(Person person, Model model) {//DB입력
//		System.out.println(">>> POST form");
//		System.out.println("person>>>"+ person);
//		
//		try {
//			service.add(person);//3.
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//		return "redirect:list";  // 5.	
//	}
//	
//	@GetMapping("/list") //1.
//	public String list(Model model) { //DB목록출력
//		//Model은 영역객체 중에 request와 같음
//		
//		try {
//			//목록 테이블에 출력할 데이터 얻어오기
//			List<Person> list = service.readAll(); //3.			
//			
//			model.addAttribute("list", list);
//			//4.영역에 데이터를 저장! => 왜? 데이터를 View와 공유하기 위해
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//		return "list";  //5.
//	}
//	
//	@GetMapping("/upform")//  localhost:8080/person/upform?no=3
//	public String upform(@RequestParam("no") int no,
//			             Model model) {//수정폼 보이기
//		
//		
//		try {
//			model.addAttribute("person", service.read(no));
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//		return "upform";
//	}
//	
//	@PostMapping("/upform")
//	public String modify(Person person) {//DB수정 요청
//		try {
//			service.edit(person);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return "redirect:list";//수정 결과를 list페이지로 확인
//	}
//	
//	@GetMapping("/delete")//  localhost:8080/person/delete?no=3
//	public String remove(@RequestParam("no") int no) {//DB삭제 요청
//		try {
//			service.remove(no);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return "redirect:list";//삭제 결과를 list페이지로 확인
//	}
	
	
	
	
}





