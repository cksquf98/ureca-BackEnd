**PersonDAOImp 없애고 personDAO + person.xml로 구성하기**

# MyBatis

[MyBatis-3-User-Guide_ko.pdf](https://prod-files-secure.s3.us-west-2.amazonaws.com/90f0cea1-2c0a-45ef-8fdd-d99b6da3fa09/8f71c7cc-cedf-4970-86bd-344ebea8a02f/MyBatis-3-User-Guide_ko.pdf)

- SQL Mapper Framework
- Connection, PreparedStatement 같이 겹치는 jdbc애들을 알아서 생성
- JDBC 코드와 수동으로 셋팅하는 파라미터와 결과 매핑을 제거한다

### iBatis와 MyBatis 차이점

- MyBatis
    - 호출객체 : SqlSession
    - DML 호출
    
    ```
    - insert(String id[, Object value])
    - delete(String id[, Object value])
    - update(String id[, Object value])
    
    ※ id : 실제 메소드 이름과 같아야 함!!!!
    ```
    
    - DQL 호출
    
    ```
    - select**One**(String id[, Object value])   : 조회결과가 한개 행일때
    - select**List**(String id[, Object value])     : 조회결과가 두개 이상의 행일때
    ```
    
    - ***NameSpace 필수!***
    
    ```
      예)  <mapper></mapper>                        : 실행에러!!
           <mapper namespace="com.ureca.emp"></mapper>        : 정상실행
    ```
    
    - 파라미터 처리 :  #{param}
        - 기존 물음표 자리를 채워주는 역할
        - 파라미터 한개만 들어오면 이름 상관 없음
    
    - 리턴값 처리 : resultType=”Type”
        - insert, update, delete는 어차피 int 반환하니까(executeUpdate) resultType 명시X
    
    ```
      예)  <select id="select" resultType = "String" parameterType = "String">
              select userid from userTable
              where username = ***#{username}***
           </select>
           
           두개 이상의 컬럼명이라면?
           <select id="" resultType="com.ureca.person.dto.Person">
    	       select no, name, age, job from person
    ```
    
    - parameter 속성 생략 가능
        - 파라미터 타입을 명시하지 않으면 파라미터 타입에 VO or Map을 전달할 수 있음
    
    ```java
    ※DTO를 가져오려면?
    	ParameterType = "com.ureca.person.dto.Person"
    	-> #{ }으로 받기
    ```
    
    - Select문만인진 모르겠는데 result 속성 생략 시 에러남!

### Mapper 인터페이스

- 매핑파일(예:product.xml)에 있는 sql을 인터페이스(예:ProductMapper)로 호출한다
    
    ⇒ Imp구현클래스 없앨 수 있음
    

```java
<작성방법>
1. product.xml의 루트엘리먼트의 namespace속성에 실제 패키지명과 인터페이스명을 명시
   <mapper namespace="패키지명.ProductDAO">

2. Mapper어노테이션사용 - Mapper인터페이스위에 @Mapper표시 (생략가능)
   @Mapper
   public interface ProductDAO { }

~~3. 기존 com.ureca.product.dao패키지에 생성할 객체가 없으므로 주석처리
   @Mapper와 매핑될 패키지를 스캔~~
   <!-- <context:component-scan base-package="com.ureca.product.dao"/> -->
   <mybatis:scan base-package="com.ureca.product.dao"/>

4. 주의: DAO의 메소드명과 매퍼XML파일의 id값은 꼭 일치해야 함. 
```

### DTD - 환경설정

- DTD : xml 작성 규칙을 써놓은 문서 ***
- resource에 mapper폴더 만들기
    - preferences > XML(Wild Web Developer) > 다운로드 external resources
    - person.xml 추가
    
    ```java
    ※ mapper.xml(sql문 작성하는 XML문서)
    
    <?xml version="1.0" encoding="UTF-8" ?>
    <!DOCTYPE mapper
    PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
    "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
    <mapper namespace="com.ureca.person.dao">
    	<insert id="insertPerson" parameterType="com.ureca.person.dto.Person">
    		insert into person (name, age, job) values (#{name},#{age},#{job})
    	</insert>
    	
    	<update id="updatePerson" parameterType="com.ureca.person.dto.Person">
    		update person set age=#{},job=#{} where no=#{}
    	</update>
    	
    	<delete id="deletePerson" parameterType="int">
    		delete from person where no=#{no}
    	</delete>
    	
    	<select id="selectAllPeople" resultType="" parameterType="">
    		select no, name, age, job from person order by no desc
    	</select>
    	
    	<select id="selectPerson" >
    		select no,name,age,job from person where no=#{no}
    	</select>
    </mapper>
    
    ※ DAO의 메소드명과 매퍼XML파일의 id값은 꼭 일치해야 함. ※
    ```
    

- pom.xml > dependencies에 코드 추가

```java
<!-- https://mvnrepository.com/artifact/org.mybatis/mybatis -->
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis</artifactId>
    <version>3.5.16</version>
</dependency>
<!-- https://mvnrepository.com/artifact/org.mybatis.spring.boot/mybatis-spring-boot-starter -->
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>3.0.3</version>
</dependency>
```

- Application Properties > 코드 추가
    - DBCP : DB Connection Pool = javax.sql.DataSource가 사용하는 방법
    - 미리 db커넥션을 만들어둬서 빨랑빨랑 할당해줌

```java
#DB Connection
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/디비이름?serverTimezone=UTC&useUniCode=yes&characterEncoding=UTF-8";
spring.datasource.username=사용자
spring.datasource.password=비번

#MyBatis Setting
mybatis.mapper-locations=classpath:mappers/*.xml
mybatis.type-aliases-package=com.ureca.person.dto
```

- PersonDAO 인터페이스에 @Mapper 어노테이션 추가
    
    ⇒ person.xml 코드 수정
    
    ```java
    <?xml version="1.0" encoding="UTF-8" ?>
    <!DOCTYPE mapper
    PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
    "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
    <mapper namespace="com.ureca.person.dao.PersonDAO">
    	<insert id="insertPerson" parameterType="Person">
    		insert into person (name, age, job) values (#{name},#{age},#{job})
    	</insert>
    	
    	<update id="updatePerson" parameterType="Person">
    		update person set age=#{},job=#{} where no=#{}
    	</update>
    	
    	<delete id="deletePerson" parameterType="int">
    		delete from person where no=#{no}
    	</delete>
    	
    	<select id="selectAllPeople" resultType="Person">
    		select no, name, age, job from person order by no desc
    	</select>
    	
    	<select id="selectPerson" resultType="Person" parameterType="int">
    		select no,name,age,job from person where no=#{no}
    	</select>
    </mapper>
    ```
    

---

**업그레이드 최종 버전 - DAO 인터페이스만 남는다**

XML 파일도 없애버릴 수 있음!

```java
PersonDAO.java 인터페이스

package com.ureca.person.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.ureca.person.dto.Person;

@Mapper
public interface PersonDAO_removeXML {

	@Insert("insert into person (name, age, job) values (#{name},#{age},#{job})") 
	public int insertPerson(Person person) throws SQLException; 
	
	@Update("update person set age=#{},job=#{} where no=#{}")
	public int updatePerson(Person person) throws SQLException;
	
	@Delete("delete from person where no=#{no}")
	public int deletePerson(int no) throws SQLException;
	
	@Select("select no, name, age, job from person order by no desc")
	public List<Person> selectAllPeople() throws SQLException;

	@Select("select no,name,age,job from person where no=#{no}")
	public Person selectPerson(int no) throws SQLException;
}
```

---

### MyBatis 동적SQL

- IF 사용

```java
<select id=”findActiveBlogWithTitleLike”
parameterType=”Blog” resultType=”Blog”>
SELECT * FROM BLOG
WHERE state = ‘ACTIVE’
<if test=”title != null”>
AND title like #{title}
</if>
</select>
```

- CHOOSE , WHEN, OTHERWISE

```java
<select id=”findActiveBlogLike”
parameterType=”Blog” resultType=”Blog”>
		**SELECT * FROM BLOG WHERE state = ‘ACTIVE’**
<choose>
<when test=”title != null”>
		**AND title like #{title}**      //if문
</when>
<when test=”author != null and author.name != null”>
		**AND author_name like #{author.name}**     //else if문
</when>
<otherwise>
		**AND featured = 1**    //else문
</otherwise>
</choose>
</select>
```
