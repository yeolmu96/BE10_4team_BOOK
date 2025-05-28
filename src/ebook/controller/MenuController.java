package ebook.controller;

import ebook.model.*;

import java.util.List;
import java.util.Scanner;

public class MenuController {
	private static final Scanner sc = new Scanner(System.in);
	private final UserDTOImpl dao = new UserDTOImpl();
	private final BookDTOImpl bao = new BookDTOImpl();

	//회원가입
	//return true: 성공 / false: 실패
	public boolean insertMenu() {
		System.out.println("[회원가입]");
		System.out.print("아이디 입력: ");
		String id = sc.nextLine();
		System.out.print("비밀번호 입력: ");
		String pw = sc.nextLine();
		System.out.print("이름 입력: ");
		String name = sc.nextLine();
		System.out.print("이메일 입력: ");
		String email = sc.nextLine();
		System.out.print("관리자입니까? (y/n): ");
		String adminCheck = sc.nextLine();
		boolean isAdmin = adminCheck.equalsIgnoreCase("y");

		UserDTO newUser = new UserDTO(id, pw, name, email, isAdmin);
		boolean result = dao.insert(newUser); // DB에 회원 정보 저장 (중복 검사 등 포함)

		if (result) {
			System.out.println("회원가입 완료!");
			return true;
		} else {
			System.out.println("회원가입 실패! (중복 ID 등)");
			return false;
		}
	}

	//로그인 처리 -로그인 성공 시 UserDTO 반환
	//return UserDTO: 로그인 성공 시 사용자 정보 반환 / 실패 시 null
	public UserDTO loginMenu() {
		System.out.println("[로그인]");
		System.out.print("아이디 입력: ");
		String id = sc.nextLine();
		System.out.print("비밀번호 입력: ");
		String pw = sc.nextLine();

		UserDTO user = dao.login(id, pw); // DB에서 유저 조회
		if (user != null) {
			System.out.println(user.getName() + "님 환영합니다!");
			return user;
		} else {
			System.out.println("로그인 실패 아이디/비밀번호를 확인하세요.");
			return null;
		}
	}

	//회원정보 수정
	public void updateUserMenu(UserDTO user) {
		System.out.println("[회원정보 수정]");
		// 필요한 정보만 받는 식으로...
		System.out.print("변경할 이메일 입력 (현재: " + user.getEmail() + "): ");
		String newEmail = sc.nextLine();
		if (!newEmail.isEmpty()) {
			user.setEmail(newEmail);
		}
		System.out.print("변경할 비밀번호 입력(공백: 변경 안함): ");
		String newPw = sc.nextLine();
		if (!newPw.isEmpty()) {
			user.setPassword(newPw);
		}
		boolean result = dao.update(user);
		if (result) {
			System.out.println("회원정보가 수정되었습니다.");
		} else {
			System.out.println("회원정보 수정 실패");
		}
	}

	//회원탈퇴  return true: 탈퇴 성공, false: 실패
	public boolean deleteUserMenu() {
		System.out.println("[회원탈퇴]");
		System.out.print("정말 탈퇴하시겠습니까? (y/n): ");
		String confirm = sc.nextLine();
		if (!confirm.equalsIgnoreCase("y")) {
			System.out.println("탈퇴가 취소되었습니다.");
			return false;
		}
		String userid= sc.nextLine();
		boolean result = dao.delete(userid);
		if (result) {
			System.out.println("회원탈퇴가 완료되었습니다.");
			return true;
		} else {
			System.out.println("회원탈퇴 실패");
			return false;
		}
	}

	// 도서 목록 보기
	public void bookListMenu() {
        List<BookDTO> books = bao.getAllBooks();
        System.out.println("[도서 목록]");
        for (BookDTO book : books) {
            System.out.println(book);
        }
	}
	// 2. 도서 상세 보기 - 도서 ID로 상세 정보 출력
	public void bookDetailMenu() {
		System.out.print("상세 조회할 도서 ID(book_id): ");
		int bookId = Integer.parseInt(sc.nextLine());
		BookDTO book = bao.getBookById(bookId);
		if (book != null) {
			System.out.println(book);
		} else {
			System.out.println("존재하지 않는 도서입니다.");
		}
	}

	//3. 도서 구매 - 유저, 도서ID, 수량 받아 주문 처리
	public void purchaseBookMenu(UserDTO user) {
		System.out.println("[도서 구매]");
		System.out.print("구매할 도서ID: ");
		String bookId = sc.nextLine();
		System.out.print("수량: ");
		int quantity = Integer.parseInt(sc.nextLine());
		System.out.println("결제 방법을 선택하세요. (1: 페이, 2: 포인트): ");
		int payType = Integer.parseInt(sc.nextLine());
		// 주문 처리 로직 (잔액/포인트 차감 등)
		// boolean success = orderDao.purchase(user, bookId, quantity, payType);
		// if (success) System.out.println("구매 성공!");
		// else System.out.println("구매 실패(잔액/재고 부족 등)");

	}

