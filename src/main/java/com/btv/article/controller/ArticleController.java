package com.btv.article.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.btv.article.dto.ArticleCreateRequest;
import com.btv.article.dto.ArticleResponse;
import com.btv.article.dto.ArticleUpdateRequest;
import com.btv.article.service.ArticleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticleController {

	private final ArticleService articleService;
	
	@PostMapping
	public ResponseEntity<ArticleResponse> createArticle(@Valid @RequestBody ArticleCreateRequest request){
		ArticleResponse response = articleService.createArticle(request);
		return new ResponseEntity<ArticleResponse>(response,HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ArticleResponse> updateArticle(@PathVariable UUID id, @Valid @RequestBody ArticleUpdateRequest request){
		ArticleResponse response = articleService.updateArticle(id, request);
		return new ResponseEntity<ArticleResponse>(response,HttpStatus.OK);
	}
	
	public ResponseEntity<ArticleResponse> getArticle(@PathVariable UUID id){
		ArticleResponse response = articleService.getArticleById(id);
		return new ResponseEntity<ArticleResponse>(response,HttpStatus.OK);
	}
	
}
