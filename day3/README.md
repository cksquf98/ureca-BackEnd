### JOIN문

- 둘 이상의 테이블에서 데이터가 필요한 경우 Where절에서 테이블 조인 필요
- 조인 조건은 일반적으로 PK 및 FK로 구성
    - PK = UK + NOT NULL
- 조인 시 겹치는 컬럼명의 경우, 해당 컬럼의 테이블명을 명시해야함

### JOIN 종류

- Inner Join
    - 교집합을 출력
        
        그냥 join만 쓰면 크로스조인이고 on절이랑 쓰면 이너조인
        
    - N개의 테이블을 조인할 경우 N-1개의 조인 조건 필요
    - 컬럼 선택 시 테이블명 명시 필요 ⇒ 테이블명에 별칭 사용하면 편함(별칭으로 싹 바꿔야 함)
    
    ```sql
    * 형식
    select 컬럼1, 컬럼2, ..
    from table1 inner join table2
    on table1.컬럼 = table2.컬럼;
    
    * USING 사용 - 공통컬럼 이름이 동일해야함
    select 컬럼1, 컬럼2, ..
    from table1 join table2
    using (공통컬럼);   **<< 공통컬럼에 테이블명 명시X**
    ```
    

- Natural Join - 비권장(컬럼 이름만 같으면 매핑하기 때문에)
    - 알아서 공통 컬럼 찾아서 조인
    - 테이블명 명시 안해줘도 됨
    
    ```sql
    select 컬럼1, 컬럼2, ..
    from table1 natural join table2;
    ```
    
    - 실습문제
        
        ```sql
        # 문제) 사번이 7788인 사원의 사번, 이름, 급여, 부서번호, 부서이름을 출력하시오.
        select empno, ename, sal, emp.deptno, dname
        from emp inner join dept
        on emp.deptno = dept.DEPTNO
        where empno=7788;
        
        select empno, ename, sal, emp.deptno, dname
        from emp join dept
        using(deptno)
        where empno=7788;
        
        select empno, ename, sal, emp.deptno, dname
        from emp natural join dept
        where empno=7788;
        
        select empno, ename, sal, emp.deptno, dname
        from emp, dept
        where emp.deptno = dept.DEPTNO and empno=7788;
        ```
        

- Outer Join
    - join조건에 일치하지 않는 데이터까지 같이 출력되도록 함
    - Left Outer Join : 왼쪽 테이블 기준
    - Right Outer Join
    - **Full Outer Join의 경우 MySQL에서 지원하지 않음 !!**
        
         **→** (구글링) left outer join이랑 right outer join 얘네를 UNION 연산해야된다는디 에바
        
    - 실습문제
        
        ```sql
        #문제) 회사에 근무하는 모든 사원의 사번, 이름, 부서번호, 부서명을 출력하시오. (단, 사원이 속하지 않은 부서에 대한 정보도 함께 출력)
        select empno, ename, dept.deptno, dname
        from emp right (outer) join dept
        on emp.DEPTNO = dept.DEPTNO;
        ```
        

- Self Join
    - 자기 테이블과 셀프로 조인
    - 실습문제
        
        ```sql
        #모든 사원의 사번, 이름, 매니저사번, 매니저이름
        select me.empno "사번", me.ename "사원명", me.mgr "매니저사번", manager.ename as "매니저이름"
        from emp me join emp manager
        on manager.empno = me.mgr; 
        ```
        

- None-Equi Join
    - PK, FK가 아닌 일반 컬럼을 조인 조건으로 지정
    - 실습문제
        
        ```sql
        #모든 사원의 사번, 이름, 급여, 급여등급
        select empno, ename, sal, grade
        from emp join salgrade
        on emp.sal between salgrade.LOSAL and salgrade.HISAL;
        ```
        
    

### 서브쿼리

- 다른 쿼리 내부에 포함된 Select문
    - 메인쿼리 : 서브쿼리를 포함하고 있는 외부 쿼리
- 소괄호 필수
- 단일행 또는 다중행 비교연산자와 함께 사용됨

![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/90f0cea1-2c0a-45ef-8fdd-d99b6da3fa09/ca106dc3-3eed-4d4b-af3f-529621caf924/Untitled.png)

