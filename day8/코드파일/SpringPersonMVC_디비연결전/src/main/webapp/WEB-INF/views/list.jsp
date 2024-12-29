<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	  <table>
	    <tr>
	      <th>번호</th>
	      <th>이름</th>
	      <th>나이</th>
	      <th>직업</th>
	    </tr>	   
		    <tr>
		      <td></td>
		      <td></td>
		      <td></td>
		      <td></td>
		    </tr>
	  </table>    
등록된 Person이 없습니다.
  <br>
  <a href="/form">Person입력하기</a>
</body>
</html>

