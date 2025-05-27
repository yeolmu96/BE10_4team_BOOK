package store;

import java.util.ArrayList;

public class MenuController {
	FaqDAOImpl dao = new FaqDAOImpl();
	
	public void showFaqBoard() {
		System.out.println("*게시글 내용 시작합니다*");
		System.out.println("*****************");
		ArrayList<FaqDTO> faqList = dao.showFaqBoard();
		
		if(faqList == null) {
			System.out.println("종료합니다.");
		}else {
			for(FaqDTO faq : faqList) {
				System.out.println("게시글 번호 : " + faq.getId());
				System.out.println("질문 : " + faq.getTitle());
				System.out.println("답변 : " + faq.getContent());
				System.out.println("작성일자 : " + faq.getCreated_at());
				System.out.println("-------------------------------");
			}
		}
	}
}
