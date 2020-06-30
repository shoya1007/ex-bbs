package com.example.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.domain.Article;
import com.example.domain.Comment;
import com.example.form.ArticleForm;
import com.fasterxml.jackson.databind.deser.std.StringArrayDeserializer;

@Repository
public class ArticleRepository {
	private static final RowMapper<Article> ARTICLE_ROW_MAPPER = (rs, i) -> {
		Article article = new Article();
		Comment comment=new Comment();
		List<Comment> commentList=new ArrayList<>();
		article.setId(rs.getInt("id"));
		article.setName(rs.getString("name"));
		article.setContent(rs.getString("content"));
		comment.setId(rs.getInt("com_id"));
		comment.setName(rs.getString("com_name"));
		comment.setContent(rs.getString("com_content"));
		comment.setArticleId(rs.getInt("article_id"));
		commentList.add(comment);
		article.setCommentList(commentList);
		return article;
	};

	@Autowired
	private NamedParameterJdbcTemplate template;

	/*
	 * public List<Article> findAll() { String sql =
	 * "select * from articles order by id desc";
	 * 
	 * List<Article> articleList = template.query(sql, ARTICLE_ROW_MAPPER);
	 * 
	 * return articleList; }
	 */

	public List<Article> findAll() {
		String sql = "select a.id, a.name, a.content, c.id as com_id, c.name as com_name, c.content as com_content, article_id from articles as a"
				+ "	left outer join comments as c" 
				+ "	on a.id = c.article_id order by id desc,com_id desc";

		List<Article> articleList = template.query(sql, ARTICLE_ROW_MAPPER);

		return articleList;
	}

	public void insert(Article article) {
		String sql = "insert into articles (name,content) values (:name,:content)";

		SqlParameterSource param = new BeanPropertySqlParameterSource(article);

		template.update(sql, param);
	}

	public void deleteById(int id) {
		String sql = "delete from articles where id=:id";

		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

		template.update(sql, param);
	}
}
