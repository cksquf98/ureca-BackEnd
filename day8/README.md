### Servlet

- 서블릿
    - 자바플랫폼에서 컴포넌트를 기반으로 한 웹 애플리케이션을 개발할때 사용
    - 스레드 기반 ⇒  웹 애플리케이션 운영에 효율적
    - 자바 기반 ⇒ 자바 API를 모두 사용할 수 있다
    - 운영체제나 하드웨어에 영향을 받지 X ⇒ 다양한 서버환경에서 실행 가능

- 서블릿 생명주기 메서드
    - init( ) : 서블릿 초기화
    - service(request, response) : 웹 서비스 메소드
    - destroy( ) : 서블릿 종료 - 대부분 컨테이너가 종료될 때 실행됨
        - 아파치 톰캣 == 서블릿 컨테이너 == 서버/실행기
    
- 서블릿과 JSP를 실행하기 위해서는 WAS가 필요

- 서블릿 클래스 호출 시

<aside>
💡 <톰캣에서 하는 일>

1. 클래스유무 체크
2. 메모리 적재 체크
3. 메모리 적재가 안되었을때 : 메모리적재-init()호출-service()호출

      메모리 적재가 되었을때 : service()호출

</aside>

- GET / POST 방식
    - 클라이언트 기준
    - HTTP 전송 방식
    - GET방식 : 서버에 있는 HTML을 브라우저로 가져오기 위함
    
    <aside>
    💡  1.   http://localhost:8080/ureca/a.html
    
    1. <a href=“a.html”>이동</a>
    2. location.href=“a.html”
    </aside>
    
    - POST방식 : 서버로 정보를 올리기 위해 설계된 방법
        
                           폼 내의 데이터를 form action에 명시된 URL로 전송
        

- 서블릿 클래스의 상속관계

<aside>
💡 javax.servlet.Servlet(인터페이스)
                 |
                 |
javax.servlet.GenericServlet(클래스) 
                 |
                 |
javax.servlet.http.HttpServlet(클래스)

</aside>

- 서블릿 실행방법
1. http://192.168.0.96:8080/TomTest/servlet/com.ureca.MyServlet
    
    ---> 보안상 문제 발생
    
2. WEB-INF/web.xml에 작성된 서블릿 클래스를 등록
---> 등록과 함께 서블릿에 대한 가상의 경로를 매핑시켜서 호출.
3. @annotation으로 서블릿을 등록
    - @WebServlet(”Path”) → @RequestMapping(”Path”) 어노테이션으로 변경

<aside>
💡 Servlet용도(무엇에 쓰는 물건)?

1. 브라우저에 출력할 화면구성(어려워)

2. 핵심! Controller기능(MVC패턴구조에서 사용)

JSP용도(무엇에 쓰는 물건)?      

1. 브라우저에 출력할 화면구성(쉬워)
---> 하지만 HTML도 화면구성을 할 수 있는데?
--->핵심! HTML+Java(서버와 데이터를 공유)

</aside>

## JSP(Jakarta Server Page, 서버스크립트)

- 브라우저 서비스 뷰를 담당하는 한 페이지를 표현
- 서블릿 컨테이너를 통해 실행
- 서버스크립트 ↔ 클라이언트 스크립트

<aside>
💡 클라이언트(브라우저) 스크립트 : JavaScript
서버(JVM) 스크립트   : JSP

</aside>

<aside>
💡

```
HTML(CSS,JavaScript)       + Java코드
    물                         기름
  ------                      -------
브라우저 실행                 JVM에서 실행
```

===> ★  누가 먼저 실행? Java코드

내(HTML)안에 너(Java)있다

</aside>

- 실행절차

