### 실습코드 - DI

- DTO, VO
    - 클래스 필드에 대해 생성자, Getter, Setter 등 서로 관련있는 메서드들을 묶어주는 역할
    
    <aside>
    💡 bmethod( ) { int su = 100; } 
    cmethod( ) { syso(su); } 
    
    📚 다른 위치에 있는 cmethod에서 su가 100이 나오려면?
          ⇒ 1. 파라미터로 전달
               2. bmethod 리턴값을 su로 지정
    
    📚 전달값이 많아진다면? 
          ⇒ 클래스에 담아서 리턴하도록
    
    📚 HTML은 Java가 아닌데 서버에는 자바로 전달되어야 하니까
          데이터를 Object로 바꾸고 싶어! ==> DTO
    
    📚 SQL을 통해서 DB테이블 조회하여 한 행의 데이터를 담아두는 객체 ==> VO
    
    </aside>
    
    - DTO(JavaBean) 대신 Map을 사용할 수도 있음 : Map<”컬럼변수명”, “value”>

- 어제 실습코드에 추가
    
    ```java
    **<인터페이스 생성>**
    package com.ureca.step4.DI;
    
    public interface MessageBean {
    	public void sayHello();
    }
    
    **<인터페이스 구현 클래스>**
    public class MessageBeanImp implements MessageBean {
    
    	//private 필드 : 자식도 접근 못함 => getter, setter를 통해 접근
    	private String greeting;
    	private String name;
    	
    	
    	//기본생성자
    	public MessageBeanImp() { }
    	
    	//오버로딩 생성자
    	public MessageBeanImp(String greeting, String name) {
    		super();
    		this.greeting = greeting;
    		this.name = name;
    	}
    
    	public String getGreeting() {
    		return greeting;
    	}
    
    	public void setGreeting(String greeting) {
    		this.greeting = greeting;
    	}
    
    	public String getName() {
    		return name;
    	}
    	public void setName(String name) {
    		this.name = name;
    	}
    
    	
    	//인터페이스에 선언된 메서드 오버라이딩
    	@Override
    	public void sayHello() {
    		String str = greeting + ", "+name;
    		System.out.println(str);
    	}
    }
    ```
    
    ```java
    <ApplicationContext.xml> : 인터페이스는 bean 생성 못한다는 저엄~
    
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://www.springframework.org/schema/beans
               http://www.springframework.org/schema/beans/spring-beans.xsd">
       <!-- 스프링컨테이너 : 객체의 관리(생성,소멸,DI)
            스프링 프레임워크는 Bean컨테이너!!  ==> 빈(자바클래스)을 관리
                   주의) 클래스만 등록( 인터페이스나 추상클래스는 안됨 )
           <XML기본>
           ==> XML은 데이터를 저장하는 방식.
               ==> 어떻게??
                  1. 시작태그와 끝태그안에 content로
                  2. 속성값으로
       -->
       <bean class="com.ureca.step4.DI.MessageBeanImp"
       		 id="msgBean">
    	<!-- 
       		 <생성자 주입>
       		 constructor-arg Tag = 클래스 생성자 호출
       		 	* 오버로딩 생성자의 경우 value를 통해 파라미터 전달
    	-->
       		 <constructor-arg>
    			<value>신짜오</value>
    		 </constructor-arg>
       		 
       		 
    	<!-- 
       		 <Setter 주입>
       		 Property Tag = 클래스 내의 Setter메서드를 호출
       		    * name 속성에 들어가는 값 앞에 set을 붙여서 메서드를 찾음
       		    * 전달할 파라미터 데이터 = <value>태그 추가 or value 속성 추가
    	-->
       		 <property name="name" value="나길동"></property>
       </bean>
    </beans>
    ```
    
    ```java
    <Main함수>
    	public static void main(String[] args) {
    //		MessageBean msg = new MessageBeanImp();
    //		msg.sayHello(); //null, null
    		
    		//DI를 통해 데이터 출력되게 만들기 - Setter 주입
    		//applicationContext.xml 생성 후 bean에 클래스 추가
    		ApplicationContext ctx = new ClassPathXmlApplicationContext("com/ureca/step4/DI/applicationContext.xml");
    		MessageBean msg = ctx.getBean("msgBean", MessageBean.class);
    		msg.sayHello();
    	}
    ```
    

