-- WHERE절 --
-- 사원명에 '%'문자가 포함된 사원의 정보(사원번호,사원명,급여)를 출력하시오
insert into emp(empno, ename, sal) values (8000, '홍%길동_',2500), 
										  (9000,  '나주원%' , 2500), 
                                          (10000, '%김주원', 2500);

select empno, ename, sal from emp where ename like '%\%%';
select empno, ename, sal from emp where ename like '%\_%';
rollback;


-- IN 연산자 --
-- 1. 커미션을 300, 500, 1400 받는 사원의 정보(사원번호, 사원명, 부서번호, 커미션)를 출력하시오.
select empno, ename, deptno, comm
from emp
where comm IN (300,500,1400);


-- BETWEEN 연산자 --
-- 1. 급여가 1600 이상이고 3000 이하인 사원의 정보(사원번호, 사원명, 급여)를 출력하시오.
select empno, ename, sal
from emp
where sal between 1600 and 3000;


-- NOT 연산자 --
-- 1. 30번 부서에 근무하지 않는 사원의 정보(사원번호,사원명,부서번호,직책)를 출력하시오. 
select empno, ename, deptno, job
from emp
where deptno != 30;

-- 2. 이름에 'A'를 포함하지 않는 사원의 사원번호, 사원명, 부서번호를 출력하시오.
select empno, ename, deptno, job
from emp
where ename not like '%A%'; 

-- 4. 급여가 1600 미만 또는 3000 초과인 사원의 사원번호, 사원명, 급여를 출력.
select empno, ename, sal
from emp
where sal not between 1000 and 3000;



-- UPDATE문 --  
-- 1. 사원테이블에서 30번 부서에 근무하는 사원들의 급여를 10% 인상하시오
update emp set sal = sal+sal*0.1 where deptno=30;
select * from emp where deptno=30;
rollback;


-- 커미션을 받는 사원의 사원번호, 사원명, 부서번호, 커미션을 출력하시오.
select empno, ename, deptno, comm
from emp
where comm > 0;

-- 커미션을 받을 조건이 되는 사원의 사원번호, 사원명, 부서번호, 커미션을 출력하시오.
select empno, ename, deptno, comm
from emp
where comm is null;

-- 커미션을 받지 않는 사원의 사원번호, 사원명, 부서번호, 커미션을 출력하시오.
select empno, ename, deptno, comm
from emp
where comm = 0;



--  NULL데이터 --
-- 1. 전체사원의 사원번호, 사원명, 급여, 연봉(comm포함)을 출력하시오.
select empno, ename, sal, sal*12+COALESCE(comm,0) as "연봉"
from emp; 

-- 2. 사원들의 사원번호, 사원명, 커미션을 출력하되 커미션을 받지않는(커미션의 값이 null) 사원들은 '없음'으로 출력하시오. 
select empno, ename, ifnull(comm,'없음') as "커미션"
from emp; 

-- 3. 10번 부서에 근무하는 사원들의 사원번호, 사원명, 직책, 매니저(상사)를 출력하시오. (단, 매니저가 없는 경우 'CEO'를 출력하시오.)
select empno, ename, job,deptno, ifnull(mgr, 'CEO') as "상사"
from emp
where deptno=10;



-- Order By절 --
-- 1. 30번 부서에 근무하는 사원의 사원번호, 사원명, 급여를 출력하되 사원번호에 대해 내림차순으로 정렬하시오.
select empno, ename, sal
from emp
where deptno=30
order by empno desc;

-- 2. 30번 부서에 근무하는 사원의 사원번호, 사원명, 급여를 출력하되 사원명에 대해 오름차순으로 정렬하시오.
select empno, ename, sal
from emp
where deptno=30
order by ename;

-- 4. 가장 최근에 입사한 사원부터 사원번호, 사원명, 급여, 입사일을 출력하시오.
select empno, ename, sal, hiredate
from emp
order by hiredate desc;

-- 5. 급여가 많은 순으로 사원번호, 사원명, 급여를 출력하시오. (단, 급여가 같을 경우 사원명을 기준으로 오름차순 출력하시오.)
select empno, ename, sal
from emp
order by sal desc, ename; 



-- 집계함수 --
-- 1. 사원들의 전체 급여 총합을 구하시오. 
select sum(sal)
from emp;

-- 2. 사원들의 평균 급여를 구하시오. 
select avg(sal)
from emp;



-- 급여를 제일 많이 받는 TOP 3 사원의 사번, 사원이름, 급여를 출력하시오. << limit없이 >> -> row_number
select empno, ename, sal
from emp
order by sal desc;

-- 가장 오래 근무한 사원의 입사일을 구하시오.
select min(hiredate)
from emp; 

-- 부서별 평균 급여를 구하시오.(부서번호가 작은 부서부터 출력)
select deptno, avg(sal)
from emp
group by deptno
order by deptno;

-- 부서의 평균 급여가 2000 이상인 부서의 부서번호와 평균 급여를 출력하시오.
select deptno, avg(sal)
from emp
group by deptno
having avg(sal) >= 2000;

-- 평균급여(==> 2073.21429)보다 더 많은 급여를 받은 사원의 사원번호, 사원명, 급여를 출력하시오.
select empno, ename, sal
from emp
where sal > (select avg(sal) from emp);

-- 급여가 1000 이상인 사원들에 대해서만 부서별로 평균을 구하고 이 중에 평균 급여가 2000 이상인 부서의 부서번호와 평균급여를 출력하시오.
select deptno, avg(sal)
from emp
where sal >= 1000
group by deptno
having avg(sal) >= 2000;

-- 내장함수 --
-- 1. 숫자 관련 함수
select abs(-5),abs(0),abs(+5)
from dual;

select ceiling(12.2), ceiling(0), ceiling(-12.2)
from dual;

select floor(12.2), floor(0), floor(-12.2)
from dual;

select truncate(12.2345, 0), truncate(12.2345, 1)
from dual;

select round(15.2345, 0), round(15.2345, 1), round(15.2345, -1), round(15.2345, -2)
from dual;

-- 2. 문자 관련 함수

