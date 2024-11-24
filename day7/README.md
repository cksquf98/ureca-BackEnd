 ### 스프링부트

- perspective > JAVA로 돌아오기
- 새 프로젝트 > Spring Starter Project (=스프링부트) > Type : Maven
<img width="392" alt="스크린샷 2024-11-24 오후 9 50 35" src="https://github.com/user-attachments/assets/ae5f44cc-8834-45c4-bc85-cc8987ac1a6d">

Package의 의미 = 기존 레거시 프로젝트에서의 <compenent-scan> 태그와 같음 :  관리할 클래스 지정하는 역할

- 기존 Lagacy Project에서 클래스 생성 시 context.xml 파일 내 bean class=~로 생성했는데,
- 스프링부트에서는 클래스 `@Component` 어노테이션만 있으면 같은 역할을 함 ⇒ xml 필요없음
- `@Component` → `@Controller`, `@Repository`(DB관련), `@Service`로 나뉨

- 스프링부트 설정파일
    <br><img width="643" alt="스크린샷 2024-11-24 오후 9 51 04" src="https://github.com/user-attachments/assets/ff86fd6d-424d-4912-ae6b-39d6ad8b50af">
<br>
    
<img width="340" alt="스크린샷 2024-11-24 오후 9 51 31" src="https://github.com/user-attachments/assets/82ecbf61-cd7c-4dd8-aaca-7e48221cdc4c"><br>
- 다이나믹 프젝이랑 똑같이 webapp 폴더 만들어주기
- 거기에 html파일 추가해줘야 white label error 안남

- static 폴더 내에 index.html 생성

- webapp 폴더 내에 index.html 생성

  ⇒ static이 이김!!!!!!!!!!! 우선순위가 높음

- webapp 하위에 WEB-INF 폴더 (웹서버가 인식함)
    - 하위에 views 폴더 생성
    - jsp파일 생성
    - [application.properties](http://application.properties) 설정
        
        ```java
        spring.application.name=HelloBoot
        
        #JSP Setting
        spring.mvc.view.prefix=/WEB-INF/views
        spring.mvc.view.suffix=.jsp
        
        #Web Setting
        server.servlet.context-path=/HelloBoot
        server.port=80
        ```
        

- pom.xml

```java
<dependencies> 태그 안에 추가할 내용

<!-- JSP Setting -->
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

- src/main/java에 UrecaController 추가
    - Component 어노테이션 추가가 빈즈에 클래스 등록한 것과 같어
    - 좀 더 세분화해서 Controller 어노테이션 추가해주면 됨

```java
package com.ureca.controller;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

//@Component
@Controller
public class UrecaController {
	// 경로 설정
	@RequestMapping("/ureca/star")
	public String method1() {

		return "hungry";
		/*
		 * #JSP Setting spring.mvc.view.prefix=/WEB-INF/views
		 * spring.mvc.view.suffix=.jsp
		 * 설정을 해뒀기 때문에 헝그리 파일로 이동
		 */
	}
}
```

- [localhost:8080/employee/findAll](http://localhost:8080/employee/findAll) 주소로 접근

```java
//@Component
@Controller
@RequestMapping("/employee")
public class UrecaController {

	// 경로 설정
	@RequestMapping("/findAll")
	public String method1() {

		return "hungry";
		/*
		 * #JSP Setting spring.mvc.view.prefix=/WEB-INF/views
		 * spring.mvc.view.suffix=.jsp
		 * 설정을 해뒀기 때문에 헝그리 파일로 이동
		 */
	}
}
```