	//4. 구매 내역 확인 - 해당 유저의 주문 기록 출력
	public void orderHistoryMenu(UserDTO user) {
		System.out.println("[구매 내역 확인]");
		// List<OrderDTO> orders = orderDao.getOrdersByUser(user.getId());
		// for (OrderDTO order : orders) {
		// System.out.println(order.simpleInfo());
		// }

	}

	//5. 리뷰 작성 - 도서ID, 평점, 내용 받아 리뷰 등록
	public void writeReviewMenu(UserDTO user) {
		System.out.println("[리뷰 작성]");
		System.out.print("리뷰 작성할 도서ID: ");
		String bookId = sc.nextLine();
		System.out.print("평점(1~5): ");
		int rating = Integer.parseInt(sc.nextLine());
		System.out.print("리뷰 내용: ");
		String content = sc.nextLine();
		// boolean success = reviewDao.insertReview(new ReviewDTO(user.getId(), bookId,
		// rating, content));
		// if (success) System.out.println("리뷰 등록 완료!");
		// else System.out.println("리뷰 등록 실패(구매 이력 없음 등)");

	}


	//6. FAQ 보기 - FAQ 목록 출력
	public void faqMenu() {
		System.out.println("[FAQ 보기]");
		// List<FaqDTO> faqList = faqDao.getAllFaq();
		// for (FaqDTO faq : faqList) {
		// System.out.println(faq);
		// }

	}

	// 1. 도서 등록 (관리자)-신규 도서 정보 입력 받아 등록
	public void registerBookMenu() {
		System.out.print("제목: ");
		String title = sc.nextLine();
		System.out.print("카테고리: ");
		String category = sc.nextLine();
		System.out.print("가격: ");
		int price = Integer.parseInt(sc.nextLine());
		System.out.print("설명: ");
		String description = sc.nextLine();
		System.out.print("저자: ");
		String author = sc.nextLine();

		BookDTO book = new BookDTO(title, category, price, description, author);
		if (bao.insert(book)) {
			System.out.println("도서 등록 성공!");
		} else {
			System.out.println("도서 등록 실패!");
		}
	}

	// 2. 도서 삭제 (관리자)-도서 ID로 삭제
	public void deleteBookMenu() {
		System.out.print("삭제할 도서 ID(book_id): ");
		int bookId = Integer.parseInt(sc.nextLine());
		if (bao.delete(bookId)) {
			System.out.println("도서 삭제 성공!");
		} else {
			System.out.println("도서 삭제 실패!");
		}
	}

	//4. 회원 목록 조회(관리자) - 전체 회원 출력
	public void userListMenu() {
		System.out.println("[회원 목록 조회]");
		// List<UserDTO> userList = dao.getAllUsers();
		// for (UserDTO user : userList) {
		// System.out.println(user);
		// }

	}

	//5. FAQ 등록(관리자)
	public void registerFaqMenu() {
		System.out.println("[FAQ 등록]");
		System.out.print("FAQ 제목: ");
		String title = sc.nextLine();
		System.out.print("FAQ 내용: ");
		String content = sc.nextLine();
		// FaqDTO faq = new FaqDTO(title, content);
		// boolean success = faqDao.insert(faq);
		// if (success) System.out.println("FAQ 등록 성공!");
		// else System.out.println("FAQ 등록 실패!");

	}

	//6. FAQ 수정/삭제(관리자)
	public void updateFaqMenu() {
		System.out.println("[FAQ 수정/삭제]");
		System.out.print("수정/삭제할 FAQ ID: ");
		String faqId = sc.nextLine();
		System.out.print("1. 수정  2. 삭제 : ");
		int mode = Integer.parseInt(sc.nextLine());
		if (mode == 1) {
			System.out.print("수정할 내용 입력: ");
			String content = sc.nextLine();
			// boolean success = faqDao.update(faqId, content);
			// if (success) System.out.println("FAQ 수정 성공!");
			// else System.out.println("FAQ 수정 실패!");
		} else if (mode == 2) {
			// boolean success = faqDao.delete(faqId);
			// if (success) System.out.println("FAQ 삭제 성공!");
			// else System.out.println("FAQ 삭제 실패!");
		} else {
			System.out.println("잘못된 선택입니다.");
		}

	}
}