### 실습코드 - request / response

<미션>

- request.html과 response폴더의 response1.jsp ~ response6.jsp 복사 → html or txt파일로 변경

- **자바스크립트(Fetch API)** 또는 **jQuery의 $.ajax()함수**를 사용하여 응답페이지 호출하고 결과를 반영
    - 1번버튼: 응답데이터를 ',' 구분해서 ul,li 엘리먼트를 구성하고 div 엘리먼트 내용변경
    each()함수사용!!
    - 2번버튼: input 태그 내의 이름 파라미터를 전달하고 응답데이터를 div 엘리먼트 내용으로 변경
    - 3번버튼: input 태그 내의 이름을 전달하고 응답데이터를 div엘리먼트 내용변경
        
        //엥 2번 3번 똑가튼데
        
    - 4번버튼: 응답데이터 중 '책제목'만  ol,li 엘리먼트를 구성하고 div 엘리먼트 내용변경
        - XML 태그 content 뽑아오는 법 ⇒ getElementsByTagName으로 배열 뽑아오고, for문 돌리면서 객체.**textContent**
    - 5번버튼: 응답데이터에서 이름,나이,직업구분해서 서로 다른 줄에 표시하고 div 엘리먼트 내용변경
    - 6번버튼: 응답데이터중 '책제목'만 ol,li 엘리먼트를 구성하고 빨강마크업후 div 엘리먼트 내용변경

```jsx
* **Fetch API** 파라미터 전달 방법
	    function load2(){
       fetch(`(URL)**?username=${document.querySelector("input[name=uname]").value}**`)
       //username : 파라미터 변수
       
           .then(function(response){
                  console.log(response);
                  // console.log(response.text());
                  
                  if(!response.ok) {//응답이 정상이 아닌 경우
	                  throw new Error("에러발생!") //에러메세지 리턴-> catch문으로 throw
		              }
                  
                  return response.text();
           })
       
           .then(function(data){
                  console.log(data);
                  var content=data;
                  document.getElementById('d1').innerHTML=content;
           })
          
           .catch(function(err) {
              alert('에러메세지: '+err)
       });
    }
```

```jsx
 **jQuery의 $.ajax()함수**
 
 * 엘리먼트 생성
 var ul = $('<ul></ul>')
 
 * innerHTML과 같은 동작
 ul.append($('<li></li>').text(<li> content에 들어갈 텍스트)
 
 
 * 버튼이 클릭된 경우
 window.onload()
 -> 축약 : $(document).ready()
 -> 축약 : $(function(){ $('button:eq(idx)').click(function(){} }
 
 
 * 파라미터 전달 방법
 $.ajax({
   url:'http://10.4.2.100:8080/response/response2.jsp',
   data:{
	   username: $('#uname').val() //username : 파라미터 변수
	   //비교) username: $('input[name=uname]').val()
   },
   
   success:function(result){
	   $('div').html(result);
   },
   
   error:function(jqueryXHR, msg) {
	    alert(jqueryXHR, jqueryXHR.status, jqueryXHR.statusText)
	   	alert(msg)
	 }
});

* document.getElement와 같은 동작
	$(doc).find('title')
	
	
* createElement, appendChild와 같은 동작
	let ol = $('<ol>')
	let li = $('<li>').text(CONTENT)
	ol.append(li)
```

*구글드라이브 코드 다운

---

## MySQL

- mysql-installer-community-8.0.38.0.msi  (303.6M) 다운로드
- MySQL은 문자, 문자열 데이터를 표현할 때 작은 따옴표 사용 권장
- 명령문의 경우 대소문자 구분X
    - 데이터의 경우 대소문자 구분O
- 주석 : #. --, /* */
- 문자열 합쳐서 출력하려면 >> **`concat`함수** 사용

### 데이터베이스

- 여러 사람이 공유하여 사용할 목적으로 통합. 관리하는 데이터 집합

### DBMS

- 디비 관리 시스템 SW
- 기능
    - 데이터베이스를 구축하는 틀을 제공
    - 효율적으로 데이터를 검색하고 저장하는 기능
    - 응용 프로그램들이 데이터베이스에 접근할 수 있는 인터페이스 제공
    - 장애에 대한 복구, 보안 유지 기능 제공
- 개발자, DB관리자, 응용 프로그램 등의 사용자와 데이터베이스 사이에서 매개체 역할을 함
    - 데이터 추가,조회,수정,삭제

## SQL : Structured Query Language

### 1. DDL 데이터 정의어

- 데이터를 객체처럼 취급 ⇒ 객체(table, view, trigger)에 대한 생성.변경.삭제
- DDL문은 **자동 커밋** 대상임 **
    - 수동 커밋 변경 불가능
