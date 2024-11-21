package com.ureca;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.ureca.util.DBUtil;

@SuppressWarnings("serial")
public class LoginForm extends JFrame implements ActionListener {
	public JTextField tf_id;
	public JPasswordField tf_pass;
	public JButton bt_login, bt_join;
	JLabel la_id, la_pass, la_img;

	JFrame serviceFrame;

	// DB 연결 객체 생성
	DBUtil dbUtil;

	public LoginForm() {
		// 디비 연결 객체 할당
		dbUtil = DBUtil.getInstance();

		setTitle("Login Form");

		tf_id = new JTextField();
		tf_pass = new JPasswordField();

		bt_login = new JButton("로그인");
		bt_join = new JButton("신규가입");

		la_id = new JLabel("I  D");
		la_pass = new JLabel("Password");

		tf_id.setBounds(80, 30, 100, 25);
		tf_pass.setBounds(80, 65, 100, 25);
		bt_login.setBounds(90, 110, 80, 25);
		bt_join.setBounds(190, 30, 90, 25);
		la_id.setBounds(8, 30, 80, 25);
		la_pass.setBounds(8, 65, 90, 25);

		setLayout(null);
		add(tf_id);
		add(tf_pass);
		add(bt_login);
		add(bt_join);
		add(la_id);
		add(la_pass);

		setBounds(400, 300, 300, 180);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		serviceFrame = new JFrame("서비스화면");
		la_img = new JLabel(new ImageIcon("img/10.jpg"));
		serviceFrame.add(la_img);
		serviceFrame.setBounds(200, 200, 600, 300);

//		serviceFrame.setVisible(true);

		// 로그인버튼 액션리스너 추가
		bt_login.addActionListener(this);
	}// 생성자

	public void showMsg(String msg) {
		JOptionPane.showMessageDialog(this, msg);
	}

	public static void main(String[] args) {
		new LoginForm();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Button Clicked");

		String userId = tf_id.getText();
		String pw = tf_pass.getText();

		loginProcess(userId, pw);
	}

	// DB 로그인 처리
	public void loginProcess(String id, String pw) {
		try {
			// 2.연결 객체
			Connection conn = dbUtil.getConnection();

			// 3.실행 객체
//			Statement stmt = conn.createStatement();
//			String sql = "select count(*) from member where id='" + id + "' and pwd='" + pw+"'";
							//문자열을 표현하는 작은 따옴표 들어가야 한다는 점 주의 ***

			String sql = "select count(*) from member where id=? and pwd=?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			
			
			// 4.결과 저장
//			ResultSet rs = stmt.executeQuery(sql);
			ResultSet rs = pstmt.executeQuery();
			
			
			// 집계함수는 한 행만 반환하니까
			rs.next();
			int count = rs.getInt(1);
			
			
			
			

			if (count > 0) {
				showMsg("로그인 성공");
				serviceFrame.setVisible(true);
			} else {
				showMsg("로그인 실패");
			}
			
			dbUtil.close(rs, pstmt, conn);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//5.자원 반환 - 여기 오는게 정석인데 추가 작업이 필요함
//			rs.close();
//			stmt.close();
//			conn.close();
		}
	}

}// LoginForm
