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
    public ArrayList<ReviewDTO> getReview() {
    	ArrayList<ReviewDTO> reviewlist = new ArrayList<ReviewDTO>();
    	String sql = "select * from review";
    	Connection con = null;
    	PreparedStatement stmt = null;
    	ResultSet rs = null;
    	try {
    		con = getConnection();
    		stmt = con.prepareStatement(sql);
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			ReviewDTO review = new ReviewDTO(rs.getString("id"), rs.getString("title"), rs.getInt("grade"), rs.getString("txt"));
    			reviewlist.add(review);
    		}
    	}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
    	return reviewlist;
    	
    }
 
}
