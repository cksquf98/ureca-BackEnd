### 로그인 구현

- DB연결을 위한 파일을 따로 만들면 편함
    ```java
    private final String driverName = "com.mysql.cj.jdbc.Driver";
    	private final String url = "jdbc:mysql://127.0.0.1:3306/ureca?serverTimezone=UTC";
    	private final String user = "ureca";
    	private final String pass = "ureca";
    
    	private static DBUtil instance = new DBUtil();
    
    	private DBUtil() {
    		try {
    			Class.forName(driverName);
    		} catch (ClassNotFoundException e) {
    			e.printStackTrace();
    		}
    	}
    
    	public static DBUtil getInstance() {
    		return instance;
    	}
    
    	public Connection getConnection() throws SQLException {
    		return DriverManager.getConnection(url, user, pass);
    	}
    
    	public void close(AutoCloseable... closeables) {
    		for (AutoCloseable c : closeables) {
    			if (c != null) {
    				try {
    					c.close();
    				} catch (Exception e) {
    					e.printStackTrace();
    				}
    			}
    		}
    	}
    ```
    
- ActionListener
    - 버튼 클릭됐을때 동작하는 메서드를 구현해서 디비에 접근할 수 있도록 해보자
    
    ```java
    * 클래스에 implements ActionListener 추가
    	=> @Override
    		 actionPerformed(ActionEvent e) 메서드 오버라이딩 해줘야 함
    	
    		
    * 로그인 버튼 클릭 시 동작하는 메서드
      @Override
    	public void actionPerformed(ActionEvent e) {
    		System.out.println("Button Clicked");
    
    		String userId = tf_id.getText();
    		String pw = tf_pass.getText();
    
    		loginProcess(userId, pw);
    	}
    	
    	// DB 로그인 처리
    	public void loginProcess(String id, String pw) {
    		try {
    			// 2.연결 객체
    			Connection conn = dbUtil.getConnection();
    
    			// 3.실행 객체
    			Statement stmt = conn.createStatement();
    			String sql = "select count(*) from member where id='" + id + "' and pwd='" + pw+"'";
    							//문자열을 표현하는 작은 따옴표 들어가야 한다는 점 주의 ***
    
    			// 4.결과 저장
    			ResultSet rs = stmt.executeQuery(sql);
    			// 집계함수 한 행만 반환하니까
    			rs.next();
    			int count = rs.getInt(1);
    
    			if (count > 0) {
    				showMsg("로그인 성공");
    				serviceFrame.setVisible(true);
    			} else {
    				showMsg("로그인 실패");
    			}
    			
    			dbUtil.close(rs, stmt, conn);
    
    		} catch (SQLException e) {
    			e.printStackTrace();
    		} finally {
    			//5.자원 반환 - 여기 오는게 정석인데 추가 작업이 필요함
    //			rs.close();
    //			stmt.close();
    //			conn.close();
    		}
    	}
    ```
    

※ 근데 이렇게 구현한 경우 큰 문제가 있음 ※

- ID입력값에 `a' or 1=1 #` 넣으면 로그인 성공함 ( = SQL Injection )
    
    ```java
    String sql = "select count(*) from member where id='a' or 1=1 #(뒷문장 주석화)' and pwd='" + pw+"'";
    ```
    
    - 쿼리로 넘기기 전에 **유효성 검사**를 거치도록 하자!
    - 쿼리문에 들어갈 **데이터**를 따로 **분리**하자!
        
        ⇒ `*PreparedStatement` ****
        

- Statement
    
    ```java
    * Statement에서는 executeQuery("DQL문"),excecuteUpdate("DML문") 메소드를 실행하는 시점에 
      파라미터로 SQL문을 DB에 전달한다.   
    
      String sql="insert into person values (1,'"+name+"',13,'학생')";
      stmt = conn.createStatement();
      stmt.executeUpdate(sql);  
      
      ==> 사용자가 입력한 데이터를 sql문과 조합해서 최종 sql문 생성
      ==> 생성된 sql문 실행!!
      
      
    * 장점 : 사용된 SQL문 전체를 명확히 알 수 있어서 디버깅이 쉽다.
    	단점 : SQL Injection에 취약 (사용자가 입력한 데이터가 SQL문장을 구성하는 것)
    ```
    

