package com.btv.article.service;

import java.util.UUID;

import com.btv.article.dto.ArticleCreateRequest;
import com.btv.article.dto.ArticleResponse;
import com.btv.article.dto.ArticleUpdateRequest;

public interface ArticleService {

	ArticleResponse createArticle(ArticleCreateRequest request);
	
	ArticleResponse updateArticle(UUID articleId, ArticleUpdateRequest request);
	
	ArticleResponse getArticleById(UUID articleId);
}
