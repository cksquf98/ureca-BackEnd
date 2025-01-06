### CRUD

- C - Create   : insert
- R - Read      : select
- U - Update  : update
- D - Delete   : delete
- 사용자가 입력한 데이터를 디비에 저장하고, 저장한 데이터를 조회 수정 삭제하는 동작

---

### CRUD프로젝트 만들어보기

- Project명: SpringPersonMVC
- 최상위패키지명: com.ureca.person
    - 패키지 : component-scan의 역할
- / : 기본 ConextRoot
    
    com.ureca.person.controller
    com.ureca.person.dto
    com.ureca.person.model.dao
    com.ureca.person.model.service
    

- 로그인/로그아웃
- 파일 업로드
- 페이징 처리 : 3중쿼리 ==> limit

### 요청의 종류

1. 입력폼 (단순포워딩)
    <img width="396" alt="image" src="https://github.com/user-attachments/assets/fe84fa4a-62ec-4a73-8e0f-88255e845cd0" />

2. DB입력처리
3. 리스트
    <img width="410" alt="image" src="https://github.com/user-attachments/assets/e227de05-a867-4f47-b4e5-92db673b10f1" />

4. 수정폼
    <img width="409" alt="image" src="https://github.com/user-attachments/assets/606fe6d9-c7ad-4fad-b78d-5784b657488b" />

5. 삭제요청

### 요청 URL

/person/form    GET
/person/form    POST
/person/list       GET
/person/upform GET
/person/upform POST
/person/delete   GET

---

### SpringPersonMVC 프젝 생성

- WEB-INF/views 하위에 jsp파일 추가

```java
  입력폼.jsp
  <form method="post" action ="form">
    이름: <input type="text" name="name"><br>
    나이: <input type="text" name="age"><br>
    직업: <input type="text" name="job"><br>
   <button>입력</button>
  </form>
  <br>
  <a href="/list">사람목록보기</a>
```

- DB테이블 생성

```sql
use ureca;
drop table if exists person;

create table person(
	no int primary key auto_increment,
	name varchar(20) not null,
  age int not null,
  job varchar(50) not null
);
```

- pom.xml에다가!! 사용할 라이브러리들을 끌어와야함

```java
dependencies에 코드 추가 - **JSP, JSTL**
---------------------------------------------------------------------------------------
		<dependency>
			<groupId>org.apache.tomcat.embed</groupId>
			<artifactId>tomcat-embed-jasper</artifactId>
		</dependency>
		<!-- JSTL Setting -->
		<dependency>
			<groupId>jakarta.servlet.jsp.jstl</groupId>
			<artifactId>jakarta.servlet.jsp.jstl-api</artifactId>
		</dependency>
		<dependency>
			<groupId>org.glassfish.web</groupId>
			<artifactId>jakarta.servlet.jsp.jstl</artifactId>
		</dependency>
```

cf.  JSTL : if문, for문을 태그로 표현할 수 있게 해주는 라이브러리

- application.properties 설정 추가

```
#JSP Setting
spring.mvc.view.prefix=/WEB-INF/views/
spring.mvc.view.suffix=.jsp

#Web Setting
server.servlet.context-path=/
server.port=8080

#DB Connection
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/ureca?serverTimezone=UTC&useUniCode=yes&characterEncoding=UTF-8";
spring.datasource.username=ureca
spring.datasource.password=ureca
```

- PersonController 생성

```java
package com.ureca.person.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // ==Spring Container로 객체 관리 받고 싶어요!
public class PersonController {
	
	/*
	   controller의 역할
	   1. 요청 분석
	   2. 데이터 얻어오기
	   3. 모델 호출
	   4. 모델 호출 결과를 영역에 저장
	   5. 페이지 이동 << 맨 마지막에 정의: return을 통해서!
	 */
	
	
	@GetMapping("/form")
	public String form() {
		//입력폼을 브라우저에 노출
		return "form"; //5. 페이지 이동 (포워드 이동)
	}
	
	@PostMapping("/form")
	public String regist(@RequestParam("name") String name,
			@RequestParam("age") int age,
			@RequestParam("job") String job
			) {
		//regist(HttpServletRequest request) 서블릿 방식과 비교해보자
		
		//DB에 데이터 insert
		~~~~
		
		return "list";
	}
}

=> localhost:8080/person/form 요청
```

- @RequestParam("파라미터") : 문자열로 받아오는데, 인자 타입을 int로 설정해두면 알아서 parseInt되어서 가져옴
    - 파라미터가 많아지면 곤란쓰 ⇒ 객체 Object (=DTO)로 받아오자 !

