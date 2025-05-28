package ebook.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * 드라이버로딩,커넥션,자원반납 등
 * 모든 DAO클래스에서 공통으로 처리하는 기능을 정의
 * 1.드라이버 로딩
 * 	-한번 실행
 * -static블럭
 * 2.DB서버 연결
 * 	-sql문이 실행될때마다 항상 DB서버에 먼저 연결해야하므로 메소드마다 정의
 * 	-메소드로 정의
 * 	-Connection을 리턴
 * 	-static메소드로 정의
 * 3.자원반납
 * 	-메소드마다 작업이 끝나면 모든 자원을 반납하도록 처리
 * 	-메소드를 정의
 * 	-모든 자원에 대한 반납을 하나의 메소드에서 처리)따로 하나씩 만들어도 좋음)
 * 	-static메소드
 */
public class DBUtil {
	// 드라이버로딩
	// => 클래스가 로딩될때 한번 실행된다.
	static {
//		System.out.println("static 블럭");
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// DB서버 접속하기
	public static Connection getConnection() {
		Connection con = null;
		String url = "jdbc:mysql://192.168.0.114:3306/ebook_store?serverTimezone=UTC";
		String user = "team4";
		String password = "team4";
		try {
			con = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}

	// 자원 반납
	public static void close(ResultSet rs, Statement stmt, Connection con) {
		try {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (con != null)
				con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// 따로 작업도 가능
	// 자원 반납 메서드 추가
	public static void close_each(AutoCloseable ac) {
		if (ac != null) {
			try {
				ac.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}