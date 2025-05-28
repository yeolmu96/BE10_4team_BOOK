package ebook.model;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 주문(orders) 테이블 DAO
 */
public class OrdersDTOImpl {
	private static BookDTOImpl bao = new BookDTOImpl();
	private static UserDTOImpl uao = new UserDTOImpl();
    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://192.168.0.114:3306/ebook_store?serverTimezone=UTC";
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
    public boolean purchase(UserDTO user, String bookId, int quantity, int payType) {
        Connection con = null;
        try {
            con = getConnection();
            con.setAutoCommit(false); // 트랜잭션 시작

            // 1. 주문 생성 (pay_type 제거!)
            String orderSql = "INSERT INTO orders(user_id, order_date, total_amount, pay_used, points_earned, status)"
                + " VALUES (?, NOW(), ?, ?, ?, ?)";
            PreparedStatement orderPs = con.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS);

            int totalAmount = bao.getBookPrice(Integer.parseInt(bookId)) * quantity;
            int payUsed = (payType == 1) ? totalAmount : 0;   // 페이로 결제면 payUsed에 금액, 포인트 결제면 0
            int pointsEarned = (int)(totalAmount * 0.05);     // 예시: 결제금액의 5% 포인트 적립
            int status = 1; // 1: 결제완료, 기타 값 필요시 추가

            orderPs.setInt(1, user.getId());
            orderPs.setInt(2, totalAmount);
            orderPs.setInt(3, payUsed);
            orderPs.setInt(4, pointsEarned);
            orderPs.setInt(5, status);

            orderPs.executeUpdate();

            // 생성된 order_id 받기
            ResultSet rs = orderPs.getGeneratedKeys();
            int orderId = -1;
            if (rs.next()) {
                orderId = rs.getInt(1);
            } else {
                con.rollback();
                return false;
            }

            // 2. 주문 상세(도서 1권) 등록
            String itemSql = "INSERT INTO order_item(order_id, book_id, quantity) VALUES (?, ?, ?)";
            PreparedStatement itemPs = con.prepareStatement(itemSql);
            itemPs.setInt(1, orderId);
            itemPs.setInt(2, Integer.parseInt(bookId));
            itemPs.setInt(3, quantity);

            itemPs.executeUpdate();

            // 3. 결제 방식에 따라 실제 차감 처리
            if (payType == 1) {
                uao.usePay(user.getId(), totalAmount); // 페이 차감
            } else if (payType == 2) {
                uao.usePoint(user.getId(), totalAmount); // 포인트 차감
            }
            uao.addPoint(user.getId(), pointsEarned); // 적립은 항상

            con.commit();
            return true;
        } catch (Exception e) {
            if (con != null) try { con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
            return false;
        } finally {
            if (con != null) try { con.setAutoCommit(true); con.close(); } catch (SQLException ex) { ex.printStackTrace(); }
        }
    }


    public boolean purchaseCart(UserDTO user, int payType) {
        Connection con = null;
        try {
            con = getConnection(); // DB 연결 (auto-commit 기본 true)

            // [문제1: 장바구니(ORDER_ID IS NULL) 내 "내 도서"만 합산해야 한다]
            // => 반드시 user_id 컬럼 기준으로 SELECT
            String sumSql = "SELECT SUM(b.price) AS total_amount " +
                            "FROM order_item oi " +
                            "JOIN book b ON oi.BOOK_ID = b.book_id " +
                            "WHERE oi.user_id = ? AND oi.ORDER_ID IS NULL";
            PreparedStatement sumPs = con.prepareStatement(sumSql);
            sumPs.setInt(1, user.getId()); // 반드시 int형 PK id (user 테이블 PK)
            ResultSet sumRs = sumPs.executeQuery();
            int totalAmount = 0;
            if (sumRs.next()) {
                totalAmount = sumRs.getInt("total_amount");
            }
            sumRs.close();
            sumPs.close();

            // [문제2: 주문 insert시도]
            String orderSql =
                "INSERT INTO orders(user_id, order_date, total_amount, pay_used, points_earned, status) " +
                "VALUES (?, NOW(), ?, ?, ?, ?)";
            PreparedStatement orderPs = con.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS);
            int payUsed = (payType == 1) ? totalAmount : 0;
            int pointsEarned = (int)(totalAmount * 0.05);
            int status = 1;

            orderPs.setInt(1, user.getId()); // user 테이블의 id(PK)
            orderPs.setInt(2, totalAmount);
            orderPs.setInt(3, payUsed);
            orderPs.setInt(4, pointsEarned);
            orderPs.setInt(5, status);

            orderPs.executeUpdate();

            // [생성된 주문(order) PK값 받기]
            ResultSet rs = orderPs.getGeneratedKeys();
            int orderId = -1;
            if (rs.next()) {
                orderId = rs.getInt(1);
            } else {
                System.out.println("주문 생성 실패");
                return false;
            }
            rs.close();
            orderPs.close();

            // [문제3: 내 장바구니(order_item에서 ORDER_ID IS NULL, user_id=내 id)만 주문상세로 전환]
            String updateSql = "UPDATE order_item SET ORDER_ID = ? WHERE user_id = ? AND ORDER_ID IS NULL";
            PreparedStatement updatePs = con.prepareStatement(updateSql);
            updatePs.setInt(1, orderId);
            updatePs.setInt(2, user.getId());
            int updatedRows = updatePs.executeUpdate();
            updatePs.close();

            // [문제4: 결제 방식에 따라 유저 pay/point 차감 및 적립]
            // 반드시 user의 PK id만 사용!
            if (payType == 1) {
                uao.usePay(user.getId(), totalAmount); // 페이 차감
            } else if (payType == 2) {
                uao.usePoint(user.getId(), totalAmount); // 포인트 차감
            }
            uao.addPoint(user.getId(), pointsEarned); // 포인트 적립

            System.out.println("구매 성공!");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("구매 실패(에러): " + e.getMessage());
            return false;
        } finally {
            if (con != null) try { con.close(); } catch (SQLException ex) { ex.printStackTrace(); }
        }
    }





    public boolean addToCart(int bookId, int user_id) {
        // 장바구니는 order_id == NULL로 표시
        String sql = "INSERT INTO order_item (ORDER_ID, BOOK_ID, QUANTITY, user_id) VALUES (?, ?, ?, ?);\r\n" + 
        		"";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setNull(1, java.sql.Types.INTEGER); // ORDER_ID는 NULL(장바구니 상태)
            ps.setInt(2, bookId);
            ps.setInt(3, 1); // ebook이면 1 고정
            ps.setInt(4, user_id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