- 동일한 이름으로 Mapping되게 해도 form Method에 따라 다른 메서드가 동작하게 할 수 있음
    - @RequestMapping(value="/form", method=RequestMethod.GET)
    - @RequestMapping(value="/form", method=RequestMethod.POST)
- 아예 매핑을 어노테이션으로 분류해도 됨
    - @GetMapping("/form")
    - @PostMapping("/form")

- com.ureca.person.dto 생성
    - 기본생성자 : ctrl + shift + Enter
    - getter/setter/오버로딩생성자 : alt + shift + S
    - toString
    - **DTO 변수 이름과 폼태그 name이 같으면 알아서 쏙 ****

- Parameter 변경

```java
	public String regist(@RequestParam("name") String name,
			@RequestParam("age") int age,
			@RequestParam("job") String job
			)
			
	==> public String regist(Person person)
```

- PersonDAO

```java
package com.ureca.person.dao;

import java.util.ArrayList;

import com.ureca.person.dto.Person;

public interface PersonDAO {

	//insert into person(no,name,age,job) values(?,?,?,?) 
	public int insertPerson(Person person); 
	//excuteUpdate의 리턴값은 int -> boolean, String으로 받아도 됨
	
	//update person set age=?, job=? where no=?
	public int updatePerson(Person person);
	
	//delete from person where no=?
	public int deletePerson(int no);
	
	//select * from person
	public List<Person> selectAllPeople();
	
	//수정폼에서 사용
	//select * from person where no=?
	public Person selectPerson(int no);
}
```

- selectAllPeople의 경우 가변배열을 써야함
    - ArrayList로 명시하면 얘밖에 못쓰니까 **인터페이스 List**로 선언

- DBUtil 추가
    - com.ureca.util
    - @Component 어노테이션을 통해 스프링 컨테이너에 등록
    - 이를 통해 PersonDAO에서 DI 가능
    - @Autowired : 스프링 컨테이너에 등록된 객체들 중 일치하는 자료형을 생성해서 Injection
        
        : Controller → Service → Repository 모두 존재해야 동작함 **
        
    - dependency 추가
    
    ```java
    <!-- https://mvnrepository.com/artifact/com.mysql/mysql-connector-j -->
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
    </dependency>
    ```
    

- Component 어노테이션으로 묶인 애들끼리는 한 식구 ⇒ 서로 생성한걸 주고받을 수 있음
- PersonDAOImp - PersonDAO 인터페이스 구현클래스 추가

```java
//스프링 컨테이너의 객체 관리를 받고 싶다! => @Component
@Repository
public class personDAOImp implements PersonDAO {
	@Autowired  //스프링 컨테이너에 등록된 객체들 중 일치하는 자료형을 통해 Injection
	DBUtil db;

	@Override
	public int insertPerson(Person person) throws SQLException {
		Connection conn = db.getConnection();

		String sql = "insert into person (name, age, job) values (?,?,?)";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, person.getName());
		pstmt.setInt(2, person.getAge());
		pstmt.setString(3, person.getJob());
		
		return pstmt.executeUpdate(); //이미 전송되어있는 sql문 실행하라고 요청
	}

	@Override
	public int updatePerson(Person person) throws SQLException  {
		Connection conn = db.getConnection();

		String sql = "update person set age=?,job=? where no=?)";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, person.getAge());
		pstmt.setString(2, person.getJob());
		pstmt.setInt(3, person.getNo());
		
		return pstmt.executeUpdate(); 
	}

	@Override
	public int deletePerson(int no) throws SQLException  {
		Connection conn = db.getConnection();

		String sql = "delete from person where no=?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, no);
		
		return pstmt.executeUpdate();
	}

	@Override
	public List<Person> selectAllPeople() throws SQLException  {
		Connection conn = db.getConnection();
		String sql = "select no,name,age,job from person";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		
		ArrayList<Person> people = new ArrayList<>();
		
		ResultSet rs = pstmt.executeQuery();
		//rs사용법 : 1. 행 얻어오기    2.행안의 열 데이터 얻어오기
		while(rs.next()) {
			//얻어올 행이 존재할때까지 1번 수행
			Person person = new Person(rs.getInt("no"),
					rs.getString("name"),
					rs.getInt("age"),
					rs.getString("job")
					);
			
			people.add(person);
		}
		return people;
	}

	@Override
	public Person selectPerson(int no) throws SQLException  {
		Connection conn = db.getConnection();
		String sql = "select no,name,age,job from person where no=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1,no);
		
		ResultSet rs = pstmt.executeQuery();
		Person person = null;
		//rs사용법 : 1. 행 얻어오기    2.행안의 열 데이터 얻어오기
		if(rs.next()) {
			//얻어올 행이 존재한다면 -> 1번 수행
			person = new Person(rs.getInt("no"),
					rs.getString("name"),
					rs.getInt("age"),
					rs.getString("job")
					);
			}
		return person;
	}
}
```

