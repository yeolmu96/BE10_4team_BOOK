package ebook.model;

public class BookDTO {
	private int bookId; // book_id (auto_increment, PK)
	private String title; // 제목
	private String category; // 카테고리
	private int price; // 가격
	private String description; // 책 설명
	private String author; // 저자
	private int salesCount; // 판매 수

	public BookDTO() {
	}

	// 생성자 (book_id를 DB에서 할당받기용)
	public BookDTO(String title, String category, int price, String description, String author) {
		this.title = title;
		this.category = category;
		this.price = price;
		this.description = description;
		this.author = author;
		this.salesCount = 0; // 최초 등록시 0
	}

	// 전체 필드 생성자 (SELECT, 상세 조회용)
	public BookDTO(int bookId, String title, String category, int price, String description, String author,
			int salesCount) {
		this.bookId = bookId;
		this.title = title;
		this.category = category;
		this.price = price;
		this.description = description;
		this.author = author;
		this.salesCount = salesCount;
	}

	public int getBookId() {
		return bookId;
	}

	public String getTitle() {
		return title;
	}

	public String getCategory() {
		return category;
	}

	public int getPrice() {
		return price;
	}

	public String getDescription() {
		return description;
	}

	public String getAuthor() {
		return author;
	}

	public int getSalesCount() {
		return salesCount;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setSalesCount(int salesCount) {
		this.salesCount = salesCount;
	}

	@Override
	public String toString() {
		return String.format("ID:%d | [%s] by %s | %s | %,d원 | 판매:%d\n설명: %s", bookId, title, author, category, price,
				salesCount, description == null ? "" : description);
	}
}
