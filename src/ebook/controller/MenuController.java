package ebook.controller;

import ebook.model.UserDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuController {
    private static final Scanner sc = new Scanner(System.in);
    public static UserDTO currentUser; // 현재 로그인된 사용자
    // 회원가입
    public boolean insertMenu() {
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
        //String id, String password, String name, String email, boolean isAdmin
        UserDTO newUser = new UserDTO(id, pw, name, email, isAdmin);
        System.out.println(" 회원가입 완료!");
        return isAdmin;
    }

    // 로그인
    public boolean loginMenu() {
        System.out.print("아이디 입력: ");
        String id = sc.nextLine();

        System.out.print("비밀번호 입력: ");
        String pw = sc.nextLine();

 

        System.out.println(" 로그인 실패. 다시 시도하세요.");
        return false; // 실패 시 기본 일반 유저 처리
    }
    public boolean deleteMenu() {
        System.out.print("아이디 입력: ");
        String id = sc.nextLine();

        System.out.print("비밀번호 입력: ");
        String pw = sc.nextLine();



        System.out.println(" 로그인 실패. 다시 시도하세요.");
        return false; // 실패 시 기본 일반 유저 처리
    }
}
