package ebook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import ebook.controller.MenuController;
import ebook.model.UserDTO; // 로그인 결과를 User 객체로 받는다고 가정

public class EbookSystem {
	public static void main(String[] args) throws IOException {
		MenuController ui = new MenuController();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			System.out.println("******Ebook-Store********");
			System.out.println("1. 로그인");
			System.out.println("2. 회원가입");
			System.out.println("3. FAQ 보기");
			System.out.println("0. 종료");
			System.out.print("원하는 작업을 선택하세요: ");
			int choice = Integer.parseInt(br.readLine());
			switch (choice) {
			case 1:
				UserDTO loginUser = ui.loginMenu(); // 로그인 성공 시 User 객체 반환
				if (loginUser != null) {
					if (loginUser.isAdmin()) {
						startAdminMenu(loginUser);
					} else {
						startUserMenu(loginUser);
					}
				} else {
					System.out.println("로그인 실패! 아이디/비밀번호를 확인하세요.");
				}
				break;
			case 2:
				boolean regSuccess = ui.insertMenu();
				if (regSuccess) {
					System.out.println("회원가입 성공! 이제 로그인을 진행하세요.");
				} else {
					System.out.println("회원가입 실패! 다시 시도하세요.");
				}
				break;
			case 3:
				ui.faqMenu();
				break;
			case 0:
				System.out.println("프로그램을 종료합니다.");
				System.exit(0);
			default:
				System.out.println("잘못된 입력입니다. 다시 선택하세요.");
			}
		}
	}

	// 일반 회원 메뉴
	public static void startUserMenu(UserDTO user) throws IOException {
		MenuController ui = new MenuController();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			System.out.println("======Ebook-Store(일반회원)======");
			System.out.println(user.getName() + "님 환영합니다!");
			System.out.println("1. 도서 목록 보기");
			System.out.println("2. 도서 상세 보기");
			System.out.println("3. 도서 구매");
			System.out.println("4. 구매 내역 확인 ");
			System.out.println("5. 리뷰 작성");
			System.out.println("6. FAQ");
			System.out.println("7. 로그아웃");
			System.out.println("0. 프로그램 종료");
			System.out.print("메뉴 선택: ");
			/*
			 * 1. 도서 목록 보기 2. 도서 상세 보기 3. 도서 구매 4. 구매 내역 확인 5. 리뷰 작성 6. FAQ 보기 7. 로그아웃 0.
			 * 프로그램 종료
			 * 
			 */
			int choice = Integer.parseInt(br.readLine());
			switch (choice) {
			    case 1: // 도서 목록 보기
			        ui.bookListMenu();
			        break;
			    case 2: // 도서 상세 보기
			        ui.bookDetailMenu();
			        break;
			    case 3: // 도서 구매
			        ui.purchaseBookMenu(user);
			        break;
			    case 4: // 구매 내역 확인
			        ui.orderHistoryMenu(user);
			        break;
			    case 5: // 리뷰 작성
			        ui.writeReviewMenu(user);
			        break;
			    case 6: // FAQ 보기
			        ui.faqMenu();
			        break;
			    case 7: // 로그아웃
			        System.out.println("로그아웃합니다.");
			        return;
			    case 0: // 프로그램 종료
			        System.out.println("프로그램을 종료합니다.");
			        System.exit(0);
			    default:
			        System.out.println("잘못된 입력입니다.");
			}

		}
	}

	// 관리자 메뉴
	public static void startAdminMenu(UserDTO user) throws IOException {
	    MenuController ui = new MenuController();
	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    while (true) {
	        System.out.println("======Ebook-Store(관리자)======");
	        System.out.println("관리자 " + user.getName() + "님 환영합니다!");
	        System.out.println("1. 도서 등록");
	        System.out.println("2. 도서 삭제");
	        System.out.println("3. 도서 목록 보기");
	        System.out.println("4. 회원 목록 조회");
	        System.out.println("5. FAQ 등록");
	        System.out.println("6. FAQ 수정/삭제");
	        System.out.println("7. 로그아웃");
	        System.out.println("0. 프로그램 종료");
	        System.out.print("메뉴 선택: ");

	        int choice = Integer.parseInt(br.readLine());
	        switch (choice) {
	            case 1: // 도서 등록
	                ui.registerBookMenu();
	                break;
	            case 2: // 도서 삭제
	                ui.deleteBookMenu();
	                break;
	            case 3: // 도서 목록 보기
	                ui.bookListMenu();
	                break;
	            case 4: // 회원 목록 조회
	                ui.userListMenu();
	                break;
	            case 5: // FAQ 등록
	                ui.registerFaqMenu();
	                break;
	            case 6: // FAQ 수정/삭제
	                ui.updateFaqMenu();
	                break;
	            case 7: // 로그아웃
	                System.out.println("로그아웃합니다.\n");
	                return;
	            case 0: // 프로그램 종료
	                System.out.println("프로그램을 종료합니다.");
	                System.exit(0);
	            default:
	                System.out.println("잘못된 입력입니다.\n");
	        }
	    }
	}

}