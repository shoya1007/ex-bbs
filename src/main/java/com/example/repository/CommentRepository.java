package com.example.repository;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Comment;
import com.example.form.ArticleForm;
import com.example.form.CommentForm;

@Repository
public class CommentRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<Comment> COMMENT_ROW_MAPPER = (rs, i) -> {
		Comment comment = new Comment();
		comment.setId(rs.getInt("id"));
		comment.setName(rs.getString("name"));
		comment.setContent(rs.getString("content"));
		comment.setArticleId(rs.getInt("article_id"));
		return comment;
	};

	public List<Comment> findByArticleId(int articleId) {
		try {
			String sql = "select * from  comments where article_id=:articleId order by id desc";

			SqlParameterSource param = new MapSqlParameterSource().addValue("articleId", articleId);

			List<Comment> commentList = template.query(sql, param, COMMENT_ROW_MAPPER);

			return commentList;
		} catch (NullPointerException e) {
			return Collections.emptyList();
		}

	}

	public void insert(Comment comment) {
		String sql = "insert into comments (name,content,article_id) values (:name,:content,:articleId)";

		SqlParameterSource param = new BeanPropertySqlParameterSource(comment);

		template.update(sql, param);
	}

	public void deleteByArticleId(int articleId) {
		String sql = "delete from comments where article_id=:articleId";

		SqlParameterSource param = new MapSqlParameterSource().addValue("articleId", articleId);

		template.update(sql, param);
	}
}