---

### 실습코드2 - DI

- 파일 출력 객체 주입하기
    - 기존 코드 변경
    - FileOutput 인터페이스, FileOutputImp 클래스 추가
    
    ```java
    <FileOutput 인터페이스>
    public interface FileOutput {
    	public void output(String str);
    }
    
    <FileOutputImp 클래스>
    public class FileOutputImp implements FileOutput {
    	
    	/*
    	         <write>            <read>
    	 byte    outputStream       InputStream
    	 ----------------------------------------
    	 char    Writer             Reader
    	 
    	 */
    
    	@Override
    	public void output(String str) {
    		FileWriter fw;
    		try {
    			fw = new FileWriter("helloTest.txt");
    			
    			//파일에 str을 출력하기
    			fw.write(str);
    			
    			fw.close();
    		
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    	}
    }
    ```
    
    - applicationContext.xml 코드 추가
        - FileOutputImp를 통해 만들어진 fo객체를 MessageBeanImp가 참조하도록 주입
    
    ```java
     	 <bean class="com.ureca.step4.DI.MessageBeanImp"
       		 id="msgBean"> 
       		 **<property name="fo" ref="foi"></property>**
       </bean>
       
       **<bean class="com.ureca.step4.DI.FileOutputImp"
       		 id="foi">
       </bean>**
    ```
    
    - MessageBeanImp 코드 추가
    
    ```java
    	<FileOutput 필드 추가>
    	private FileOutput fo;
    	
    	
    	<FileOutput 생성자 추가>
    	public void setFo(FileOutput fo) {
    		this.fo = fo;
    	}
    	
    	
    	<함수에 파일 출력코드 추가>
    	@Override
    	public void sayHello() {
    		String str = greeting + ", " + name;
    		System.out.println(str);
    		fo.output(str); //파일 출력
    	}
    ```
    
    - 프로젝트 refresh → helloTest.txt 파일 만들어져 있음

<aside>
💡 JavaBean(자바클래스) : 
- 서로 관련성 있는 속성을 묶어주는 클래스
- DTO(Data Transfer Object),  VO(Value Object) 라는 이름으로도 사용
- 만드는 방법:

     > 속성은 private선언
     > 속성값을 접근할수 있는 public한 메소드 : getXXX(게터메소드),  setXXX(세터메소드)
     >  public 필드의자료형 get+필드명( ) {  return 필드명; }
     >  public void set+필드명(필드명과 같은 변수선언){ this.필드명 = 매개변수명; }
     >  더하는 필드명의 첫글자는 대문자
     

추가사항) 기본생성자, 오버로딩생성자, toString()

</aside>

---

### Spring Framework

- STS 설치
- 톰캣 10 ver 설치
- STS 압축 풀기 > cmd > java -jar 파일명
- 톰캣 압축 풀기
- STS > 마켓플레이스 > 이클립스 자바 엔터프라이즈 웹 install
- New > Other > Server > 톰캣 압축 푼 홈 디렉토리 경로 넣고 설정
- 우측 Open perspective > Java EE로 변경
- Dynamic Web Pj 생성 > Generate web.xml 체크
- 톰캣 서버 우클릭 > add and remove > 현재 프로젝트 add
<img width="656" alt="스크린샷 2024-11-23 오후 11 53 17" src="https://github.com/user-attachments/assets/3a84ea84-446b-46cc-9e9b-1a9327cd54b7"><br>
- Context : 제품
- Content : 서비스 리소스 → HTML, CSS, JS, image, JSP ...
<br>
  
<img width="557" alt="스크린샷 2024-11-23 오후 11 53 43" src="https://github.com/user-attachments/assets/a46d7f4e-49b3-4807-8867-c806811e14da"><br>
- java resources : 자바파일
- webapp : 나머지파일들

