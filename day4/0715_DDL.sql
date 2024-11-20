use ureca;

-- 서브쿼리문 활용 --
create table emp_cp
select * from emp
where 1=0;

drop table emp_cp;

drop table emp_cp;

insert into emp_cp
(select * from emp
where deptno=10);

update emp_cp
set sal = sal + 50
where sal > (select avg(sal) from emp);

savepoint s1;

delete from emp_cp
where sal > (select avg(sal) from emp);

rollback to s1;

update emp set deptno = 20
where deptno = 10;


-- 미션 --
# 미션1) emp테이블과 같은 구조, 같은 데이터를 갖는 emp_copy테이블을 생성하시오. (== 기존테이블 참조하여 정의하는 테이블)
create table emp_cp
(select * from emp);

desc emp_cp;

alter table emp_cp add constraint primary key(empno);
alter table emp_cp modify ename varchar(10) not null;

# 미션2) emp_copy테이블에서 30번 부서 사원들의 정보(사원번호, 사원명, 급여, 부서번호)를 참조하게 emp_view 뷰를 생성하고 조회하시오.
create or replace view emp_view
as
(select empno, ename, sal, deptno
from emp_cp
where deptno = 30);
select * from emp_view;

drop table if exists emp_cp;

insert into emp_view (empno, ename, sal, deptno)
values (1000,'k',3500,30);

select * from emp_cp;

# 미션3) emp_copy테이블에서 30번 부서 사원들의 정보(사원번호, 사원명, 급여, 급여등급, 부서번호, 부서명)를 참조하게 
# emp_view30 뷰를 생성하고 emp_view30을 조회하시오.
create view emp_view30
as
(select empno, ename, sal, grade, emp_cp.deptno, dname
from emp_cp join dept
	on emp_cp.deptno = dept.deptno
    join salgrade
    on sal between losal and hisal
    where emp_cp.deptno = 30
);

select * from emp_view30;
select * from emp_cp;
select * from dept;

insert into emp_view30 (empno, sal, deptno)
values(8010, 2300, 20);
