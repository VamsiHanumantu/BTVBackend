package com.btv.article.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
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
	
	@PreAuthorize("hasAnyRole('REPORTER','ADMIN')")
	@PostMapping
	public ResponseEntity<ArticleResponse> createArticle(@Valid @RequestBody ArticleCreateRequest request){
		ArticleResponse response = articleService.createArticle(request);
		return new ResponseEntity<ArticleResponse>(response,HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasAnyRole('REPORTER','ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<ArticleResponse> updateArticle(@PathVariable UUID id, @Valid @RequestBody ArticleUpdateRequest request){
		ArticleResponse response = articleService.updateArticle(id, request);
		return new ResponseEntity<ArticleResponse>(response,HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('USER','REPORTER','ADMIN')")
	@GetMapping("/{id}")
	public ResponseEntity<ArticleResponse> getArticle(@PathVariable UUID id){
		ArticleResponse response = articleService.getArticleById(id);
		return new ResponseEntity<ArticleResponse>(response,HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping
	public ResponseEntity<Page<ArticleResponse>> getAllArticles(Pageable pageable){
		Page<ArticleResponse> response = articleService.getAllArticles(pageable);
		return new ResponseEntity<Page<ArticleResponse>>(response,HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('USER','REPORTER','ADMIN')")
	@GetMapping("/approved")
	public ResponseEntity<Page<ArticleResponse>> getApprovedArticles(Pageable pageable){
		Page<ArticleResponse> response = articleService.getApprovedArticles(pageable);
		return new ResponseEntity<Page<ArticleResponse>>(response,HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/pending")
	public ResponseEntity<Page<ArticleResponse>> getPendingArticles(Pageable pageable){
		Page<ArticleResponse> response = articleService.getPendingArticles(pageable);
		return new ResponseEntity<Page<ArticleResponse>>(response,HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('REPORTER','ADMIN')")
	@PatchMapping("/{id}/submit")
	public ResponseEntity<ArticleResponse> submitForReview(@PathVariable UUID id){
		ArticleResponse response = articleService.submitForReview(id);
		return new ResponseEntity<ArticleResponse>(response,HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PatchMapping("/{id}/approve")
	public ResponseEntity<ArticleResponse> approveArticle(@PathVariable UUID id){
		ArticleResponse response = articleService.approveArticle(id);
		return new ResponseEntity<ArticleResponse>(response,HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PatchMapping("/{id}/reject")
	public ResponseEntity<ArticleResponse> rejectArticle(@PathVariable UUID id){
		ArticleResponse response = articleService.rejectArticle(id);
		return new ResponseEntity<ArticleResponse>(response,HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteArticle(@PathVariable UUID id){
		articleService.deleteArticle(id);
		return ResponseEntity.noContent().build();
	}
	
}
