package ebook.model;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * UserDTOImpl
 * - user 테이블(DB)과 연동되는 DAO 클래스
 * - 회원가입/로그인/정보수정/탈퇴/목록 등 기능 구현
 */
public class UserDTOImpl {
    // DB 연결 메서드
    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://192.168.0.114:3306/ebook_store?serverTimezone=UTC";
        String user = "team4";
        String pw = "team4";
        return DriverManager.getConnection(url, user, pw);
    }

    //1. 회원가입 (INSERT)
    public boolean insert(UserDTO user) {
        String sql = "INSERT INTO user(user_id, password, name, email, joined_at, is_admin, pay_balance, points_balance) "
                   + "VALUES (?, ?, ?, ?, NOW(), ?, 0, 0)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getUserId());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getName());
            ps.setString(4, user.getEmail());
            ps.setBoolean(5, user.isAdmin());
            // pay_balance, points_balance는 0으로 자동 입력
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 2. 로그인 (user_id, password로 조회)
    public UserDTO login(String userId, String password) {
        String sql = "SELECT * FROM user WHERE user_id=? AND password=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userId);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Timestamp ts = rs.getTimestamp("joined_at");
                LocalDateTime joinedAt = ts != null ? ts.toLocalDateTime() : null;
                return new UserDTO(
                        rs.getInt("id"),
                        rs.getString("user_id"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email"),
                        joinedAt,
                        rs.getBoolean("is_admin"),
                        rs.getInt("pay_balance"),
                        rs.getInt("points_balance")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //3. 회원 정보 수정 (이메일/비번/페이/포인트)
    public boolean update(UserDTO user) {
        String sql = "UPDATE user SET password=?, email=?, pay_balance=?, points_balance=? WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getPassword());
            ps.setString(2, user.getEmail());
            ps.setInt(3, user.getPayBalance());
            ps.setInt(4, user.getPointsBalance());
            ps.setInt(5, user.getId());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //4. 회원탈퇴 (id로 삭제)
    public boolean delete(int id) {
        String sql = "DELETE FROM user WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 5. user_id 중복체크 (회원가입시)
    public boolean isUserIdDuplicate(String userId) {
        String sql = "SELECT COUNT(*) FROM user WHERE user_id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //6. 전체 회원 목록 (관리자용)
    public List<UserDTO> getAllUsers() {
        List<UserDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM user";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Timestamp ts = rs.getTimestamp("joined_at");
                LocalDateTime joinedAt = ts != null ? ts.toLocalDateTime() : null;
                list.add(new UserDTO(
                        rs.getInt("id"),
                        rs.getString("user_id"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email"),
                        joinedAt,
                        rs.getBoolean("is_admin"),
                        rs.getInt("pay_balance"),
                        rs.getInt("points_balance")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 7. pay/points 증감(충전)
    public boolean updatePayBalance(int id, int amount) {
        String sql = "UPDATE user SET pay_balance = pay_balance + ? WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, amount);
            ps.setInt(2, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean updatePointsBalance(int id, int amount) {
        String sql = "UPDATE user SET points_balance = points_balance + ? WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, amount);
            ps.setInt(2, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
