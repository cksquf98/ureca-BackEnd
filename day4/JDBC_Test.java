package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBC_Test {

	public static void main(String[] args) throws Exception {
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
//		int rowCnt = stmt.executeUpdate(query);
		
//		if (rowCnt > 0) {
//			System.out.println("success");
//		} 
//		else {
//			System.out.println("fail");
//		} // 디비 자동 커밋 설정 안되어있다면 commit을 따로 해야 반영됨

		// 4.결과 객체 생성 - 미션2. 20번 부서에 근무하는 사원의 사원번호, 사원명, 급여를 출력하시오
		query = "select empno, ename, sal from emp where deptno = 20";
		ResultSet rs = stmt.executeQuery(query);
		while (rs.next()) {
			System.out.println("empno: " + rs.getInt(1));
			System.out.println("ename: " + rs.getString("ename"));
			System.out.println("sal: " + rs.getInt(3));
			System.out.println("============================");
		}
	}
}
