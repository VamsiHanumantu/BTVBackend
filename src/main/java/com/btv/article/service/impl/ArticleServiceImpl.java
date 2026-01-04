package com.btv.article.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.btv.article.dto.ArticleCreateRequest;
import com.btv.article.dto.ArticleResponse;
import com.btv.article.dto.ArticleUpdateRequest;
import com.btv.article.entity.Article;
import com.btv.article.mapper.ArticleMapper;
import com.btv.article.repository.ArticleRepository;
import com.btv.article.service.ArticleService;
import com.btv.category.entity.Category;
import com.btv.category.repository.CategoryRepository;
import com.btv.common.exception.ResourceNotFoundException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
	
	private final ArticleRepository articleRepository;
	
	private final CategoryRepository categoryRepository;
	
	private final ArticleMapper articleMapper;

	@Override
	public ArticleResponse createArticle(ArticleCreateRequest request) {
	
		Article article = articleMapper.toEntity(request);
		
		Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(
				()-> new ResourceNotFoundException("Category Not Found")
				);
		article.setCategory(category);
		
		//article.setAuthor(currentUser);
		
		return articleMapper.toDto(article);
	}

	@Override
	public ArticleResponse updateArticle(UUID articleId, ArticleUpdateRequest request) {
		
		Article article = articleRepository.findById(articleId).orElseThrow(
				()-> new ResourceNotFoundException("Article Not Found")
				);
		articleMapper.updateEntity(request, article);
		
		if(request.getCategoryId()!=null) {
			Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(
					()-> new ResourceNotFoundException("Category Not Found")
					);
			article.setCategory(category);
		}
		return articleMapper.toDto(article);
	}

	@Override
	public ArticleResponse getArticleById(UUID articleId) {
		Article article = articleRepository.findById(articleId).orElseThrow(
				()-> new ResourceNotFoundException("Article Not Found")
				);
		return articleMapper.toDto(article);
	}

}
