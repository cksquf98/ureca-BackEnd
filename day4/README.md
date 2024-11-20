<6주차수업> <br>
- JDBC API, DAO, VO <br>
- Spring 환경설정,  SpringBoot 개념과 구조 <br>
- Spring Web MVC Architecture, Web MVC <br>
- Mybatis 개요, Dao를 이용한 호출 <br>
- Rest API 개념과 구조 <br>

---

### 서브쿼리

- create문
    - 테이블 생성 시 서브쿼리 사용 가능
    - **근데!! 제약조건은 복사 안됨!!!!! ****
    
    ```sql
    * 테이블 구조, 데이터 복사
    	create table 테이블명 
    	(select * from 카피할 테이블);
    	
    * 테이블 구조만 복사
    	create table 테이블명
    	(select * from 카피할 테이블
    	where 1 = 0); 
    	=> 아무 행도 선택안됨
    	
    ```
    
- insert문
    
    ```sql
    * VALUES 키워드를 안쓰는군...
    insert into 테이블명
    (select * from emp
    where deptno = 10);
    ```
    
- update문
    
    ```sql
    update 테이블명
    set sal = sal + 50
    where sal > (select avg(sal) from emp);
    ```
    

- delete문
    
    ```sql
    delete from 테이블명
    where sal > (select avg(sal) from emp);
    ```
    

---

### DDL

- Create문
    - 테이블 생성
    
    ```sql
    1.
    create table (
    	컬럼명1 TYPE [optional attr],
    	컬럼명2 TYPE [optional attr]
    	)
    
    2.	
    create table(
    	컬럼명1 TYPE,
    	컬럼명2 TYPE,
    	[constraint 제약조건이름] 제약조건(컬럼명1),
    	[constraint 제약조건이름] 제약조건(컬럼명2)
    	)
    	
    	
    * TYPE : INT, NUMERIC, VARCHAR, DATETIME
    * optional attr : NOT NULL, DEFAULT, AUTO INCREMENT, UNSIGNED, PRIMARY KEY
    	여러개 지정하고 싶으면 띄어쓰기로 여러개 넣으면 됨 ~.~
    ```
    
- Alter문
    - 컬럼 추가
    
    ```sql
    alter table 테이블명 add column 컬럼명 TYPE 제약조건;
    ```
    
    - 컬럼 변경 / 컬럼 이름 변경
    
    ```sql
    alter table 테이블명 modify column 컬럼명 TYPE 제약조건;
    
    alter table 테이블명 change column 기존컬럼명 변경컬럼명 TYPE 제약조건;
    ```
    
    - 컬럼 삭제
    
    ```sql
    alter table 테이블명 drop column 컬럼명;
    ```
    
    - 테이블 이름 변경
    
    ```sql
    alter table 이전테이블명 rename 변경테이블명;
    ```
    
    - 제약조건 추가
    
    ```sql
    alter table 테이블명 add [constraint] primary key(컬럼명);
    
    (구글링) - not null 조건 추가
    alter table emp_cp modify 컬럼명 TYPE not null;
    ```
    

### 제약조건

- PK, FK 쓰는 이유 : ***데이터 무결성 *****
- Create문에서 추가하기

```sql
* NOT NULL : 널값 입력 불가

* UNIQUE : 중복값 저장 불가 & **NULL 허용****

* PRIMARY KEY : Unique + Not Null

* FOREIGN KEY : 특정 테이블 PK컬럼을 참조하는 키 & **NULL 허용**
		1. 컬럼명 TYPE references 테이블명(컬럼명) --> 이케 하려니까 적용안됨(안되는 버전이 있대)
		2. [constraint 이름] foreign key(컬럼명) references 테이블명(컬럼명)
		

* DEFAULT : 널값 들어오면 기본값으로 저장

* CHECK : 들어올 수 있는 값의 범위나 종류 지정
```

- Alter문에서 추가하기

```sql
ALTER TABLE 테이블명
ADD 컬럼명 컬럼타입 NOT NULL;

ALTER TABLE 테이블명	
ADD 컬럼명 컬럼타입 UNIQUE;

ALTER TABLE 테이블명
ADD [CONSTRAINT 제약조건이름] UNIQUE (컬럼명);

ALTER TABLE 테이블명
ADD 컬럼명 컬럼타입 PRIMARY KEY

ALTER TABLE 테이블명
ADD [CONSTRAINT 제약조건이름] PRIMARY KEY (컬럼명)

ALTER TABLE 테이블명
ADD [CONSTRAINT 제약조건이름] FOREIGN KEY (컬럼명) REFERENCES 테이블명 (컬럼명)

※참고
ON UPDATE CASCADE : 참조하는 테이블에서 값 수정되면 연쇄 수정
ON DELETE CASCADE : 참조하는 테이블에서 값 삭제되면 연쇄 삭제
ON DELETE SET NULL : 참조하는 테이블에서 값 삭제되면 자식테이블 값을 NULL로 변경
```

