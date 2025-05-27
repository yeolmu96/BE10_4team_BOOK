package ebook.model;

import java.time.LocalDateTime;
import java.util.List;

public class OrdersDTO {
	private int id; // 주문번호(PK, auto_increment)
	private int userId; // 회원번호(FK, user.id)
	private LocalDateTime orderDate; // 주문일시
	private int totalAmount; // 총 결제금액(=책값합)
	private int payUsed; // 실제 사용한 pay
	private int pointsEarned; // 적립 포인트
	private int status; // 주문상태 (0:결제완료, 1:취소 등)
	private List<Order_itemDTO> items; // 주문한 책들(다:1)

	public OrdersDTO() {
	}

	// 주문 전체 생성자(주문+아이템)
	public OrdersDTO(int id, int userId, LocalDateTime orderDate, int totalAmount, int payUsed, int pointsEarned,
			int status, List<Order_itemDTO> items) {
		this.id = id;
		this.userId = userId;
		this.orderDate = orderDate;
		this.totalAmount = totalAmount;
		this.payUsed = payUsed;
		this.pointsEarned = pointsEarned;
		this.status = status;
		this.items = items;
	}

	// 주문 등록용 생성자 (주문 생성 후 아이템을 set하는 식으로 활용 가능)
	public OrdersDTO(int userId, int totalAmount, int payUsed, int pointsEarned, int status) {
		this.userId = userId;
		this.totalAmount = totalAmount;
		this.payUsed = payUsed;
		this.pointsEarned = pointsEarned;
		this.status = status;
	}

	// getter/setter
	public int getId() {
		return id;
	}

	public int getUserId() {
		return userId;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public int getTotalAmount() {
		return totalAmount;
	}

	public int getPayUsed() {
		return payUsed;
	}

	public int getPointsEarned() {
		return pointsEarned;
	}

	public int getStatus() {
		return status;
	}

	public List<Order_itemDTO> getItems() {
		return items;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}

	public void setPayUsed(int payUsed) {
		this.payUsed = payUsed;
	}

	public void setPointsEarned(int pointsEarned) {
		this.pointsEarned = pointsEarned;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setItems(List<Order_itemDTO> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return String.format("주문번호: %d | 회원번호: %d | 일시: %s | 결제금액: %,d원 | Pay: %,d | 적립: %,d | 상태: %d | 상품: %s", id,
				userId, orderDate, totalAmount, payUsed, pointsEarned, status, items);
	}
}
