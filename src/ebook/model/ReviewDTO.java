package ebook.model;

public class ReviewDTO {
    private String id; // 입력한 회원의 ID
    private String title; // 리뷰하는 책 제목
    private int grade; // 평점
    private String txt; // 리뷰 내용

    public ReviewDTO() {

    }
    public ReviewDTO(String id, String title, int grade, String txt) {
        super();
        this.id = id;
        this.title = title;
        this.grade = grade;
        this.txt = txt;
    }

    @Override
    public String toString() {
        return "ReviewDTO [id=" + id + ", title=" + title + ", grade=" + grade + ", txt=" + txt + "]";
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public int getGrade() {
        return grade;
    }
    public void setGrade(int grade) {
        this.grade = grade;
    }
    public String getTxt() {
        return txt;
    }
    public void setTxt(String txt) {
        this.txt = txt;
    }
}
