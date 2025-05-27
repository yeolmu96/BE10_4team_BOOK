package ebook.model;

import java.time.LocalDateTime;

public class UserDTO {
	private int id; // PK, auto_increment
	private String userId; // 로그인 ID (user_id)
	private String password;
	private String name;
	private String email;
	private LocalDateTime joinedAt; // 가입일시
	private boolean isAdmin; // 관리자 여부 (0/1 → false/true)
	private int payBalance; // 충전금
	private int pointsBalance; // 포인트

	public UserDTO() {
	}

	// 회원가입용 생성자 (id/joinedAt/잔고/포인트는 자동)
	public UserDTO(String userId, String password, String name, String email, boolean isAdmin) {
		this.userId = userId;
		this.password = password;
		this.name = name;
		this.email = email;
		this.isAdmin = isAdmin;
		this.payBalance = 0;
		this.pointsBalance = 0;
		this.joinedAt = LocalDateTime.now();
	}

	// 전체필드 생성자 (SELECT, 조회용)
	public UserDTO(int id, String userId, String password, String name, String email, LocalDateTime joinedAt,
			boolean isAdmin, int payBalance, int pointsBalance) {
		this.id = id;
		this.userId = userId;
		this.password = password;
		this.name = name;
		this.email = email;
		this.joinedAt = joinedAt;
		this.isAdmin = isAdmin;
		this.payBalance = payBalance;
		this.pointsBalance = pointsBalance;
	}

	public int getId() {
		return id;
	}

	public String getUserId() {
		return userId;
	}

	public String getPassword() {
		return password;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public LocalDateTime getJoinedAt() {
		return joinedAt;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public int getPayBalance() {
		return payBalance;
	}

	public int getPointsBalance() {
		return pointsBalance;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPayBalance(int payBalance) {
		this.payBalance = payBalance;
	}

	public void setPointsBalance(int pointsBalance) {
		this.pointsBalance = pointsBalance;
	}

}