- PersonService 패키지, 인터페이스 추가

```java
package com.ureca.person.service;

import java.sql.SQLException;
import java.util.List;

import com.ureca.person.dto.Person;

public interface PersonService {
	//Person 추가 수정 삭제 조회에 대한 명세 역할
	
	public int add(Person person) throws SQLException;
	public int edit(Person person) throws SQLException;
	public int remove(int no) throws SQLException;
	public Person read(int no) throws SQLException;
	public List<Person> readAll() throws SQLException;
}

-----------------------------------------------------------------
구현 클래스

public class PersonServiceImp implements PersonService {
	@Autowired
	PersonDAO dao;
	
	
	@Override
	public int add(Person person) throws SQLException {
		return dao.insertPerson(person);
	}

	@Override
	public int edit(Person person) throws SQLException {
		return dao.updatePerson(person);
	}

	@Override
	public int remove(int no) throws SQLException {
		return dao.deletePerson(no);
	}

	@Override
	public Person read(int no) throws SQLException {
		return dao.selectPerson(no);
	}

	@Override
	public List<Person> readAll() throws SQLException {
		return dao.selectAllPeople();
	}

}
```

- select문으로 끌어온 데이터들을 list.jsp에 출력 By JSTL
- JSTL
    - 자바코드를 사용해서 뽑아와서 쓰는게 아니라 한방에
    
    ```java
    <JSTL> JSP Standard Tag Library 표준태그 라이브러리
    - JSP페이지에서 많이 사용되는 논리적인 판단, 반복처리, 포맷처리를 위한
      커스텀 태그를 표준으로 만들어서 정의한 것.
      
    - https://mvnrepository.com
         --------> 검색: jstl
         
    종류)
     1. 코어(변수지원, 흐름제어, URL처리)
    	 - 같은 파일에 있는 같은 이름 → namespace에서 prefix가 붙어서 구분 가능함
    	    ex. <java:class> ≠ <school:class>
    	 - prefix도 같을 경우에 대해 JSTL은 대응방법이 있도다
       - http://java.sun.com/jsp/jstl/core
     ==> <%@taglib prefix="c" uri="jakarta.tags.core" %>
    
     2. XML (XML코어, 흐름제어, XML변환)
       - http://java.sun.com/jsp/jstl/xml
       
     3. 국제화 (지역, 메시지형식, 숫자 및 날짜 형식)
       - http://java.sun.com/jsp/jstl/fmt
       
     4. 데이터베이스(SQL)
       - http://java.sun.com/jsp/jstl/sql
       
     5. 함수(컬렉션 처리, String 처리)  
       - http://java.sun.com/jsp/jstl/functions
       
    <코어태그>
    1. 변수지원 : set, remove   
                JSP페이지 안에서 ----->  <c:set>   <c:remove>  
                
    2. 흐름제어 : if, choose, forEach, forTokens
    
    3. URL처리 : import, redirect, url
    
    4. 기타 : catch, out    
    
    ---------------------------------------------------------------------------------
    <<표현언어>> Expression Language : EL  ★
     - 처음 JSTL(JSP Standard Tag Library)에서 소개
          현재 JSP스펙에 포함되어 사용.
     - 자바빈즈 속성 값을 보다 쉽고 제약을 덜 받은 방법으로 사용하기 위해 나온 것.
     - JSP파일이 useBean액션태그나 표현식등으로 복잡해 지는 것을 막고
          일종의 템플릿코드처럼 사용할 수 있도록 해줌. 
     
     문법)
       - 표현언어는 '$'로 시작
       - 모든 내용을  '{표현식}'과 같이 구성
             ====> ${            }                    ※참고: <script>  $(     ) </script> : jQuery
             ----------                          ---------
                JAVA                              JavaScript
        
       - 표현식에는 기본적으로 변수명 혹은  '키명.속성명' 구조를 갖는다.
                                   ----
                                                    영역객체.setAttribute("키값",vo);
                              request.setAttribute("p", vo);
                              ===> ${p.name}                                                
       
       - 표현식에 부가적으로 숫자, 문자열, boolean, null과 같은 상수값도 가능.
       - 표현식에는 기본적인 연산가능.         
                
         <%
             int su=10;
         %>      
               
         <script>  var i=<%= su %>;   </script>
         <input type=text value=<%= su%>  name=age>
         
         
         <%    <%= su%>    ${  }         %>      
               -------     -----
                  X           X
               
           속성접근)
          ${person.name}   또는  ${person["name"]}  :  out.print(person.getName()) 
          ${row[0]} : row라는 이름의 컬렉션의 첫번째 아이템
          
          ---> 데이터를 영역에 저장
               request.setAttribute("p" , new Person("길동"));
               
          ---> 데이터를 영역에서 조회
               Person p = (Person)request.getAttribute("p");
               out.print(p.getName());
               
          ---> 위의 두 줄을 EL로 표현  : ${p.name}
            
                ${sessionScope.p.name}   <=== request와 session영역에 공통적으로 'p'키값이 정의       
       
         내장객체)
        pageScope, requestScope, sessionScope, applicationScope,
        param, paramValues, header, headerValues, initParam, cookie, pageContext         
               
               
               <%
                 out.print(데이터);  ----> JSP페이지내에서는 out에 대한 자료형 선언을 하지 않았다.
               %>
               
               ${ param.username }
               ----->  String name = request.getParameter("username");
                            out.print(name);         
    ```
    