### VIEW

- 가상 테이블로, 실제 데이터를 물리적으로 저장하고 있지는 않음
- Select문을 저장하는 것임
- 사용목적
    - 테이블 전체가 아닌 필요한 필드만 보여줄 수 있음 ⇒ 보안성
    - 복잡한 쿼리 단순화
- 뷰는 인덱스를 가질 수 없음

- 뷰 생성

```sql
create ***[or replace]*** view 뷰이름
**AS**
(select 컬럼1, ..
from 테이블1, ..
where 조건식);
```

- 뷰 수정
    
    ```sql
    alter view 뷰이름
    as
    select 컬럼1, ..
    from 테이블;
    ```
    

- 뷰 삭제
    
    ```sql
    drop view 뷰이름;
    ```
    

- 뷰에서 INSERT하는 경우
    
    ```sql
    뷰에 INSERT를 한다는 것은 테이블의 일부분에만 데이터를 추가하는 것이 됩니다. 
    => '뷰에 INSERT를 하는 데에는 제한이 있다.'
    	 ex. not null 제약조건이나 join, union, 서브쿼리 등을 사용하는 뷰
    ```
    

---

### JDBC (Java DataBase Connectivity)

- 응용 프로그램과 데이터베이스를 연결해주는 기술을 인터페이스로 제공하는 것
- JDBC 관련 클래스 - java.sql 패키지
    - DriverManager : Driver등록, Connection 연결작업 등
    - Driver : DB를 만드는 Vendor(Oracle, MS-SQL,MySQL등)를 implements하여 자신들의 DB를 연결할 수 있는 class를 만드는 인터페이스
    - **Connection :** DB와 연결성을 갖는 인터페이스
    - **Statement :** SQL문을 실행하는 인터페이스
    - **ResultSet :** 조회된 결과 데이터를 갖는 인터페이스

- JDBC Programming
    1. Driver Loading - 디비 선택
    2. Connection 연결 객체 생성
        1. DB주소/포트번호/연결계정 필요
    3. Statement 실행 객체 생성 → SQL문 실행 요청
        1. `executeUpdate` : insert, update, delete문 수행 시 사용 ⇒ 적용된 행의 수 int를 리턴
        2. `executeQuery` : select문 수행 시 사용 ⇒ ResultSet 리턴
    4. ResultSet 결과 객체 생성 → 행/열 단위 데이터 저장
        
        ```sql
        ResultSet rs <- 참조변수로, 행/열 데이터 저장
        
        * rs.next() : 행/열 데이터 한 줄씩 가져옴 
        							다음 행이 존재하면 true 리턴 후 커서를 그 행으로 옮김
        							없으면 false 리턴
        							==> if문 or while문과 함께 사용
        
        * rs.getInt("컬럼명" | select문 내 idx)
        * rs.getString("컬럼명" | select문 내 idx)
        * rs.getDate("컬럼명" | select문 내 idx)
        
        cf. select문 내 컬럼 인덱스는 1부터 시작임 ㅎ.ㅎ
        ```
        

- 이클립스 > jdbc라이브러리 추가

![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/90f0cea1-2c0a-45ef-8fdd-d99b6da3fa09/6cf3b609-6c5e-45fa-849e-60a1ab384fbf/Untitled.png)

```sql
 		// 1.제품군 선택 - Class.forName("Driver 클래스명")
		Class.forName("com.mysql.cj.jdbc.Driver");

		// 2.연결객체 생성
		String url = "jdbc:mysql://127.0.0.1:3306/ureca?serverTimezone=UTC&useUniCode=yes&characterEncoding=UTF-8";
					 //url에서의 ureca는 데이터베이스명을 말함
		String user = "ureca"; // ip:port에 접속할 유저 아이디
		String pw = "ureca";

		Connection conn = DriverManager.getConnection(url, user, pw);
		System.out.println("DB연결 성공");

		
		// 3.실행 객체 생성 - 미션1. dept테이블에 부서 추가하기 => 50/IT/선릉
		Statement stmt = conn.createStatement();
		
		String query = "insert into dept values(50,'IT','선릉')";
		
		int rowCnt = stmt.executeUpdate(query);
		
		if (rowCnt > 0) {
			System.out.println("success");
		} 
		else {
			System.out.println("fail");
		} // 디비 자동 커밋 설정 안되어있다면 commit을 따로 해야 반영됨

		// 4.결과 객체 생성 - 미션2. 20번 부서에 근무하는 사원의 사원번호, 사원명, 급여를 출력하시오
		query = "select empno, ename, sal from emp where deptno = 20";
		ResultSet rs = stmt.executeQuery(query);
		while (rs.next()) {
			System.out.println("empno: " + rs.getInt(1));
			System.out.println("ename: " + rs.getString("ename"));
			System.out.println("sal: " + rs.getInt(3));
		}
```
