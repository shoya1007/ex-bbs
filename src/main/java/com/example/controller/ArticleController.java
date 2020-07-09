package com.example.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Article;
import com.example.domain.Comment;
import com.example.form.ArticleForm;
import com.example.form.CommentForm;
import com.example.repository.ArticleRepository;
import com.example.repository.CommentRepository;
import com.fasterxml.jackson.databind.deser.std.NumberDeserializers.IntegerDeserializer;

@Controller
@RequestMapping("/bbs")
public class ArticleController {
	@ModelAttribute
	private ArticleForm form() {
		return new ArticleForm();
	}

	@ModelAttribute
	private CommentForm CommentForm() {
		return new CommentForm();
	}

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private CommentRepository commentRepository;

	@RequestMapping("")
	public String index(Model model) {

		/*
		 * List<Article> articleList = articleRepository.findAll();
		 * 
		 * for (Article article : articleList) {
		 * 
		 * Integer articleId = article.getId(); System.out.println(articleId);
		 * List<Comment> commentList = commentRepository.findByArticleId(articleId);
		 * 
		 * for (int j = 0; j < commentList.size(); j++) {
		 * System.out.println(commentList.get(j).getName());
		 * System.out.println(commentList.get(j).getContent()); }
		 * article.setCommentList(commentList);
		 * 
		 * } model.addAttribute("articleList", articleList);
		 * 
		 * return "articlelist";
		 */

		/*
		 * List<Article> articleList = articleRepository.findAll(); List<Comment>
		 * commentList = new ArrayList<>(); List<Article> articleList2 = new
		 * ArrayList<>(); Article article2=new Article(); int i = 0; for (Article
		 * article : articleList) { if (i == 0) {
		 * 
		 * commentList.addAll(article.getCommentList());
		 * article2.setId(article.getId()); article2.setName(article.getName());
		 * article2.setContent(article.getContent()); if(articleList.size()==1) {
		 * article2.setCommentList(commentList); articleList2.add(article2); } i++;
		 * continue; }
		 * 
		 * if(i==(articleList.size()-1)&&articleList.get(i).getId() != articleList.get(i
		 * - 1).getId()) { article2.setCommentList(commentList);
		 * articleList2.add(article2); commentList=new ArrayList<>(); article2=new
		 * Article(); commentList.addAll(article.getCommentList());
		 * article2.setCommentList(commentList); article2.setId(article.getId());
		 * article2.setName(article.getName());
		 * article2.setContent(article.getContent()); articleList2.add(article2);
		 * continue; } if(i==(articleList.size()-1)&&articleList.get(i).getId() ==
		 * articleList.get(i - 1).getId()) {
		 * 
		 * commentList.addAll(article.getCommentList());
		 * article2.setCommentList(commentList); article2.setId(article.getId());
		 * article2.setName(article.getName());
		 * article2.setContent(article.getContent()); articleList2.add(article2);
		 * continue; }
		 * 
		 * if (i!=0&&articleList.get(i).getId() == articleList.get(i - 1).getId()) {
		 * commentList.addAll(article.getCommentList());
		 * article2.setId(article.getId()); article2.setName(article.getName());
		 * article2.setContent(article.getContent()); i++; continue; } else if
		 * (i!=0&&articleList.get(i).getId() != articleList.get(i - 1).getId()) {
		 * 
		 * article2.setCommentList(commentList);
		 * 
		 * articleList2.add(article2); commentList = new ArrayList<>(); article2=new
		 * Article(); commentList.addAll(article.getCommentList());
		 * article2.setId(article.getId()); article2.setName(article.getName());
		 * article2.setContent(article.getContent()); i++; continue; }
		 * 
		 * }
		 * 
		 * model.addAttribute("articleList",articleList2);
		 */
		List<Article> articleList = articleRepository.findAll();
		List<Integer> idList = new ArrayList<>();
		List<Comment> commentList = new ArrayList<>();
		Article article2 = new Article();
		List<Article> articleList2 = new ArrayList<>();
		for (Article article : articleList) {
			idList.add(article.getId());
		}
		List<Integer> idSet = new ArrayList<Integer>(new LinkedHashSet<>(idList));
		for (Integer id : idSet) {
			for (Article article : articleList) {
				if (id == article.getId()) {
					commentList.addAll(article.getCommentList());
					article2.setId(article.getId());
					article2.setName(article.getName());
					article2.setContent(article.getContent());
				}

			}
			article2.setCommentList(commentList);
			articleList2.add(article2);
			commentList = new ArrayList<>();
			article2 = new Article();
		}
		model.addAttribute("articleList", articleList2);
		return "articlelist";
	}

	@RequestMapping("/post")
	public String post(ArticleForm articleForm) {
		Article article = new Article();
		article.setName(articleForm.getName());
		article.setContent(articleForm.getContent());

		articleRepository.insert(article);

		return "forward:/bbs";
	}

	@RequestMapping("/postComment")
	public String postComment(CommentForm commentForm) {
		Comment comment = new Comment();
		comment.setArticleId(Integer.parseInt(commentForm.getArticleId()));
		comment.setName(commentForm.getName());
		comment.setContent(commentForm.getContent());
		commentRepository.insert(comment);
		return "forward:/bbs";
	}

	@RequestMapping("/delete")
	public String deleteArticle(CommentForm commentForm) {
		Integer articleId = Integer.parseInt(commentForm.getArticleId());

		commentRepository.deleteByArticleId(articleId);
		articleRepository.deleteById(articleId);
		return "forward:/bbs";
	}

}
