package ebook.model;

import java.sql.*;
import java.util.*;

public class BookDTOImpl {
    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/ebook_store?serverTimezone=UTC";
        String user = "team4";
        String pw = "team4";
        return DriverManager.getConnection(url, user, pw);
    }

    // 1. 도서 등록 (INSERT)
    public boolean insert(BookDTO book) {
        String sql = "INSERT INTO book(title, category, price, description, author) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getCategory());
            ps.setInt(3, book.getPrice());
            ps.setString(4, book.getDescription());
            ps.setString(5, book.getAuthor());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 2. 도서 삭제 (DELETE)
    public boolean delete(int bookId) {
        String sql = "delete from book where book_id=?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, bookId);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 3. 도서 목록 조회 (SELECT)
    public List<BookDTO> getAllBooks() {
        List<BookDTO> list = new ArrayList<>();
        String sql = "select * from book";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new BookDTO(
                    rs.getInt("book_id"),
                    rs.getString("title"),
                    rs.getString("category"),
                    rs.getInt("price"),
                    rs.getString("description"),
                    rs.getString("author"),
                    rs.getInt("sales_count")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 4. 도서 상세 조회 (SELECT by book_id)
    public BookDTO getBookById(int bookId) {
        String sql = "select * from book where book_id=?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, bookId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new BookDTO(
                    rs.getInt("book_id"),
                    rs.getString("title"),
                    rs.getString("category"),
                    rs.getInt("price"),
                    rs.getString("description"),
                    rs.getString("author"),
                    rs.getInt("sales_count")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 5. 도서 정보 수정 
    public boolean update(BookDTO book) {
        String sql = "update book SET title=?, category=?, price=?, description=?, author=? where book_id=?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getCategory());
            ps.setInt(3, book.getPrice());
            ps.setString(4, book.getDescription());
            ps.setString(5, book.getAuthor());
            ps.setInt(6, book.getBookId());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 6. 판매 수량 증가 (구매 시)
    public boolean increaseSalesCount(int bookId, int amount) {
        String sql = "update book set sales_count = sales_count + ? where book_id=?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, amount);
            ps.setInt(2, bookId);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
