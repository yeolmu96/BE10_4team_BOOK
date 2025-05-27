package ebook.model;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 주문(orders) 테이블 DAO
 */
public class OrdersDTOImpl {
    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/ebook_store?serverTimezone=UTC";
        String user = "team4";
        String pw = "team4";
        return DriverManager.getConnection(url, user, pw);
    }

    //1. 주문 등록 (주문 1건 생성)
    public int insert(OrdersDTO order) {
        String sql = "insert into orders(user_id, order_date, total_amount, pay_used, points_earned, status)"
        		+ " VALUES (?, NOW(), ?, ?, ?, ?)";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, order.getUserId());
            ps.setInt(2, order.getTotalAmount());
            ps.setInt(3, order.getPayUsed());
            ps.setInt(4, order.getPointsEarned());
            ps.setInt(5, order.getStatus());
            int result = ps.executeUpdate();
            if (result == 1) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1); // 생성된 order_id 반환
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // 실패 시
    }

    //2. 내 주문 목록 조회 
    public List<OrdersDTO> getOrdersByUserId(int userId) {
        List<OrdersDTO> list = new ArrayList<>();
        String sql = "select * from orders where user_id=? "
        		+ "order by order_date desc";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Timestamp ts = rs.getTimestamp("order_date");
                LocalDateTime orderDate = ts != null ? ts.toLocalDateTime() : null;
                OrdersDTO order = new OrdersDTO(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        orderDate,
                        rs.getInt("total_amount"),
                        rs.getInt("pay_used"),
                        rs.getInt("points_earned"),
                        rs.getInt("status"),
                        null // item 목록은 아래에서 별도 채움
                );
                list.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    //3. 주문 상세 (order_id로 1건 조회)
    public OrdersDTO getOrderById(int orderId) {
        String sql = "select * from orders where id=?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Timestamp ts = rs.getTimestamp("order_date");
                LocalDateTime orderDate = ts != null ? ts.toLocalDateTime() : null;
                return new OrdersDTO(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        orderDate,
                        rs.getInt("total_amount"),
                        rs.getInt("pay_used"),
                        rs.getInt("points_earned"),
                        rs.getInt("status"),
                        null // item 목록은 아래에서 별도 채움
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //4. 주문 상태 변경 (예: 취소 등)
    public boolean updateStatus(int orderId, int newStatus) {
        String sql = "update orders set status=? WHERE id=?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, newStatus);
            ps.setInt(2, orderId);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
