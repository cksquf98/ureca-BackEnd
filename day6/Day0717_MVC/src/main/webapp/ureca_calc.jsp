<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>심플계산기</title>
  <style>
	  *{text-align: center;}
  </style>
</head>
<body>
  <h3>심플계산기</h3>
  <hr>
  <form name="calcForm">
     <input name="su1" type="text" size="4" maxlength="4">
     +
     <input type="text" size="4" name="su2">
     <input type="button" value="계산" onclick="calc()"><br>
         결과값:
     <input name="result" type="text" size="4">
  </form>
  <%
  	//자바코드
  %>
</body>
</html>