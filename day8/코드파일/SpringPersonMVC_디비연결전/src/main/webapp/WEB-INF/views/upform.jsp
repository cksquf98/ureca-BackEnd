<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Person정보수정</title>
</head>
<body>
  <h3>Person정보수정</h3>
  <hr>
  <form method="post">
  <input type="hidden" name="no" value="">
    이름: <input type="text" name="name" value="" readonly><br>
    나이: <input type="text" name="age" value=""><br>
    직업: <input type="text" name="job" value=""><br>
   <button type="submit">수정</button>
   <button type="button">삭제</button>
  </form>
  <br>
  <a href="/list">사람목록보기</a>
</body>
</html>