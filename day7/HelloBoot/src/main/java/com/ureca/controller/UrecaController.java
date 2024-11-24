package com.ureca.controller;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//@Component
@Controller
@RequestMapping("/employee")
public class UrecaController {

	// 경로 설정
	@RequestMapping("/findAll")
	public String method1() {

		return "hungry";
		/*
		 * #JSP Setting spring.mvc.view.prefix=/WEB-INF/views
		 * spring.mvc.view.suffix=.jsp
		 * 설정을 해뒀기 때문에 헝그리 파일로 이동
		 */
	}
}
