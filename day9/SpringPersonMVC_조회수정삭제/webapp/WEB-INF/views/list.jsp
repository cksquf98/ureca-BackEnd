<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="jakarta.tags.core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Person목록</title>
  <style type="text/css">
     th,td{
        border: 1px solid black;
     }
     
     table{
        border-collapse: collapse;
     }
     
     th{
        background-color: skyblue;
     }
  </style>
</head>
<%-- list.jsp --%>
<body>
	<h3>Person목록</h3>
	<hr>
	<c:if test="${ !empty people }">
	  <table>
	    <tr>
	      <th>번호</th>
	      <th>이름</th>
	      <th>나이</th>
	      <th>직업</th>
	    </tr>	
	    <c:forEach items="${people}" var="person">  
		    <tr>
		      <td>${person.no}</td>  <!-- getter메서드 알아서 호출 -->
		      <td><a href="upform?no=${person.no}">${person.name}</a></td>
		      <td>${person.age}</td>
		      <td>${person.job}</td>
		    </tr>
		</c:forEach> 
	  </table>    
	</c:if>
	<c:if test="${empty people}">
	등록된 Person이 없습니다.
	</c:if>
  <br>
  <a href="form">Person입력하기</a>
</body>
</html>

