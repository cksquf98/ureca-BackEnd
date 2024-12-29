<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
  <h3>end.jsp(결과출력)</h3>
  <%
      request.setCharacterEncoding("UTF-8");
      String username = request.getParameter("username");      
  %>
  전달 이름: <%=username %> <%= session.getAttribute("un") %>
  <br>
  <hr>
  
  k1(pageContext) : <%= pageContext.getAttribute("k1") %><br>
  k2(Request) : <%= request.getAttribute("k2") %><br>
  k3(Session) : <%= session.getAttribute("k3") %><br>
  k4(Application) : <%= application.getAttribute("k4") %><br>
  
</body>
</html>