```html
index.html
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	앙녕 MVC
</body>
</html>	

hello.html
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	앙녕 MVC ^_^
</body>
</html>
```

```html
web.xml
--------------------------------------------
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="https://jakarta.ee/xml/ns/jakartaee" xsi:schemaLocation="https://jakarta.ee/xml/ns/jakartaee https://jakarta.ee/xml/ns/jakartaee/web-app_6_0.xsd" id="WebApp_ID" version="6.0">
  <display-name>Day0717_MVC</display-name>
  <welcome-file-list>
    <welcome-file>index.html</welcome-file>
    <welcome-file>index.jsp</welcome-file>
    <welcome-file>index.htm</welcome-file> 
    <welcome-file>default.html</welcome-file>
    <welcome-file>default.jsp</welcome-file>
    <welcome-file>default.htm</welcome-file>
  </welcome-file-list>
</web-app>

=> URL : http://127.0.0.1:8080/Day0717_MVC 일케 요청했을때 알아서 index.html 디폴트로 연결됨
```

- context root 변경
    - 하단 메뉴 servers 더블클릭 > Modules > Edit > Path를 그냥 ‘/’로 변경

- Port 변경
    - 하단 메뉴 servers 더블클릭 > Overview > Ports : HTTP/1.1 포트 변경 가능

### 실습코드 - Servlet

- MVC에서 Controller를 맡고 있음
- 함수 동작 순서
    
    init( )
    
    → service( ) : GET / POST 
    
    → destroy( )


- JSP
    - 자바와 HTML을 한 번에 쓸 수 있음
    - 유지보수가 어려울 수 있음

```java
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
  <%
  	//자바코드
  %>
</body>
</html>
```

- MyServlet.java

```java
package com.ureca;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/sun/ureca")  //맨앞의 '/'는 contextRoot를 의미
public class MyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
	   System.out.println("init"); //System.out: 서버 모니터링 할때 사용
	}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("service");
		//req.getMethod() ==> "GET" "POST"
		if(req.getMethod().equals("GET")) {
			 doGet(req, resp);
		}else { //else if(req.getMethod().equals("POST"))
			 doPost(req, resp);
		}
	}
	
	@Override
	public void destroy() {
		System.out.println("destroy");
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//브라우저 출력객체 생성
//		response.setCharacterEncoding("UTF-8"); //서버에서 만든 데이터의 문자집합 설정!!
		
		response.setContentType("text/html;charset=UTF-8");//설정
		PrintWriter  out = response.getWriter();//위의 설정을 갖는 출력객체 얻기 
		
//		out.print("HTML코드");
//		out.print("Hello"); 
		out.print("안녕"); 
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
```

---

## MVC패턴

- model1 : 파일 구분없이 필요한 내용들을 프로그래밍
- model2 : 내용, 형식에 따라서 파일을 분리하여 프로그래밍

### Model (Model2 구조)

- 비즈니스로직과 관련된 부분 처리.
- 데이터베이스 관련로직 구현
- JSP Beans, EJB Component
- 애플리케이션의 데이터를 표현.

### View

- 클라이언트 뷰

### Controller

- Request Process 담당
    - 브라우저를 통해 클라이언트가 직접 URL Request하는 경우엔 MVC 패턴이 아님
    - 요청분석 - 데이터 얻기 - 모델 호출 - 데이터 저장 - 뷰 호출(페이지 이동) 역할을 담당함
- Model로 데이터 불러온 후 View를 통해 html 출력시킴

### DAO(Database Access Object)

- 데이터베이스 연결 객체
- 데이터베이스 업무(Insert/Select/Update/Delete)를 담당하는 자바 클래스
- 각 메소드에서 SQL문 실행 관련 코드를 정의
- CRUD 작업 메소드
    
<img width="658" alt="스크린샷 2024-11-23 오후 11 54 34" src="https://github.com/user-attachments/assets/e4a3bece-33c5-47ac-a122-f848f840fe9a">


---

### 실습코드(7/18)

