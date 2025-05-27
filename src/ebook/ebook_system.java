package ebook;

import java.util.Scanner;
public class ebook_system {
	public static void main(String[] args) {
		Scanner key = new Scanner(System.in);
		System.out.println("******쇼핑몰********");
		System.out.println("1. 회원등록");
		System.out.println("2. 모든회원조회");
		System.out.println("3. 회원정보수정");
		System.out.println("4. 회원탈퇴");
		System.out.println("5. 주소로 회원 검색");
		System.out.println("6. 로그인");
		System.out.println("7. 회원번호로 조회하기");
		System.out.print("원하는 작업을 선택하세요:");
		int choice  = key.nextInt();
//		show(choice);
	}
//	public static void show(int choice){
//		MenuController ui = new MenuController();
//		switch(choice){
//			case 1:
//				ui.insertMenu();//회원등록
//				break;
//			case 2:
//				ui.selectMenu();//모든회원조회
//				break;
//			case 3:
//				ui.updateMenu();//회원정보수정
//				break;
//			case 4:
//				ui.deleteMenu();//회원탈퇴
//				break;
//			case 5:
//				ui.findByAddrMenu();//주소로 회원 검색
//				break;
//			case 6:
//				ui.loginMenu();//로그인
//				break;
//			case 7:
//				ui.myPageMenu();//회원번호로 조회하기
//				break;
//		}
//	}
}













