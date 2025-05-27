package ebook.model;

import java.time.LocalDate;

public class UserDTO {
    private String id;
    private String password;
    private String name;
    private String email;
    private LocalDate joinDate;
    private int pay;
    private int point;
    private boolean isAdmin;
    public UserDTO() {}
    // »ý¼ºÀÚ
    public UserDTO(String id, String password, String name, String email, boolean isAdmin) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
        this.joinDate = LocalDate.now();
        this.pay = 0;
        this.point = 0;
        this.isAdmin = isAdmin;
    }

    // getter/setter
    public String getId() { return id; }
    public String getPassword() { return password; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public LocalDate getJoinDate() { return joinDate; }
    public int getPay() { return pay; }
    public int getPoint() { return point; }
    public boolean isAdmin() { return isAdmin; }

    public void setPassword(String password) { this.password = password; }
    public void setEmail(String email) { this.email = email; }
    public void setPay(int pay) { this.pay = pay; }
    public void setPoint(int point) { this.point = point; }
}