```java
1. jsp페이지 요청
   http://ip:port/컨텍스트루트/경로/hello.jsp
                 ----------
                 ContextRoot==프로젝트명
                 
----------- 서버의 반응  -----------------------
2. jsp페이지 존재 유무     ----무---->   404에러

3. 매핑된 서블릿 클래스(hello_jsp.java)  존재 유무     -----무 ----> 생성
    public class hello_jsp extends HttpServlet{
        public void init(){}
        public void service(HttpServletRequest request,
                            HttpServletResponse response){
            out.print("JSP페이지에서 작성된 HTML");                    
        }
        public void destroy(){}
    }   

4. 컴파일(hello_jsp.class) 유무   -----무----> 컴파일
5. 메모리 적재 유무  ----무----> 메모리 로딩!!
6. 최초 호출인 경우 init()메소드 호출
7. service()메소드 호출
```

- JSP 기본태그

```java
1. Declaration(선언) <%!      %>
   <%!
              멤버요소  ---> 변수선언, 변수선언과 동시에 초기화, 메소드 정의
       int su;     (O)
           su=10;  (X)
       int su2=20; (O)
       
       public String getMsg(){
         return "건강하세요~!!";
       } -----> (O)  비권장!!
       
       System.out.println(su2);   ---->  메소드 호출(X) 
           
   %>

2. Scriptlet(스크립트릿, 실행)  <%       %>
   <%
       service()메소드 안의 자바코드!!
       - 변수선언, 변수초기화, 메소드호출, 조건문, 반복문, 자바주석문(//, /**/) 
   
       int su3;
       int su4=40;
           su=10;//멤버초기화
           su3=30;//지역변수 초기화
           
       if(조건식){}
       for(){}
       System.out.println("안녕");           
   %>

3. Expression(표현식, 출력식) <%=      %>
   <%= 데이터 %>    ----> 서블릿 코드에서 out.print(데이터); 변환!!
   <%= 브라우저에 출력하고자 하는 데이터  %>
         ---> 데이터, 변수명, 연산자,  (리턴값이 있는)메소드호출
   
   <%= 2+3 %>              -----> out.print(2+3); 
   <%= "안녕" %>            -----> out.print("안녕");
   <%= su4 %>              -----> out.print(su4);
   <%= getMsg()  %>        -----> out.print(getMsg()); 
   <%= getMsg(); %>        -----> out.print(getMsg(););  (X)

4. Comment(주석,설명문)  <%--   --%>
   <%-- JSP주석   --%>
```

- QUIZ !!

![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/90f0cea1-2c0a-45ef-8fdd-d99b6da3fa09/ceaffa1f-0eeb-48db-b443-664d1794fcc1/Untitled.png)

문제2) 자바영역의 문자열을 자바스크립트 영역에서 출력

```java
<%  int su=300;    
        String str="Friday";
%>
    
<script>
document.write(su);  ==> 가능? 
document.write(<%=su%>);  ==  document.write(300);   ==> 가능? ㅇㅇ
          
document.write(str);  ==> 가능? 
document.write(<%=str%>);  ==> 가능? 되긴한데 문제 발생
                            ==>document.write(Friday);   // Friday를 변수명으로 인식 

document.write('<%=str%>');  ==> 가능? ㅇㅇ
                             ==>document.write('Friday');  
</script> 
```

- JSP지시어
    - JSP파일의 속성을 기술
    - page 지시어
    - include 지시어
    - taglib 지시어

### JSP기본객체(내장객체) **★**

JSP페이지내에서 자료형을 선언하지 않고 사용하는 객체

📚 **Request**

