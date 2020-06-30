package com.example.form;

public class CommentForm {
	private String articleId;
	
	private String name;
	
	private String content;
	
	public CommentForm() {
		
	}

	public CommentForm(String articleId, String name, String content) {
		super();
		this.articleId = articleId;
		this.name = name;
		this.content = content;
	}

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
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
