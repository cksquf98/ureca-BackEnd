<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>count</title>
</head>
<body>
	<% 
		int local_cnt = 0;  //지역변수라 계속 0으로 초기화됨
	%>
	<%!
		int mem_cnt = 0;
	%>	
	조회수 : <%= ++local_cnt %>회
	조회수 : <%= ++mem_cnt %>회
	
	
	<br>
	<hr>
	브라우저당 한번만 실행 제한 : 
	<%! int cnt_limit = 0; %>
	<%
		//접속 session 찾기
		out.print("브라우저 출력");
		System.out.println(request.getRemoteAddr() + " : " + session.getId()); //콘솔 출력
	
		if(session.isNew()) //브라우저를 통해 첫 접속을 하였다면(=처음 세션이라면)
			++cnt_limit;
	%>
	
	<br>
	조회수 : <%= cnt_limit %>
</body>
</html>