- 주요 메소드
    - getParameter(String name)
    
    ```java
    문자열 name과 같은 이름을 가진 파라미터의 값 얻어오기
    
    ★ setAttribute(String key, Object value);    ==> 데이터 저장 - controller에서 수행
    ★ getAttribute(String key);                  ==> 데이터 조회 - jsp파일에서 수행
      ==> ★★영역(scope)객체들이 공통적으로 사용.
             --------------
            ~~page[Context]~~,**request,session**,application
            ==> 영역내에 데이터(숫자,문자열,Beans,ArrayList)를 저장, 조회하는 역할  
    ```
    
    - getParameterNames
    - getCookies : 모든 쿠기값을 javax.servlet.http.Cookie의 배열형태로 얻어오기
    - getMethod : 요청방식을 문자열로 얻어오기 ("GET","POST")
    - getSession : 현재 세션객체
    - getRemoteArr : 클라이언트의 IP주소
    - setCharacterEncoding("문자집합명") : POST로 전달된 한글을 처리하기 위해 사용
    
    <aside>
    💡 <Request한글 처리>
    요청페이지로 부터 얻는 (한글)데이터에 대한 문자집합 설정
       ==> request를 통해 (폼을 통해)전달된 한글에 대한 처리(한글깨짐 방지)
    
       ==> request.setCharacterEncoding("문자집합");
    
    ```
      ※주의)
    1. 그 어떤 request.getParameter("name"); 보다 먼저 설정!!
    2. 전달 받는 방식을 POST!!
    3. request(HTML)에 정의된 문자집합과 일치하는 설정(setCharacterEncoding)을 해야함!!
    ```
    
    ```
             input.jsp                            print.jsp
    ---------------------------------    ------------------------------------
    <%@page pageEncoding="UTF-8">        request.setCharacterEncoding("UTF-8");
    <%@page pageEncoding="euc-kr">       request.setCharacterEncoding("euc-kr");
    
    ```
    
    </aside>
    

📚 **Response**

- 주요 메소드

```
 setContentType(type)
   - 문자열형태의 type에 지정된 MIME Type으로 contentType을 설정.
 setHeader(name,value)
   - 문자열 name의 이름으로 문자열 value의 값을 헤더로 설정.
 setDateHeader(name,date)
   - 문자열 name의 이름으로 date에 설정된 밀리세컨드 시간 값을
      헤더에 설정.
 sendError(status, msg)
   - 오류코드를 세팅하고 메시지를 보낸다.
 sendRedirect(url)  ★
   - 클라이언트 요청을 다른 페이지로 보낸다.(페이지 이동)

```

- 페이지간 이동 :  a.jsp   ----->   b.jsp
    - HTML : <a href="이동경로b.jsp">텍스트 또는 이미지</a>
    <form action="이동경로"> <input type="submit"> <button> </form>
    - JavaScript: location.href="이동할경로";
    replace("이동할경로")
        
        폼이름.submit();  ---> 폼태그내의 action에 명시된 경로로 이동!!
        
- **JSP ★**
    - **포워드 이동**
    pageContext.forward(); ===> JSP페이지에서만 사용
    requestDispatcher.forward(); ===> JSP와 Servlet 둘 다 사용 가능!!
    특징) 브라우저에 출력되는 페이지와 URL에 명시된 페이지가 서로 다름.
    URL에 명시된 페이지와 이동한 페이지간에 request를 공유. (새로고침하면 request가 또 가는거임!!)
    - **리다이렉트 이동**
        
        response.sendRedirect("이동경로");
        특징) 브라우저에 출력되는 페이지와 URL에 명시된 페이지가 서로 같음.
        URL에 명시된 페이지와 이동한 페이지간에 session를 공유.
        
    
    <aside>
    💡 기본적으로 페이지 이동은 포워드로 해놓고, 문제 발생이 예상되면 리다이렉트로 변경하는 방식으로 해라~
    
    </aside>
    

### JSP영역객체**★★★**

- pageContext : 현재 페이지에서만 유효
- ***request*** : 요청페이지와 응답페이지   (forward이동했을때의 호출한페이지와 호출된페이지)
- ***session*** : 동일 브라우저면 유효
- application : 같은 도메인이면 몽땅 공유!! = 동일 서버 (정확히는 같은 서버의 동일 Context)

![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/90f0cea1-2c0a-45ef-8fdd-d99b6da3fa09/0a3cd6cc-0f80-4be7-8563-df92f459c534/Untitled.png)

