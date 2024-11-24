package com.ureca;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ureca/color")
public class UrecaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, 
						 HttpServletResponse response) throws ServletException, IOException {
		
		/*
		 <페이지 이동 서비스>
		 1. 포워드
		 2. 리다이렉트
		 */
		
		//단순포워딩 : 모델 호출 없이 페이지만 보여줌
//		RequestDispatcher dispatcher = request.getRequestDispatcher("이동할 페이지");
//		dispatcher.forward(request, response);
		request.getRequestDispatcher("/WEB-INF/views/red.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, 
						  HttpServletResponse response) throws ServletException, IOException {
	
	}
}