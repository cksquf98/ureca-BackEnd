-- 서브쿼리 : 단일행 --
#SCOTT사원과 같은 부서에 근무하는 사원의 사번, 이름, 부서번호를 출력하시오
select empno, ename, deptno
from emp
where deptno = (select deptno from emp where ename = 'SCOTT');


-- 서브쿼리 : 다중행 --
-- insert into emp (empno, ename, sal, hiredate, deptno) values(8000, 'SCOTT', 3100, now(), 30);
select count(*)
from emp;

select empno, ename, deptno
from emp
where deptno = (select deptno from emp where ename = 'SCOTT'); -- >> 에러남(단일행 아님)

select empno, ename, deptno
from emp
where deptno IN(select deptno from emp where ename = 'SCOTT');

rollback;


#30번 부서에 근무하는 사원들 중 최소한 단 한명보다 급여를 많이 받는 사원의 사번, 이름, 부서번호를 출력하시오
select empno, ename, deptno
from emp
where sal > ANY(select sal from emp where deptno=30);

select empno, ename, deptno
from emp
where sal > (select min(sal) from emp where deptno=30);


#30번 부서에 근무하는 사원들보다 급여를 더 많이 받는 사원의 사번, 이름, 부서번호를 출력하시오
select empno,ename,deptno
from emp
where sal > (select max(sal) from emp where deptno = 30);

select empno,ename,deptno
from emp
where sal > ALL(select sal from emp where deptno=30);


-- 인라인뷰 --
#모든 사원의 평균 급여보다 적게 받는 사원들과 같은 부서에 근무하는 사원들의 사번, 이름, 급여, 부서번호를 출력하시오
select e.empno, e.ename, e.sal, e.deptno
from emp e, (select DISTINCT deptno
			 from emp 
			 where sal < (select avg(sal) from emp)) salary
where e.deptno = salary.deptno;


-- MISSION --
#문제1) SCOTT와 급여가 동일하거나 더 많이 받는 사원의 이름과 급여출력
select ename, sal
from emp
where sal >= ANY(select sal from emp where ename='SCOTT');

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

#문제7) 자신의 급여가 평균 급여보다 많고 이름에 'S'가 들어가는 사원과 동일한 부서에서 근무하는 모든 사원의 사원번호, 사원명, 급여 출력.
select empno, ename, sal
from emp
where deptno IN(select deptno from emp where ename like '%S%' 
		and sal > (select avg(sal) from emp));