- session(javax.servlet.http.HttpSession) ★★
(세션 == 접속)
    - 클라이언트에 대한 세션정보를 처리하기 위해 사용
    - 비연결형 프로토콜 HTTP
    (한 페이지가 출력된 다음에 서버와 클라이언트 연결은 끊어짐.)에 대해
    마치 계속 연결되어있는 것처럼 해주는 메커니즘
    - 쿠키는 사용자PC에 세션은 서버에 사용자와 관련된 정보를 보관
    
    <aside>
    💡 로그인한 후 서버랑 브라우저는 계속 연결되어있는게 아님!!
    
    * 브라우저를 통해 URL요청을 했을때 서버에서는 각브라우저를 식별할수 있는 번호를 부여!!
    * 서버측 메모리에 기억하고 있다가 브라우저가 또다른 (URL)요청을 할 때 같은 Client인지 비교
    
    </aside>
    
    - 주요 메소드
    
    ```
      getId()
        - 각 접속에 대한 세션 고유의 ID를 문자열형태로 리턴.
      getCreatingTime()
        - 세션이 생성된 시간을 long형 밀리세컨드 값으로 리턴
          (January 1, 1970 GMT기준)
      getLastAccessedTime()
        - 현재 세션으로 마지막 작업한 시간을 long형 밀리세컨드 값으로 리턴.
      getMaxInactiveInterval()
        - 세션의 유지시간을 초로 반환.
      setMaxInactiveInterval(t)
        - 세션의 유효시간을 t에 설정된 초 값으로 설정.
    
      **invalidate() ★**
        - 현재 세션을 종료.
        - 로그아웃에서 사용.
    
      setAttribute(key, value)
        - 문자열 key으로 Object value을 설정.
    
      getAttribute(key)
        - 문자열 key로 설정된 세션 값을 Object형태로 반환.
    
      removeAttribute(key)
        - 문자열 key로 설정된 Object 삭제.
    ```
    

---

### 실습코드 - JSP 기본태그

```java
1. webapp > good.jsp파일 생성 후 
2. src/main/java에 A.java 클래스 생성
3. 서비스 메서드 영역에서 A 객체 생성 - import 단축키 : ctrl + space
------------------------------------------------------------------------------------

[JSP파일]
~~<%@page import="com.ureca.A"%>~~
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
		~~A a = new A();~~
	%>
	<hr color="green">	
	<%= 
		str //%=태그는 출력문을 의미
	%>
	
	<hr color="pink">
	~~<%=
		a.getMsg()
	%>~~
</body>
</html>
```

```java
[A클래스]
package com.ureca;

public class A {
	public String getMsg() {
		return "회덮밥";
	}
}
```

### 실습코드2 - JSP 기본태그

- 조회수 카운트

```java
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
	
</body>
</html>
```

- 브라우저당 한번만 조회수 카운트되도록 제한

```java
	브라우저당 한번만 실행 제한 : 
	<%! int cnt_limit = 0; %>
	<%
		if(session.isNew()) //브라우저를 통해 첫 접속을 하였다면(=처음 세션이라면)
			++cnt_limit;
	%>
	
	<br>
	조회수 : <%= cnt_limit %>
```

- 접속자 세션 알아내기

```java
request.getRemoteAddr() + " : " + session.getId()
```

### 실습코드 - 페이지 이동 **

- start.jsp → forward.jsp / redirect.jsp →end.jsp 이동
- <%= session.getAttribute("un") %>