- PreparedStatement
    
    ```java
    * PrepareStatement는 커넥션에서 생성하면서 SQL문이 DB에 전송되어진다.
    
    	String sql="insert into person values (?,?,?,?)";
                                             -------
                                             ? : 바인드 변수
      stmt = conn.prepareStatement(sql);
      
      //?의 수만큼 설정(데이터 설정)!!
      stmt.setInt   (1,   1);
      stmt.setString(2,  name);
      stmt.setInt   (3,   13);
      stmt.setString(4,  "학생");
      stmt.executeUpdate();   // **※주의: execute()메소드 내에 sql기입하면 안됨**
      
    * 장점 : bind변수를 사용하여 DB서버에서 파싱된 SQL을 재사용하게 만듬으로써, 
             반복적인 다량의 SQL수행시 성능상 이득이 있다.
             반복 루프를 통해서 하나의 SQL문에 변수값만 입력하며 반복 실행 할 수 있음.
             ★ SQL injection 예방의 방법이 될 수 있다.     
    * 단점 : 오류발생 시, 변수에 입력되는 값을 알 수 없어서 디버깅이 어렵다.
    ```
    

---

### Spring Framework

- EJB의 단점을 극복한 솔루션
- JEE가 제공하는 다수의 기능을 지원하여 JEE를 대체하는 프레임워크가 됨
- DI(Dependency Injection), AOP(Aspect Oriented Programming)와 같은 기능 지원
    - DI 의존성 주입
      <img width="583" alt="스크린샷 2024-11-21 오후 5 35 13" src="https://github.com/user-attachments/assets/9c93aea6-d371-4fea-abb7-4ae2aec276c1">
        <aside>
        💡 외부 데이터를 파라미터를 통해 클래스에 주입시키는 것
        —> By 생성자 / Setter 함수
        
        </aside>
        
    
    - AOP
    
    <aside>
    💡 - 관점 지향 프로그래밍을 지원하는 기술
    - 여러 개의 클래스에서 반복해서 사용하는 코드가 있다면, 해당 코드를 모듈화 하여 공통 관심사로 분리한다.
    
    </aside>
    

- Framework vs. Library
    - 짠별 :  (라이브러리는 개발자가 가져다 쓰는거고 프레임워크는 개발자가 지켜야 하는 규칙)

- 구조
    
    ① POJO : 특정 클래스에 종속되지 않는 자바 클래스 (= 상속받지 않은 자바 클래스)
    
    ② PSA : 환경과 기술 변경에 관계 없이 일관된 방식으로 기술에 접근할 수 있게 하는 설계 원칙
    
    ③ IoC/DI 
    
    IoC 제어의 역전 - 객체 생성(new)을 클래스 내부가 아닌 외부(Spring Container)에서 한 후에 DI를 통해 주입
    
    ④ AOP : 관심사 분리를 통해 SW모듈성을 향상
    

---

### 실습코드 - IOC

- Step1. 강한 결합
    - 소스 자체를 변경해야 값이 바뀜

```java
<HelloTest.java>
package com.ureca.step1;

public class HelloTest {

	public static void main(String[] args) {
		MessageBean msg = new MessageBean();
		msg.sayHello("김찬별");
	}
}

<MessageBean.java>
package com.ureca.step1;

public class MessageBean {
	public void sayHello(String name) {
//		System.out.println("안녕 "+name);
		System.out.println("Hi "+name);
	}
}
```

- Step2. 느슨한 결합 by Interface
    - 클래스만 추가하면 값 변경할 수 있음

