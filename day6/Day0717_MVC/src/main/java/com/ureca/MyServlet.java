package com.ureca;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/sun/ureca")  //맨앞의 '/'는 contextRoot를 의미
public class MyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
	   System.out.println("init"); //System.out: 서버 모니터링 할때 사용
	}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("service");
		//req.getMethod() ==> "GET" "POST"
		if(req.getMethod().equals("GET")) {
			 doGet(req, resp);
		}else { //else if(req.getMethod().equals("POST"))
			 doPost(req, resp);
		}
	}
	
	@Override
	public void destroy() {
		System.out.println("destroy");
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//브라우저 출력객체 생성
//		response.setCharacterEncoding("UTF-8"); //서버에서 만든 데이터의 문자집합 설정!!
		
		response.setContentType("text/html;charset=UTF-8");//설정
		PrintWriter  out = response.getWriter();//위의 설정을 갖는 출력객체 얻기 
		
//		out.print("HTML코드");
//		out.print("Hello"); 
		out.print("안녕"); 
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}