```java
list.jsp에 JSTL 적용
--------------------------------------------------------------------------------------
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
		      <td>${person.name}</td>
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

```

---

- 여까지 하고 실행!
- 문제점1)
    - 데이터 입력 후 확인 클릭하면 데이터 목록이 안보임
    - 조회하려면 model에다가 people이 전송되어야 하는데
    
    ```java
    List<Person> people = service.readAll(); //3번 수행
    			
    //4번 수행
    model.addAttribute("people",people);
    ```
    
    - regist함수는 return “list”로 이동하니까 model에 전송이 안댐
    
    ```java
    	public String regist(Person person) {
    	//regist(HttpServletRequest request) 서블릿 방식과 비교해보자
    		
    		//DB에 데이터 insert
    		System.out.println("param >>> " + person);
    		try {
    			service.add(person);
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    		
    		***return "list"; //5번 수행***
    	}
    ```
    

- 문제점2)
    - 새로고침 시 데이터가 여러번 제출됨
        
        ⇒ 리다이렉트 사용해야함
        

- 해결법
    - 리다이렉트 시 list가 새로 불러와지니까 1번 문제도 같이 해결됨

```java
	@PostMapping("/form")
	public String regist(Person person) {
	//regist(HttpServletRequest request) 서블릿 방식과 비교해보자
		
		//DB에 데이터 insert
		System.out.println("param >>> " + person);
		try {
			service.add(person); //3번 수행
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		**return "redirect:list"; //5번 수행**
	}
```

---

- 이름 클릭 시 수정폼으로 이동되도록 (no값 전달되도록)

```java
list.jsp에 name부분 출력코드 수정

<td><a href=**"upform?no=${people.no}"**>${person.name}</a></td>
```

- 수정폼 (name은 수정 불가 ㅎ,ㅎ)

```java
Controller에서 model.addAttribute("person", person)를 통해 person 전달된걸 사용

upform.jsp
----------------------------------------------------------------------------------------
<form method="post">
  <input type="hidden" name="no" value="">
    이름: <input type="text" name="name" value=**"${person.name}"** readonly><br>
    나이: <input type="text" name="age" value=**"${person.age}"**><br>
    직업: <input type="text" name="job" value=**"${person.job}"**><br>
   <button type="submit">수정</button>
   <button type="button">삭제</button>
  </form>
```

```java
@GetMapping("/upform") // person/upform?no=1
	public String upform(@RequestParam int no,
			Model model) {//수정폼 보이도록
		
		try {
			Person person = service.read(no);
			model.addAttribute("person", person);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return "upform";
	}
	
	
	@PostMapping("/upform")
	public String modify(Person person) {//DB수정 요청
		try {
			service.edit(person);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "redirect:list";
	}
	
	@GetMapping("/delete")
	public String remove(@RequestParam int no) {//DB삭제 요청
		try {
			service.remove(no);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "redirect:list";
	}
```

---

cf.

public List<DTO> selectBy조건(조건을 표현하는 자료형);  

→ 디비에서 특정 조건으로 검색해서 나온 데이터들 조회

cf.

Form태그에서 Action값 없이 submit된 경우는 새로고침처럼 자기 자신 페이지를 호출함

cf. 

html 주석 **<!--   -->**안에 **${  }** 있으면 무조건 먼저 해석 & 실행해버림

⇒ <%--  --%> 주석 태그로 바꿔라~

cf. Model Interface 더 찾아봐야겄다


---
### Form Action 경로 정리

- form태그 내 action = “path”

<aside>
💡 현재 페이지 : [localhost](http://localhost):8080/path/src/ureca

<form method = “GET” action = “person”
⇒ Request URL = localhost:8080/path/src/ureca

<form method = “GET” action = “/person”
⇒ Request URL = localhost:8080/person

</aside>