- 종류
    1. 중첩 서브쿼리
        1. 단일행 : 서브쿼리 결과가 단일행을 리턴 ⇒ 비교 연산자 & IN,ANY,ALL 모두 사용 가능
            
            ```sql
            #SCOTT사원과 같은 부서에 근무하는 사원의 사번, 이름, 부서번호를 출력하시오
            	select empno, ename, deptno
            	from emp
            	where deptno = (select deptno from emp where ename = 'SCOTT');
            ```
            
        2. 다중행 : 서브쿼리 결과가 다중행을 리턴 ⇒ ***IN***, ANY, ALL 연산자만 사용 가능
            - IN, ANY : 적어도 하나만 만족하면 True
            - ALL : 모두 만족해야 True
            
            ```sql
            -- 서브쿼리 : 다중행 --
            #SCOTT 이름이 중복되는 행이 있는 경우)
            	select empno, ename, deptno
            	from emp
            	where deptno **IN**(select deptno from emp where ename = 'SCOTT');
            
            ---------------------------------------------------------------------------	
            #30번 부서에 근무하는 사원들 중 최소한 단 한명보다, 급여를 많이 받는 사원의 사번, 이름, 부서번호를 출력하시오
            	select empno, ename, deptno
            	from emp
            	where sal > **ANY**(select sal from emp where deptno=30);
            	== 30번 부서 사람들 급여 중 최솟값 = 950
            	== 950보다 크면 출력됨
            
            	select empno, ename, deptno
            	from emp
            	where sal > (select min(sal) from emp where deptno=30); //얘는 단일행이군
            
            	
            ---------------------------------------------------------------------------	
            #30번 부서에 근무하는 사원들보다 급여를 더 많이 받는 사원의 사번, 이름, 부서번호를 출력하시오
            select empno,ename,deptno
            from emp
            where sal > (select max(sal) from emp where deptno = 30);
            
            select empno,ename,deptno
            from emp
            where sal > **ALL**(select sal from emp where deptno=30);
            ```
            
        3. 다중컬럼
            
            :마이에스큐엘은 다중컬럼 지원 안한댕
            
    2. 인라인뷰 : From절에 사용되는 서브쿼리
        - 동적으로 생성된 테이블(뷰)로 사용 가능
        
    3. 스칼라 서브쿼리 : Select절에 있는 서브쿼리로 한개의 행만 반환

---

### 실습코드

