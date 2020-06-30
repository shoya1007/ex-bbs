package com.example.form;

public class ArticleForm {
	
	private String name;
	
	private String content;
	
	public ArticleForm() {
		
	}

	public ArticleForm(String name, String content) {
		super();
		this.name = name;
		this.content = content;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	
	
	
}
