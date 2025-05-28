package ebook.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class FaqDAOImpl {
	//조회
	public ArrayList<FaqDTO> getAllFaq() {
		String sql = "select * from faq";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ArrayList<FaqDTO> faqList = new ArrayList<FaqDTO>();
		ResultSet rs = null;
		
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				FaqDTO faq = new FaqDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4));
				faqList.add(faq);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, pstmt, conn);
		}
		return faqList;
	}
	//등록
	public int registerFaqMenu(String title, String content) {
		String sql = "insert into faq (title, content) values (?, ?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(null, pstmt, conn);
		}
		return result;
	}
	//수정
	public int updateFaqMenu(int faqId, String content) {
		String sql = "update faq set (content = ?) where id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, content);
			pstmt.setInt(2, faqId);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(null, pstmt, conn);
		}
		return result;
	}
	//삭제
	public int deleteFaqMenu(int faqId) {
		String sql = "delete from faq where id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, faqId);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(null, pstmt, conn);
		}
		return result;
	}
}
