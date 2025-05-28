package ebook.model;
/*
 *
id(auto)
user_id
book_id
rating
content
created_at(auto)
 *
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReviewDAOImpl {
	//리뷰 작성
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
}
