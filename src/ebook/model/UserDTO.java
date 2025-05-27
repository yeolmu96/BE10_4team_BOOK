package ebook.model;

import java.time.LocalDate;
import java.util.Objects;

public class UserDTO {
    private String id;
    private String password;
    private String name;
    private String email;
    private LocalDate joinDate;
    private int pay;
    private int point;
    private boolean isAdmin;

    // 기본 생성자
    public UserDTO() {}

    // 회원가입용 생성자
    public UserDTO(String id, String password, String name, String email, boolean isAdmin) {
        this(id, password, name, email, LocalDate.now(), 0, 0, isAdmin);
    }

    // 전체필드 생성자 (DB 조회 등에서 사용)
    public UserDTO(String id, String password, String name, String email, LocalDate joinDate,
                   int pay, int point, boolean isAdmin) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
        this.joinDate = joinDate;
        this.pay = pay;
        this.point = point;
        this.isAdmin = isAdmin;
    }

    // getter
    public String getId() { return id; }
    public String getPassword() { return password; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public LocalDate getJoinDate() { return joinDate; }
    public int getPay() { return pay; }
    public int getPoint() { return point; }
    public boolean isAdmin() { return isAdmin; }

    // setter
    public void setPassword(String password) { this.password = password; }
    public void setEmail(String email) { this.email = email; }
    public void setPay(int pay) { this.pay = pay; }
    public void setPoint(int point) { this.point = point; }

    // toString/equals/hashCode (편의상 오버라이딩)
    @Override
    public String toString() {
        return String.format("ID: %s, 이름: %s, 이메일: %s, 가입일: %s, 잔액: %d, 포인트: %d, 관리자: %s",
                id, name, email, joinDate, pay, point, isAdmin ? "O" : "X");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDTO)) return false;
        UserDTO user = (UserDTO) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
