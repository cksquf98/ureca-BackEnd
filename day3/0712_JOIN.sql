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



#문제) 회사에 근무하는 모든 사원의 사번, 이름, 부서번호, 부서명을 출력하시오. (단, 사원이 속하지 않은 부서에 대한 정보도 함께 출력)
select empno, ename, dept.deptno, dname
from emp right join dept
on emp.DEPTNO = dept.DEPTNO;


#모든 사원의 사번, 이름, 매니저사번, 매니저이름
select me.empno "내 사번", me.ename "내 이름", me.mgr "매니저사번", manager.ename as "매니저이름"
from emp me join emp manager
on manager.empno = me.mgr; 


#모든 사원의 사번, 이름, 급여, 급여등급
select empno, ename, sal, grade
from emp join salgrade
on emp.sal between salgrade.LOSAL and salgrade.HISAL;

-- ----- Join미션 ----- --
#문제1) 사원들의 이름, 부서번호, 부서이름을 출력
select ename, emp.deptno, dname
from emp inner join dept
on emp.deptno = dept.DEPTNO;

#문제2) 부서번호가 30번인 사원들의 이름, 직급, 부서번호, 부서위치를 출력
select ename, job, emp.deptno, loc
from emp join dept
on emp.deptno = dept.DEPTNO
where emp.deptno = 30;

#문제3) 커미션을 받는 사원의 이름, 커미션, 부서이름, 부서위치 출력(커미션 0은 제외)
select ename, comm, dname, loc
from emp join dept
using (deptno)
where comm > 0;

#문제4) DALLAS에서 근무하는 사원들의 이름, 직급, 부서번호, 부서이름을 출력
select ename, job, deptno, dname
from emp join dept
using(deptno)
where dept.LOC = 'DALLAS';

#문제5) 사원이름에 'A'가 들어가는(포함하는) 사원들의 이름과 부서이름을 출력
select ename, dname
from emp join dept
using(deptno)
where ename like '%A%';

#문제6) 사원이름과 직급, 급여, 급여등급을 출력
select ename, job, sal, grade
from emp join salgrade
on emp.SAL between salgrade.LOSAL and salgrade.HISAL;

-- 부서이름 출력 추가
select ename, job, sal, grade, dname
from emp join salgrade join dept
on (emp.SAL between salgrade.LOSAL and salgrade.HISAL)
	and (dept.deptno = emp.deptno);


#문제7) 사원들의 이름과 그 사원과 같은 부서에 근무하는 사원의 사원명, 부서번호를 출력 ===> 자기 이름을 제외한 동료(56행) 정보 출력!!
select my.ename "사원번호", my.deptno "부서번호", other.ename "동료"
from emp my left join emp other
on my.deptno = other.deptno
where my.EMPNO != other.EMPNO;

#모든 사원번호 사원명 매니저이름
select me.empno "사원번호", me.ename "사원명", ifnull(mgr.ename,'없음') "매니저이름"
from emp me left join emp mgr
on me.MGR = mgr.EMPNO;