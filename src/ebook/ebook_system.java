package ebook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import ebook.controller.MenuController;

public class ebook_system {
	public static void main(String[] args) throws IOException {
		// 첫화면 - 로그인시 일반회원 콘솔,관리자회원 콘솔을 나누어서 출력하도록
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("******Ebook-Store********");
		System.out.println("1. 로그인");
		System.out.println("2. 회원가입");
		System.out.println("3. 종료");
		System.out.print("원하는 작업을 선택하세요:");
		int choice = Integer.parseInt(br.readLine());
		show(choice);
	}

	public static void show(int choice) throws IOException {
		MenuController ui = new MenuController();
		switch (choice) {
		case 1:
			if (ui.loginMenu()) {// 로그인
				startAdminMenu();
			} else {
				startUserMenu();
			}
			break;
		case 2:
			if (ui.insertMenu()) {// 회원가입
				startAdminMenu();
			} else {
				startUserMenu();
			}

			break;
		case 3:
			return;
		default:
			System.out.println("잘못된 회원유형입니다. 메인 메뉴로 돌아갑니다.");
			break;
		}
	}

	public static void startUserMenu() throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			System.out.println("******Ebook-Store********");
			System.out.println("환영합니다 일반 회원유저");
			System.out.println("1. 회원정보 수정");
			System.out.println("2. 회원탈퇴");
			System.out.println("3. 종료");
			System.out.print("원하는 작업을 선택하세요:");
			int choice = Integer.parseInt(br.readLine());
			table(choice);
		}
	}

	public static void startAdminMenu() throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			System.out.println("******Ebook-Store********");
			System.out.println("환영합니다 관리자 회원유저");
			System.out.println("1. 회원정보 수정");
			System.out.println("2. 회원탈퇴");
			System.out.println("3. 종료");
			System.out.print("원하는 작업을 선택하세요:");
			int choice = Integer.parseInt(br.readLine());
			table(choice);
		}

	}

	private static void table(int choice) throws NumberFormatException, IOException {
		MenuController ui = new MenuController();
		switch (choice) {
		case 1:
			if (ui.loginMenu()) {// 로그인
				startAdminMenu();
			} else {
				startUserMenu();
			}
			break;
		case 2:
			if (ui.insertMenu()) {// 회원가입
				startAdminMenu();
			} else {
				startUserMenu();
			}

			break;
		case 3:
			return;
		default:
			System.out.println("잘못된 접근입니다.");
			break;
		}
	}

}
/*
ebook_system/
├── Main.java                   // main 시작점
├── controller/
│   └── MenuController.java     // 회원가입/로그인 기능
├── model/
│   └── User.java               // 유저 정보 클래스
│   └── Book.java               // 책 정보 클래스
├── service/
│   └── UserService.java        // 회원 CRUD 담당
│   └── BookService.java        // 상품 CRUD 담당
├── repository/
│   └── InMemoryDB.java         // 간단히 List로 유저/책 저장
*/