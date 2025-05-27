package ebook.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//주문상품
public class Order_itemDTOImpl {
    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/ebook_store?serverTimezone=UTC";
        String user = "team4";
        String pw = "team4";
        return DriverManager.getConnection(url, user, pw);
    }

    //1. 주문상품 등록(여러 건 동시 추가 지원)
    public boolean insertItems(List<Order_itemDTO> items, int orderId) {
        String sql = "INSERT INTO order_item(ORDER_ID, BOOK_ID, QUANTITY) VALUES (?, ?, ?)";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            for (Order_itemDTO item : items) {
                ps.setInt(1, orderId);
                ps.setInt(2, item.getBookId());
                ps.setInt(3, item.getQuantity());
                ps.addBatch();
            }
            int[] results = ps.executeBatch();
            for (int r : results) {
                if (r != Statement.SUCCESS_NO_INFO && r != 1) return false;
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //2. 특정 주문(order_id)의 주문상품 목록 조회 
    public List<Order_itemDTO> getItemsByOrderId(int orderId) {
        List<Order_itemDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM order_item WHERE ORDER_ID=?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Order_itemDTO(
                        rs.getInt("id"),
                        rs.getInt("ORDER_ID"),
                        rs.getInt("BOOK_ID"),
                        rs.getInt("QUANTITY")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
