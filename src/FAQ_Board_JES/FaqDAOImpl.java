package store;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class FaqDAOImpl {
	public ArrayList<FaqDTO> showFaqBoard() {
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
}
