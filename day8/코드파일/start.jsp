<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
  <h3>start.jsp</h3>
  <hr>
  <h4>forward이동</h4>
  <form method="post" action="forward_test.jsp">
     이름: <input type="text" name="username"><br>
     <input type="submit" value="이동">
  </form>
  <hr color="red">
  
  
  <h4>redirect이동</h4>
  <form method="post" action="redirect_test.jsp">
     이름: <input type="text" name="username"><br>
     <input type="submit" value="이동">
  </form>
</body>
</html>