```java
<MessageBean.interface>
package com.ureca.step2;

public interface MessageBean {
	//인터페이스는 연결 객체다!!
	//유지보수, 객체간 결합을 느슨하게 하기 위해 사용
	
	public void sayHello(String name); //선언된 메서드
	
}

<MessageBeanKO.java>
public class MessageBeanKO implements MessageBean{

	@Override
	public void sayHello(String name) {
		System.out.println("안녕 "+name);
	}
}

<MessageBeanEN.java>
public class MessageBeanEN implements MessageBean{

	@Override
	public void sayHello(String name) {
		System.out.println("Hi "+name);
	}
}

---------------------------------------------------------------
<메인함수 실행-HelloTest.java>
public class HelloTest {

	public static void main(String[] args) {
		MessageBean msg = new MessageBeanKO();
		msg.sayHello("김찬별");
		
		msg = new MessageBeanEN();
		msg.sayHello("Spring");
		
		msg = new MessageBeanVT();
		msg.sayHello("베트남");
	}
}
```

- Step3. IOC 적용
    - 코드 수정할 필요 없음
    - 패키지에 파일 추가 > applicationContext.xml >
        
        ```java
        <?xml version="1.0" encoding="UTF-8"?>
        <beans xmlns="http://www.springframework.org/schema/beans"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xsi:schemaLocation="http://www.springframework.org/schema/beans
                   http://www.springframework.org/schema/beans/spring-beans.xsd">
        
        	<!-- 
        		스프링컨테이너 : 서비스할 객체를 얘가 알아서 관리해줌 ⇒ 객체 생성, 소멸, DI
        										단, 인터페이스나 추상클래스는 안됨
        	-->
        	
        	관리할 클래스 지정
        	//<bean class="com.ureca.step3.MessageBeanKO"></bean> 
        	//<bean class="com.ureca.step3.MessageBeanEN"></bean> 
        	<bean class="com.ureca.step3.MessageBeanVT"></bean> 
        	이렇게만 변경해주면 텍스트 변경만으로 수정 가넝
        </beans>
        ```
        
    - 프로젝트 우클릭 > Configure > Maven 프젝으로 변환
    
![image](https://github.com/user-attachments/assets/a62e7e7d-055f-4233-9026-a89bf63a24cf)

    여기가 메이븐 저장 위치
    C:\Users\student\.m2\repository\org\springframework\spring-context\6.1.11
    
    - pom.xml에 dependency 추가
    
    ```java
    <version> <build> 사이에 추가
    	<dependencies>
    		<!--
    		https://mvnrepository.com/artifact/org.springframework/spring-context -->
    		<dependency>
    			<groupId>org.springframework</groupId>
    			<artifactId>spring-context</artifactId>
    			<version>6.1.11</version>
    		</dependency>
    
    	</dependencies>
    ```
    
    ```java
    //applicationContext.xml 문서 읽기
    ApplicationContext ctx = new ClassPathXmlApplicationContext("com/ureca/step3/applicationContext.xml");
    													 //ClassPathXmlApplicationContext => beans에서 관리하는 클래스 생성자들을 호출
    													 
    ```
    
    - Main
    
    ```java
    		//applicationContext.xml 문서 읽기
    		ApplicationContext ctx = new ClassPathXmlApplicationContext("com/ureca/step3/applicationContext.xml");
    		
    		//XML문서에 정의된 Bean 가져오기 : ctx.getBean("xml에 정의된 id명")
    //		MessageBean msg = ctx.getBean("msgBean"); 자 = 부 관계임 : 에러!! => 자식 캐스팅 필요
    		
    //		MessageBean msg = (MessageBean) ctx.getBean("msgBean");
    //		MessageBean msg = ctx.getBean("msgBean", MessageBean.class); //타입 정의하면 자식 캐스팅도 필요 없음
    		
    		MessageBean msg = ctx.getBean(MessageBean.class); //클래스가 여러개가 아니라면 아이디 명시하지 않아도 됨. 클래스로 매핑
    		msg.sayHello("Rain");
    ```
    

- 추가 설명

<aside>
💡 PSA 
@Transactional 어노테이션
메소드에 대해서 알아서 트랜잭션으로 만들어주고 커밋, 하나라도 실패하면 롤백해줌

</aside>
