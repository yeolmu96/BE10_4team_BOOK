package ebook.model;

public class Order_itemDTO {
	private int id; // PK, auto_increment
	private int orderId; // 주문번호(FK, orders.id)
	private int bookId; // 도서번호(FK, book.book_id)
	private int quantity; // 주문 수량

	public Order_itemDTO() {
	}

	public Order_itemDTO(int id, int orderId, int bookId, int quantity) {
		this.id = id;
		this.orderId = orderId;
		this.bookId = bookId;
		this.quantity = quantity;
	}

	// 주문 등록용(등록 시에는 id, orderId 없이 생성 가능)
	public Order_itemDTO(int bookId, int quantity) {
		this.bookId = bookId;
		this.quantity = quantity;
	}

	// getter/setter
	public int getId() {
		return id;
	}

	public int getOrderId() {
		return orderId;
	}

	public int getBookId() {
		return bookId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return String.format("[도서번호:%d, 수량:%d]", bookId, quantity);
	}
}
