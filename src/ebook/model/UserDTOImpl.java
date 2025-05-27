package ebook.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

//Customer테이블을 엑세스하는 모든 기능을 구현
public class UserDTOImpl {
	// 회원등록
	public int insert(UserDTO user) {
		String sql = "insert into ebook_store values(?,?,?,?,sysdate())";
		Connection con = null;
		PreparedStatement ptmt = null;
		int result = 0;
		try {
			con = DBUtil.getConnection();
			System.out.println("연결성공" + con);
			ptmt = con.prepareStatement(sql);
			System.out.println("Statement객체생성=>" + ptmt);
			ptmt.setString(1, user.getId());
//			ptmt.setString(2, user.getPass());
//			ptmt.setString(3, user.getName());
//			ptmt.setString(4, user.getAddr());
			result = ptmt.executeUpdate();
			System.out.println(result + "개 행 삽입성공");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(null, ptmt, con);
		}
		return result;
	}

	// 로그인메소드
	//pk로 비교한 경우에는 무조건 조회된 레코드는 1개 - 레코드 1개만 리턴되므로 DTO로 변환해서 리턴하는것이 일반적
	public UserDTO Login(String id, String pass) {
		String sql = "select * from customer where id=? and pass=?";
		UserDTO user =null;
		try (Connection con = DBUtil.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setString(1, id);
			pstmt.setString(2, pass);
			//pk로 비교시 리턴값은 무조건 한개밖에 없다 고로 if처리
//			try (ResultSet rs = pstmt.executeQuery();) {
//				if(rs.next()) {
//					user= new UserDTO(rs.getString(1), rs.getString(2), rs.getString(3),
//							rs.getString(4), rs.getDate(5));
//				}
//			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return user;
		}

	}

	public String Update(String id, String addr) {
		String sql = "update customer set addr=? where id=?";
		String result = null;
		try (Connection con = DBUtil.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setString(1, addr);
			pstmt.setString(2, id);
			int rs = pstmt.executeUpdate();
			// 5.실행결과 처리
			if (rs > 0) {
				result = rs + "개 update 성공";
			} else {
				result = rs + "개 update 실패";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return result;
		}
		// 실무에서는 미리커넥션을 만들어놓고 요청이 들어오면 쳐내는형식
	}

	// 회원탈퇴
	public int delete(String id) {
		String sql = "delete from customer where id=? ";
		int result = 0;
		try (// 2.DBMS에 접속
				Connection con = DBUtil.getConnection();
				// 3. SQL문을 실행하기 위한 객체를 생성
				PreparedStatement ptmt = con.prepareStatement(sql);) {
			ptmt.setString(1, id);
			// 4. SQL문 실행
			int rs = ptmt.executeUpdate();
			// 5.실행결과 처리
			result = rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return result;
		}
	}

	// 회원 전체목록 조회
	public ArrayList<UserDTO> findByArr(String addr) {
		String sql = "select * from customer where addr=?";
		// 조회된 Customer 테이블의 모든 데이터를 담아서 리턴할 자료구조
		ArrayList<UserDTO> customerlist = new ArrayList<UserDTO>();
		try (Connection con = DBUtil.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setString(1, addr);
			ResultSet rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
//			while (rs.next()) {
//				UserDTO customer = new UserDTO(rs.getString(1), rs.getString(2), rs.getString(3),
//						rs.getString(4), rs.getDate(5));
//				customerlist.add(customer);
//			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return customerlist;
		}
	}

	// 회원 전체목록 조회
	// 레코드를 DTO로 변환해서 ArrayList에 담아서 리턴
	public ArrayList<UserDTO> Select() {
		String sql = "select * from customer";
		// 조회된 Customer 테이블의 모든 데이터를 담아서 리턴할 자료구조
		ArrayList<UserDTO> customerlist = new ArrayList<UserDTO>();
		try (// 2.DBMS에 접속
				Connection con = DBUtil.getConnection();
				// 3. SQL문을 실행하기 위한 객체를 생성
				PreparedStatement pstmt = con.prepareStatement(sql);) {
			// 4. SQL문 실행
			ResultSet rs = pstmt.executeQuery();
			// 모든 데이터에 접근을 하는게 아니라 sql문에서부터 조건을 걸어라
//			while (rs.next()) {
//				UserDTO customer = new UserDTO(rs.getString(1), rs.getString(2), rs.getString(3),
//						rs.getString(4), rs.getDate(5));
//				customerlist.add(customer);
//			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return customerlist;
		}

	}

}