- 내장함수
    
    [0712_내장함수.sql](https://prod-files-secure.s3.us-west-2.amazonaws.com/90f0cea1-2c0a-45ef-8fdd-d99b6da3fa09/ecaa9b74-5101-4870-8639-8d5146c5d4a5/0712_%EB%82%B4%EC%9E%A5%ED%95%A8%EC%88%98.sql)
    

- JOIN

```sql
※아래의 문제들을 ANSI JOIN으로 해결하시오.

문제1) 사원들의 이름, 부서번호, 부서이름을 출력
	select ename, emp.deptno, dname
	from emp join dept
	on emp.deptno = dept.deptno;

문제2) 부서번호가 30번인 사원들의 이름, 직급, 부서번호, 부서위치를 출력
	select ename, job, emp.deptno, loc
	from emp join dept
	on emp.deptno = dept.deptno
	where emp.deptno = 30;

문제3) 커미션을 받는 사원의 이름, 커미션, 부서이름, 부서위치 출력(커미션 0은 제외)
	select ename, comm, dname, loc
	from emp join dept
	using (deptno)
	where comm > 0;  //아니면 comm is not null and comm != 0

문제4) DALLAS에서 근무하는 사원들의 이름, 직급, 부서번호, 부서이름을 출력
	select ename, job, deptno, dname
	from emp join dept
	using(deptno)
	where dept.LOC = 'DALLAS';

문제5) 사원이름에 'A'가 들어가는(포함하는) 사원들의 이름과 부서이름을 출력
	select ename, dname
	from emp join dept
	using(deptno)
	where ename like '%A%';

문제6) 사원이름과 직급, 급여, 급여등급을 출력
	select ename, job, sal, grade
	from emp join salgrade
	on emp.SAL between salgrade.LOSAL and salgrade.HISAL;
	
	
	문제6-1) 부서이름도 함께 출력
		select ename, job, sal, grade, dname
		from emp join salgrade join dept
		on (emp.SAL between salgrade.LOSAL and salgrade.HISAL)
		and (dept.deptno = emp.deptno);

문제7) 사원들의 이름과 그 사원과 같은 부서에 근무하는 사원의 사원명, 부서번호를 출력
       ===> 자기 이름을 제외한 동료(56행) 정보 출력!!
	select my.ename "사원번호", my.deptno "부서번호", other.ename "동료"
	from emp my left join emp other
	on my.deptno = other.deptno
	where my.EMPNO != other.EMPNO;
	
	
추가문제) 모든 사원(14명)들의 사원번호와 사원명 그리고 매니저명을 출력하시오.
	select me.empno "사원번호", me.ename "사원명", ifnull(mgr.ename,'없음') "매니저이름"
	from emp me left join emp mgr
	on me.MGR = mgr.EMPNO;
```

- 서브쿼리 미션

```sql
-- MISSION --
#문제1) SCOTT와 급여가 동일하거나 더 많이 받는 사원의 이름과 급여출력
select ename, sal
from emp
where sal >= **ANY**(select sal from emp where ename='SCOTT'); //다중행 에러 방지

#문제2) 직급(job)이  사원(CLERK)인 사람이 속한 부서의 부서번호와 부서명, 부서위치를 출력.
select emp.deptno, dname, loc
from emp join dept
on emp.deptno = dept.deptno
where job = 'CLERK';

#문제3) 사원명에 'T'를 포함하고 있는 사원들과 같은 부서에서 근무하고 있는 사원의 사원번호와 이름을 출력.
select e.ename, other.empno, other.ename
from emp e join emp other
on e.deptno = other.deptno 
where e.ename like '%T%';

#문제4) 부서위치가 NEW YORK인 모든 사원의 이름, 부서번호를 출력
select ename, emp.deptno
from emp join dept
on emp.DEPTNO = dept.DEPTNO
where loc = 'NEW YORK';

#문제5) SALES부서의 모든 사원의 이름과 급여출력
select ename, sal
from emp
where deptno = (select deptno from dept where dname='SALES');

#문제6) KING에게 보고하는 모든 사원의 이름과 급여를 출력
select e.ename, e.sal
from emp e join emp mgr
on e.MGR = mgr.empno
where mgr.ename = 'KING';

#자신의 급여가 평균 급여보다 많고  이름에 'S'가 들어가는 사원과 동일한 부서에서 근무하는 모든 사원의 사원번호, 사원명, 급여 출력.
select empno, ename, sal
from emp
where deptno IN(select deptno from emp where ename like '%S%' 
		and sal > (select avg(sal) from emp));
```

- 강사님 코드

```sql
#============================= 서브쿼리 미션 ===========================================
#문제1) SCOTT와 급여가 동일하거나 더 많이 받는 사원의 이름과 급여출력.
     select ename, sal
     from   emp
     where  sal   >= any(select sal
                      from emp
                      where ename='SCOTT');   
                      #---> 2500 이상의 사원정보         

     select ename, sal
     from   emp
     where  sal   >= all(select sal
                      from emp
                      where ename='SCOTT');   
                      #---> 3000 이상의 사원정보                      
                      
     ※ 다중행이 될 가능성이 있기 때문에 ANY, ALL 다중행 연산자 사용

    
#문제2) 직급(job)이  사원(CLERK)인 사람이 속한 부서의 부서번호와 부서명, 부서위치를 출력.
   select deptno, dname, loc
   from   dept
   where  deptno   in  (select deptno          # ----> 20  20  10  30
                       from  emp
                       where  job='CLERK');

#문제3) 사원명에 'T'를 포함하고 있는 사원들과 같은 부서에서 근무하고 있는 사원의 사원번호와 이름을 출력.
   select empno, ename
   from   emp
   where  deptno in (select deptno  #---> 20  30  20  30
                    from  emp
                    where ename like '%T%');

#문제4) 부서위치가 NEW YORK인 모든 사원의 이름, 부서번호를 출력
  select ename, deptno
  from   emp
  where  deptno  IN (select deptno        # ----> 10
                   from dept
                   where loc='NEW YORK');

#문제5) SALES부서의 모든 사원의 이름과 급여출력
   select ename, sal
   from   emp
   where  deptno IN (select  deptno
                   from dept
                   where dname='SALES');

#문제6) KING에게 보고하는 모든 사원의 이름과 급여를 출력

  select ename, sal
  from   emp   -- 14명 사원
  where  mgr  IN  (select empno
                   from emp
                   where ename='KING');

#문제7) 자신의 급여가 평균 급여보다 많고  이름에 'S'가 들어가는 사원과
#               ------------------------             ----------
#                (1번째 조건)                        (2번째 조건)
#                ---> 1,2번을 동시에 만족하는 사원의 부서
                
#       동일한 부서에서 근무하는 모든 사원의 사원번호, 사원명, 급여 출력.

#서브쿼리 첫번째 조건)
select deptno,ename,sal from emp 
where sal > (select avg(sal) from emp ); 

#서브쿼리 두번째 조건)  
select deptno,ename,sal from emp 
where ename  like '%S%';

  select empno, ename, sal
  from    emp
  where   deptno IN (select deptno     -- 20 20
                            from emp  -- 14명 사원
                            where  ename  like '%S%'
                            and
                            sal > (select avg(sal) from emp ));
```