- 동일 프로젝트에서 WEB_INF 하위 view 폴더 생성 > red.jsp 파일 이동
    - WEB_INF : Server Scope **
    - 해당 파일 run on server > 브라우저쪽에서 URL접근 불가능

- UrecaServlet에 컨트롤러 역할 부여
    - Dispatcher : 이동할 곳을 안내해주는 가이드 역할
        
        ```java
           	/*
        		 <페이지 이동 서비스>
        		 1. 포워드
        		 2. 리다이렉트
        		 */
        		
        		//단순포워딩 : 모델 호출 없이 페이지만 보여줌
        		RequestDispatcher dispatcher = request.getRequestDispatcher("이동할 페이지");
        		dispatcher.forward(request, response)
        		
        		request.getRequestDispatcher("/WEB-INF/views.red.jsp").forward(request, response)
        ```
        
        <aside>
        💡 getRequestDispatcher("/WEB-INF/views.red.jsp")
        getRequestDispatcher("WEB-INF/views.red.jsp") 차이점
        
        1. 루트 path 다음에 URL을 붙여서 요청
            “~MVCTest(루트)/WEB_INF/~~”
        2. 현재 페이지 path 다음에 이어서 URL을 붙여서 요청
            “~MVCTest(루트)/현재 보여지는 페이지 주소/WEB_INF/~~”
        
        </aside>
        
    
    - 하나의 URL에서 두 파일로 포워딩을 하고싶으면 파라미터를 받아서 if-else문으로 실행문을 나누면 되겠군
        
        ```java
        request.getParameter("파라미터명");
        --------------------------------------------------------------------------------
        
        	protected void doGet(HttpServletRequest request, 
        						 HttpServletResponse response) throws ServletException, IOException {
        		
        		/*
        		 <페이지 이동 서비스>
        		 1. 포워드
        		 2. 리다이렉트
        		 */
        		
        		//단순포워딩 : 모델 호출 없이 페이지만 보여줌
        //		RequestDispatcher dispatcher = request.getRequestDispatcher("이동할 페이지");
        //		dispatcher.forward(request, response);
        		
        		String act = request.getParameter("act"); //url: http://localhost:8080/Day0718/ureca/color?act=red
        		
        		if(act.equals("red")) 
        			request.getRequestDispatcher("/WEB-INF/views/red.jsp").forward(request, response);
        		else
        			request.getRequestDispatcher("/WEB-INF/views/blue.jsp").forward(request, response);
        		
        	}
        ```
        

### 실습코드2 - 로그인.jsp

- 로그인폼
    
    ```java
    <body>
    	<h3>로그인폼</h3>
    	<hr>
    	
    	<!-- 
    		<서버(java) '/' 경로> : Context Root(:8080/MVCTest)
    		예) /MVCTest/ureca/color
    		=> localhost:8080/MVCTest/MVCTest/ureca/color
    		
    		
    		<클라이언트(html,css,js) '/' 경로> : 서버 루트 (:8080/)
    		예) /MVCTest/ureca/color
    		=> localhost:8080/MVCTest/ureca/color
    	
    	-->
    	
    	
    	<form>
    		<!-- 아이디 : <input type="text" name="id"><br>  -->
    		<input type="hidden" name="act" value="login">
    		아이디 : <input type="text" name="id" ><br>
    		비밀번호 : <input type="password" name="pwd"><br>
    		<button>로그인</button>
    	</form>
    	
    	<a href="uplus.com" onclick="return uplus()">유플러스</a>
    	<!-- 하이퍼링크 이동을 막는 방법 : onclick이벤트 false되도록 -->
    </body>
    <script type="text/javascript">
    	function uplus() {
    		return false;
    	}
    </script>
    ```
    
    - `<input type="hidden" name="act" value="login">` : 로그인 버튼 클릭 시 act 파라미터 값 없으면 널익셉션 떠서 그거 안나게 할라고 넣어줌
    - 폼 태그 안에 input 태그 name속성 value속성 ⇒ Submit 버튼 동작하면 바로 해당 name에 해당하는 파라미터에 value전달됨
