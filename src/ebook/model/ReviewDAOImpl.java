package ebook.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReviewDAOImpl {
	// DB 연결 메서드
    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://192.168.0.114:3306/ebook_store?serverTimezone=UTC";
        String user = "team4";
        String pw = "team4";
        return DriverManager.getConnection(url, user, pw);
    }

	// 리뷰 조회(select)
    public ArrayList<ReviewDTO> getReview(int bookId) {
    	ArrayList<ReviewDTO> reviewlist = new ArrayList<ReviewDTO>();
    	String sql = "select * from review where BOOK_ID =?";
    	Connection con = null;
    	PreparedStatement stmt = null;
    	ResultSet rs = null;
    	try {
    		con = getConnection();
    		stmt = con.prepareStatement(sql);
    		stmt.setInt(1, bookId);
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			ReviewDTO review = new ReviewDTO(rs.getInt("RATING"), rs.getString("CONTENT"));
    			reviewlist.add(review);
    		}
    	}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(null, stmt, con);
		}
		return reviewlist;
	}
	
    public boolean hasPurchased(int userId, int bookId) {
        String sql = "select 1 "
                + "from orders o "
                + "join order_item oi on o.id = oi.order_id "
                + "where o.user_id = ? and oi.book_id = ? "
                + "limit 1";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            pstmt.setInt(2, bookId);

            try {
                rs = pstmt.executeQuery();
                return rs.next();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(rs, pstmt, conn);
        }
        return false;
    }
    public int writeReviewMenu(int user_id, int book_id, int rating, String content) {
		String sql = "insert into review (user_id, book_id, rating, content) values (?, ?, ?, ?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, user_id);
			pstmt.setInt(2, book_id);
			pstmt.setInt(3, rating);
			pstmt.setString(4, content);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(null, pstmt, conn);
		}
		return result;
	}
}
