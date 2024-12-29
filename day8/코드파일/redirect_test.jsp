<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<%-- redirect_test.jsp --%>
<body>
   <%
   request.setCharacterEncoding("UTF-8");
   String username = request.getParameter("username"); 
      session.setAttribute("un", username);   
   
      response.sendRedirect("end.jsp");//페이지 이동
   %>
</body>
</html>