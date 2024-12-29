<%@page import="com.ureca.A"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>좋아요</title>
</head>
<body>
	<h3>GOOOOOOD</h3>
	<hr color="red">
	
	<%! 
		//자바 코드 %!태그 - 클래스 멤버 영역을 의미
		int su = 1004; //멤버 변수
	%>
	<hr color="blue">
	
	<% 
		//service 메서드 영역 
		String str = "조아조아"; //지역 변수
		out.print("str = "+str);
		str = "오늘 메뉴는?";
		
		
		//객체 생성
		A a = new A();
	%>
	<hr color="green">	
	<%= 
		str //%=태그는 출력문을 의미
	%>
	
	<hr color="pink">
	<%=
		a.getMsg()
	%>
</body>
</html>