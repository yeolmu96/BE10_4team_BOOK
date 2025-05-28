package ebook.model;

import java.util.Date;

public class FaqDTO {
	private int id;
	private String title;
	private String content;
	private Date created_at;
	
	public FaqDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public FaqDTO(int id, String title, String content, Date created_at) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.created_at = created_at;
	}
	public FaqDTO(String title, String content) {
		super();
		this.title = title;
		this.content = content;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}
	
	@Override
	public String toString() {
		return "FaqDTO [id=" + id + ", title=" + title + ", content=" + content + ", created_at=" + created_at + "]";
	}
}
