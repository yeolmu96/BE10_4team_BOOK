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

    // 회원가입 시 사용
    public UserDTO(String id, String password, String name, String email, boolean isAdmin) {
        this(id, password, name, email, LocalDate.now(), 0, 0, isAdmin);
    }

    // 모든 필드 입력 생성자 (DB조회/테스트용)
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

    // getter만 (id, name, joinDate)
    public String getId() { return id; }
    public String getPassword() { return password; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public LocalDate getJoinDate() { return joinDate; }
    public int getPay() { return pay; }
    public int getPoint() { return point; }
    public boolean isAdmin() { return isAdmin; }

    // setter (가변 정보만)
    public void setPassword(String password) { this.password = password; }
    public void setEmail(String email) { this.email = email; }
    public void setPay(int pay) { this.pay = pay; }
    public void setPoint(int point) { this.point = point; }

    // toString 오버라이드 (디버깅, 로그, 테스트에 유용)
    @Override
    public String toString() {
        return "UserDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", joinDate=" + joinDate +
                ", pay=" + pay +
                ", point=" + point +
                ", isAdmin=" + isAdmin +
                '}';
    }

    // equals/hashCode (컬렉션, 비교, 테스트용)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDTO)) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(id, userDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
