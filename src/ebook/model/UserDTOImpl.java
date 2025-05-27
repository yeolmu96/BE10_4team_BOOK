package ebook.model;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// DAO: UserDTO <-> DB 테이블 연동
public class UserDTOImpl {
    // DB 연결 메서드(실무에선 별도 DBUtil 클래스에서 관리)
    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/ebookdb?serverTimezone=UTC";
        String user = "ebook";
        String pw = "ebook";
        return DriverManager.getConnection(url, user, pw);
    }

    /** 1. 회원가입 */
    public boolean insert(UserDTO user) {
        String checkSql = "SELECT COUNT(*) FROM user WHERE id=?";
        String insertSql = "INSERT INTO user(id, password, name, email, join_date, pay, point, is_admin) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement psCheck = conn.prepareStatement(checkSql);
             PreparedStatement psInsert = conn.prepareStatement(insertSql)) {

            // 중복 ID 검사
            psCheck.setString(1, user.getId());
            ResultSet rs = psCheck.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return false; // 이미 존재하는 ID
            }

            // 신규 회원 저장
            psInsert.setString(1, user.getId());
            psInsert.setString(2, user.getPassword());
            psInsert.setString(3, user.getName());
            psInsert.setString(4, user.getEmail());
            psInsert.setDate(5, Date.valueOf(user.getJoinDate()));
            psInsert.setInt(6, user.getPay());
            psInsert.setInt(7, user.getPoint());
            psInsert.setBoolean(8, user.isAdmin());

            int result = psInsert.executeUpdate();
            return result == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** 2. 로그인 (ID, PW로 회원정보 반환) */
    public UserDTO Login(String id, String pw) {
        String sql = "SELECT * FROM user WHERE id=? AND password=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            ps.setString(2, pw);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // DB에서 조회된 값으로 UserDTO 객체 생성
                return new UserDTO(
                        rs.getString("id"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getDate("join_date").toLocalDate(),
                        rs.getInt("pay"),
                        rs.getInt("point"),
                        rs.getBoolean("is_admin")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // 로그인 실패
    }

    /** 3. 회원정보 수정 */
    public boolean update(UserDTO user) {
        String sql = "UPDATE user SET password=?, email=?, pay=?, point=? WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getPassword());
            ps.setString(2, user.getEmail());
            ps.setInt(3, user.getPay());
            ps.setInt(4, user.getPoint());
            ps.setString(5, user.getId());

            int result = ps.executeUpdate();
            return result == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** 4. 회원탈퇴(삭제) */
    public boolean delete(String id) {
        String sql = "DELETE FROM user WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            int result = ps.executeUpdate();
            return result == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** 5. 전체 회원 목록 조회 (관리자용) */
    public List<UserDTO> getAllUsers() {
        List<UserDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM user";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new UserDTO(
                        rs.getString("id"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getDate("join_date").toLocalDate(),
                        rs.getInt("pay"),
                        rs.getInt("point"),
                        rs.getBoolean("is_admin")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