- 데이터베이스의 논리적인 구조를 정의
    
    ① CREATE
    
    ② DROP
    
    ③ ALTER
    
    ④ RENAME
    
    ⑤ TRUNCATE : 제약 무시하고 전체 삭제
    

### 2. DML 데이터 조작어 ★

- 데이터베이스에 대한 접근 권한 부여등의 데이터베이스 시스템의 관리를 위한 목적으로 사용
    
    ① SELECT : 데이터질의어(DQL) - 인출 + 출력
    
    = 데이터 **Fetch(인출)**가 주 목적임 **
    
    ```sql
    1. SELECT 조회컬럼명
    2. FROM   테이블명
    3. WHERE  (전체행에 대한)조건식
    4. GROUP BY (그룹을 묶을 수 있는 조건을 가진)컬럼명
    5. HAVING  (그룹에 대한)조건식
    6. ORDER BY 정렬 기준 컬럼명;
    
    **※ 실행 순서 : 2-3-4-5-1(*fetch*)-6 ※**
    --------------------------------------------------------------------------------
    
    *** 별칭** :  
    		SELECT 컬럼명 [AS] 별명(띄어쓰기 없다면 따옴표 생략 가능)
        FROM   테이블명;
    
    *** DISTINCT** : 중복제거
    		SELECT DISTINCT 컬럼명
    		FROM   테이블명;
    
    *** WHERE절
    	=>** 기준컬럼명  연산자  비교데이터
    		 비교데이터  연산자  기준컬럼명
    	
    		 - **비교연산자** : > , < , >= , <=, =, !=, <>
    	
    		 - **AND 연산자**
    	
    		 - **OR 연산자** 
    			 => IN연산자로 바꿔쓰는게 간편함
    				ex. where deptno = 20 or deptno = 30;
    						where deptno IN(20,30);
    	
    		 - **LIKE 연산자**
    			 => 컬럼명 LIKE '문자열' : 해당 문자열이 속한 행을 조회
    			    '%문자열' : 0~n개의 문자를 대체 = 앞에 뭐 오던지간에 해당 문자열로 끝나는 경우
    			    '_문자열' : 단 1개의 문자를 대체 = 앞에 문자 1개 있고 해당 문자열로 끝나는 경우
    			    '문자열%' : 해당 문자열로 시작해서 뒤에 문자 올 수도 안올수도
    			    '문자열_ _' : 해당 문자열로 시작해서 뒤에 문자 2개오고 끝
    			    '\%' '\_' : 이스케이프 구분자'\'를 통해 문자로 사용 가능
    			    ...
    
    		 - **IN 연산자
    			 =>** 컬럼명 IN (Value List) : 컬럼의 값이 리스트 내 데이터와 하나라도 일치하는지
    		
    		 - **BETWEEN ~ AND 연산자**
    			 => 컬럼명 BETWEEN a AND b
    			 => a:최소값, b:최대값  ==> a이상 ~ b이하
    			 => 초과, 미만 표현 불가
    	
    		 - **NOT 연산자
    			 =>** 조건에 만족하지 못하는 행을 검색
    				  in, like, between 앞에 사용
    		      null앞에 사용
    	
    *** ORDER BY절
    	=>** ORDER BY (정렬하고자 하는)컬럼명 [**ASC** - 오름차순, 디폴트 | **DESC** - 내림차순];
    		 
    		 여러 컬럼 기준 정렬 : ORDER BY 컬럼명1 [**ASC|DESC**], 컬럼명2 [**ASC|DESC**];
    ```
    
    cf.  ESCAPE옵션  : '%' 또는 '_'를  와일드 카드가 아닌 문자의 의미를 가지고자 할 때 사용.
    
     ==> 컬럼명  LIKE '문자열'  ESCAPE '부호';
    
     ==> escape를 명시하지 않을 경우 '\'가 기본값
    
    cf. Where 컬럼1=300 or 컬럼2=500 or 컬럼3=1400 이런 조건절이 있을 때, 
    
    체크 순서는 **3 → 2 → 1** ***
    
    하나라도 true 나오면 true
    
    ② INSERT
    
    ```sql
    insert into 테이블명 [(컬럼명1, 컬럼명2)] values (데이터1, 데이터2)
    
    * MySQL : 한개의 명령문으로 여러 행 삽입하기
    insert into 테이블명 [(컬럼명1, 컬럼명2)] values **(데이터1, 데이터2),(데이터1, 데이터2)**
    
    * 전체 컬럼에 대한 데이터 추가하는 경우 컬럼명 생략 가능 - 순서 지키기
    ```
    
    ③ UPDATE
    
    ```sql
    update **테이블명** set 컬럼명1 = 변경데이터1, 컬럼명2 = 변경데이터2 [where 조건식]
    ```
    
    ④ DELETE
    
    ```sql
    delete from 테이블명 [where 조건식]
    ```
    