![start.jsp](https://prod-files-secure.s3.us-west-2.amazonaws.com/90f0cea1-2c0a-45ef-8fdd-d99b6da3fa09/edc81b53-9114-44a6-b5a2-2d4378a5c2ad/Untitled.png)

start.jsp

```java
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
```

- 페이지 이동

![forward이동 : URL과 보여주는 페이지가 다름](https://prod-files-secure.s3.us-west-2.amazonaws.com/90f0cea1-2c0a-45ef-8fdd-d99b6da3fa09/1a3e13a4-c806-45d7-aaf3-8fb47620d56f/Untitled.png)

forward이동 : URL과 보여주는 페이지가 다름

![리다이렉트 : URL과 보여주는 페이지가 일치](https://prod-files-secure.s3.us-west-2.amazonaws.com/90f0cea1-2c0a-45ef-8fdd-d99b6da3fa09/2aff4e9b-9a54-470e-a36d-e374f825c923/Untitled.png)

리다이렉트 : URL과 보여주는 페이지가 일치

- 포워드 : forward_test.jsp에서 Request 객체는 현재 페이지임. 현재 페이지를 데이터로 넘겨서 end.jsp에서 request.getParameter로 forward_test에 있는 request데이터 공유

```java
<%
   request.getRequestDispatcher("end.jsp")
          .forward(request, response);//페이지 이동
%>
```

- 리다이렉트 : 새로 페이지를 시작함. 이전 페이지에서의 request 데이터가 이어지지 않음 ⇒
    
                         데이터 전달하고 싶어서 **Session**을 통해 담아서 보내줘야함
    

```java
<%
   request.setCharacterEncoding("UTF-8");
   String username = request.getParameter("username"); 
   session.setAttribute("un", username);   
    
   response.sendRedirect("end.jsp");//페이지 이동
%>
```

- pageContext : 현재 페이지에서만 유효한 데이터
- request : 호출한 페이지에서는 유효
- session : 세션 유지되면 유효
- application : 같은 도메인이면 몽땅 유효

```java
  k1(pageContext) : <%= pageContext.getAttribute("k1") %><br>
  k2(Request) : <%= request.getAttribute("k2") %><br>
  k3(Session) : <%= session.getAttribute("k3") %><br>
  k4(Application) : <%= application.getAttribute("k4") %><br>
```

![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/90f0cea1-2c0a-45ef-8fdd-d99b6da3fa09/052e7fbe-22d6-4862-84dd-1b63c2326a00/Untitled.png)

- 포워딩 결과

![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/90f0cea1-2c0a-45ef-8fdd-d99b6da3fa09/faf35034-14c2-4835-af93-a31dcd0d398e/Untitled.png)

- 리다이렉트 결과

### 실습코드4 - Session

- sessionT1.jsp 로그인 → 성공 → sessionT2.jsp 하이퍼링크 클릭 → sessionT3.jsp : 로그인 성공 유저만 이미지 보이도록
- 디비 연결까진 안하고 맵으로 데이터 미리 저장해둠

```java
로그인폼 [sessionT1.jsp]
<form name="inputForm" action="sessionT2.jsp" method="post">
<input type="text" name="id">
<input type="password" name="pass">
<button>확인</button>
</form>
```

```java
로그인 체크 [sessionT2.jsp]
-----------------------------------------------------------------------------------
<%
HashMap<String,String> map = new HashMap<>();//map == DataBase
   //key:아이디, value:패스워드
   //디비 대신 ㅎㅎ 회원데이터 저장해두기
      map.put("gildong","1234");
      map.put("lime","5678");
      map.put("juwon","3333");
      
//클라이언트 입력데이터 얻기
	String id = request.getParameter("id"); //폼 인풋태그 네임
	String pass = request.getParameter("pass"); //폼 인풋태그 네임
	
//아이디, 비번 검사 ==> 페이지 이동
    String dbPass = map.get(id); //없는 아이디라면 null이 리턴됨
	if(dbPass != null) { 
		if(dbPass.equals(pass)) {
			//비번까지 일치 => session 부여
			session.setAttribute("login", "success");
		}
		else {
			//비번 틀림
			response.sendRedirect("sessionT1.jsp");
		}	
	}
	else {
		//아이디 없음
		response.sendRedirect("sessionT1.jsp");
	}
%>

  <body>    
   <center>
   <p class="normalbold">사용자 인증 완료</p>
   <p class="normal"><a href="sessionT3.jsp">서비스페이지</a></p>
   </center>  
  </body>
```

```java
로그인한 유저인지 체크하기 [sessionT3.jsp]
----------------------------------------------------------------------------
 <%
 	 String login = (String)session.getAttribute("login");
 
     //로그인하지 않았다면!!
     if(login == null || !login.equals("success")) {
    	 // login == null || 필요하네.. 
    	 // !login.equals("success") 여기서 null을 잡진 못하는구먼..
    	 response.sendRedirect("sessionT1.jsp");
     }
 %>
```

```java
세션종료 [sessionT4.jsp]
----------------------------------------------------------------------------
 <%
     //세션종료, 로그아웃 프로세스
	session.invalidate();
 %>  
```
