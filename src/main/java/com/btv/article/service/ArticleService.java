package com.btv.article.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.btv.article.dto.ArticleCreateRequest;
import com.btv.article.dto.ArticleResponse;
import com.btv.article.dto.ArticleUpdateRequest;

public interface ArticleService {

	ArticleResponse createArticle(ArticleCreateRequest request);
	
	ArticleResponse updateArticle(UUID articleId, ArticleUpdateRequest request);
	
	ArticleResponse getArticleById(UUID articleId);

	Page<ArticleResponse> getAllArticles(Pageable pageable);

	Page<ArticleResponse> getApprovedArticles(Pageable pageable);

	Page<ArticleResponse> getPendingArticles(Pageable pageable);

	ArticleResponse submitForReview(UUID articleId);

	ArticleResponse approveArticle(UUID articleId);

	ArticleResponse rejectArticle(UUID articleId);

	void deleteArticle(UUID articleId);
}