### 3. DCL 데이터 제어어

- 데이터베이스에 대한 접근 권한 부여등의 데이터베이스 시스템의 관리를 위한 목적으로 사용
    
    ① GRANT = 권한 부여
    
    ② REVOKE = 권한 회수
    

### 4. TCL 트랜잭션 제어어 ★

- 트랜잭션 : 논리적인 작업단위(DML)의 묶음
    - 데이터 처리의 한 단위
    - All or Nothing방식
    - MySQL에서는 DDL/DML 작업하면 **자동 커밋**됨
    
    ```sql
    DML 수동 커밋하기 
    
    방법 1) 이건 걍 내가 구글링한거임
    start transaction;  << 명령문을 통해서 수동 트랜잭션으로 바꿔서 수동 커밋
    
    방법 2)
    설정 > SQL Execution > auto commit 체크 해제 > 재시작
    
    방법 3)
    select @@autocommit; //자동 커밋 모드인지 확인 1: True, 0: False
    set autocommit = 0;
    ```
    
    ① COMMIT : 물리적인 반영O
    
    ② ROLLBACK : 물리적인 반영X
    
    ③ SAVEPOINT
    
    ⇒ 얘네 셋 중 하나 나오면 한 트랜잭션 종료인거임
    

---

## SQL 실습코드

- 디비 스키마 생성 및 데이터 추가
    
    [SQL수업.txt](https://prod-files-secure.s3.us-west-2.amazonaws.com/90f0cea1-2c0a-45ef-8fdd-d99b6da3fa09/e4218b5c-17ef-45ce-990b-681c43e65e43/SQL%EC%88%98%EC%97%85.txt)
    
    [urecaDB.sql](https://prod-files-secure.s3.us-west-2.amazonaws.com/90f0cea1-2c0a-45ef-8fdd-d99b6da3fa09/ada3f246-6148-4977-b1cb-3ee4a311706d/urecaDB.sql)
    
    ```
    <사원테이블구성> - EMP
     EMPNO    (사원번호)
     ENAME    (사원명)  
     JOB         (직책)    
     MGR        (매니저,직속상사)
     HIREDATE (고용일,입사일)  
     SAL         (급여)         
     COMM     (커미션,특별수당,성과급여)
     DEPTNO   (사원이 속한 부서번호)          
          
    <부서테이블구성> - DEPT
     DEPTNO   (부서번호) 
     DNAME    (부서명)   
     LOC         (부서위치)    
               
    <급여 등급 테이블구성> - SALGRADE
     GRADE     (등급 1~5)
     LOSAL      (최소급여)
     HISAL       (최대급여)
    ```
    
    <aside>
    💡 describe 테이블명;
    
    * Field, Type, Null, Key, Default, Extra ⇒ 테이블 구조 파악
    * NULL 컬럼 : NOT NULL인지 널 허용인지 체크
    * Key 컬럼 :  MUL(외래키)
    
    </aside>
    
    <aside>
    💡 Create문에서 컬럼명에 백틱 사용 시 예약어도 사용할 수 있음!
    
    </aside>
    
    <aside>
    💡 Select문 ⇒ 출력 기능 사용하기
    
    * select ‘출력문’ from dual  →  컬럼명도 ‘출력문’으로 됨
    ==> dual : 가상테이블, 한 행이 입력되어 있고 특정한 테이블의 참조가 필요없을 때 사용
    
    * select '안녕, SQL~!!' from emp;   →  emp 행 개수만큼 출력
    
    </aside>
    
    <aside>
    💡 SQL 나누기 연산 비교
    
    ```sql
    select 10+3 "더하기", 10-3 "빼기", 10*3 "곱하기", 10/3 "그냥 나누기",
            10%3 "나누기(나머지)",  mod(10,3) "나누기(나머지)",
             floor(10/3) "나누기(몫)"
    from dual;
    ```
    
    </aside>
    

<aside>
💡 명시적 형변환

```sql
select empno, ename, deptno, hiredate
from emp
where hiredate >= convert('19810601', date);    

select empno, ename, deptno, hiredate
from emp
where hiredate >= cast('19810601' as date);    

select cast('19810601' as date), convert('19810601', date)
from dual;

==> cast함수 : cast(데이터 as 변환하고자 하는 Type)
	  convert함수 : convert(데이터, 변환하고자 하는 Type)
```

</aside>

<aside>
💡 **NULL**

* boolean 비교 연산 불가능
* **`*IS NULL`* / *`IS NOT NULL`* /  **`NOT(컬럼 is null)`**로 체크해야함
* **`IN` / `NOT IN`** 연산에서, NULL은 결국 *UNKNOWN*이기 때문에 **조회 조건에 포함되지 않는다!!**

</aside>
