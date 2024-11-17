- SQL - 1에 내용 추가

---

### NULL

- 미확정 데이터 (같은 행에서 입력되지 않은 데이터)
- 연산, 할당, 비교가 불가능 (결과행에서 배제)

```sql
comm = null (X)
3000 + null ==> null연산은 결국 null
```

- NULL 대체함수
    - NVL함수 없음 << 이거 오라클임
    
    ```sql
    *** IFNULL(컬럼, 대체값)**
    	select empno, ename, ifnull(comm,'없음') as "커미션"
    	from emp;
    
    * COALESCE(컬럼, 대체값) //이건 내가 구글링함
    ```
    

---

### 집계함수 ★

- 전체데이터를 그룹별로 구분하여 통계적인 결과를 구하기 위해 사용
- **결과값은 항상 단행 ****
- **Where절에서 사용 불가 ** ⇒ 서브쿼리로 처리하기!**
- **Having절에서 사용 가능 ****
    
    ```sql
    select empno, ename, sal
    from emp
    where sal > avg(sal);  => error!
    
    ------------------------------------------------------
    
    => select empno, ename, sal
    	 from emp
    	 where sal > (select avg(sal) from emp);  => 실행 O
    ```
    
- NULL은 결과값에서 제외
    
    ⇒ Primary Key같이 중복되지 않고, NULL값이 들어올 수 없는 컬럼에 대해 적용해야 좋음
    
- 그룹함수와 단순컬럼은 함께 사용하는 것이 불가능
    - 해당 컬럼이 그룹으로 묶일 수 있다면 Group By절과 사용 가능
    
    ```sql
    select deptno, avg(sal)
    from emp; ==> error!
    
    select deptno, avg(sal)
    from emp
    group by deptno; ==> 실행O
    ```
    

- 종류
    - SUM(총합), AVG(평균), COUNT(행갯수), MIN(최소값), MAX(최대값)
    
    ```sql
    전체 행의 갯수 구하기 :
    	1. select count(PK) from -
    	2. select count(*) from -
    ```
    

---

### MySQL 내장함수

- 숫자 관련 함수

<img width="627" alt="스크린샷 2024-11-17 오후 7 26 44" src="https://github.com/user-attachments/assets/d4b55825-c01e-4c85-9471-3cf090268ebb">

ceiling( ) : 무조건 올림
floor( ) : 무조건 내림
truncate(숫자, 소수점자릿수) : 버림
round(숫자, 소수점자릿수) : 반올림

- 문자 관련 함수
<img width="636" alt="스크린샷 2024-11-17 오후 7 27 18" src="https://github.com/user-attachments/assets/22ee6934-0220-4939-b163-20284314c16b">

insert ( ) 문자열 시작위치 : 1번부터 시작

```sql
* 추가 내장함수

-- hello ureca !!!
select reverse('!!! aceru olleh')
from dual;

-- hello ureca !!!, hello ureca !!!
select lower('hELlo UreCa !!!'), lcase('hELlo UreCa !!!')
from dual;

-- HELLO URECA !!!, HELLO URECA !!!
select upper('hELlo UreCa !!!'), ucase('hELlo UreCa !!!')
from dual;

-- hello, ca !!!
select left('hello ureca !!!', 5), right('hello ureca !!!', 6)
from dual;
-> left : 왼쪽에서 시작해서 5개
	 right : 오른쪽에서 시작해서 6개
```

- 날짜 관련 함수 - 짱많음
    - 현재 시간 : NOW( ), SYSDATE( ), CURRENT_TIMESTAMP( )
    - 날짜 더하기/빼기
    <img width="498" alt="스크린샷 2024-11-17 오후 7 27 56" src="https://github.com/user-attachments/assets/a1e1307a-a242-49dc-b82c-d9fa24924d86">

    ```sql
    select date_add(now(), interval 5 second) 5초후,
           date_add(now(), interval 5 hour) 5시간후, 
           date_add(now(), interval -7 day) 7일전,
           date_sub(now(), interval 1 month) 한달전
    from dual;
    ```
    
    - 날짜 리턴 함수
    <img width="613" alt="스크린샷 2024-11-17 오후 7 28 16" src="https://github.com/user-attachments/assets/9847fedd-34cc-4ec5-aefa-e95acaeedaa5">

    - 날짜 형식
        
        %Y : 2024
        
        %y : 24
        
        %M : JUL
        
        %m : July
        
        …
        

- 논리 관련 함수

```sql
1. IF (논리식, 값1, 값2) : 논리식이 참이면 값1, 거짓이면 값2 리턴
	
2. IFNULL (값1, 값2) : 값1이 null이면 값2 리턴, null 아니면 값1 리턴

3. NULLIF (값1, 값2) : 값1 = 값2면 TRUE 리턴, 아니면 FALSE 리턴

------------------------------------------------------------
select if(3 > 2, '크다', '작다'), 
			 if(3 > 5, '크다', '작다'), 
       nullif(3, 3), 
       nullif(3, 5), 
       ifnull(null, 'b'), 
       ifnull('a', 'b')
from dual;

-- 크다  작다  3  b  a
```
