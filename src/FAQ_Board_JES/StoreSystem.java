package store;

import java.util.Scanner;

public class StoreSystem {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("----FAQ게시판----");
		System.out.println("--1번을 누르세요---");
		System.out.println("---------------");
		int choice = sc.nextInt();
		show(choice);
	}
	
	public static void show(int choice) {
		MenuController ui = new MenuController();
		switch(choice) {
		case 1:
			ui.showFaqBoard();
			break;
		}
	}
}
