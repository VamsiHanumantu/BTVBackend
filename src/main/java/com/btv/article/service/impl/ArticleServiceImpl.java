package com.btv.article.service.impl;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.btv.article.dto.ArticleCreateRequest;
import com.btv.article.dto.ArticleResponse;
import com.btv.article.dto.ArticleUpdateRequest;
import com.btv.article.entity.Article;
import com.btv.article.entity.ArticleTag;
import com.btv.article.enums.ArticleStatus;
import com.btv.article.mapper.ArticleMapper;
import com.btv.article.repository.ArticleRepository;
import com.btv.article.repository.ArticleTagRepository;
import com.btv.article.service.ArticleService;
import com.btv.category.entity.Category;
import com.btv.category.repository.CategoryRepository;
import com.btv.common.exception.BadRequestException;
import com.btv.common.exception.ResourceNotFoundException;
import com.btv.common.exception.UnauthorizedException;
import com.btv.tag.entity.Tag;
import com.btv.tag.repository.TagRepository;
import com.btv.user.entity.User;
import com.btv.user.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
	
	private final ArticleRepository articleRepository;
	
	private final CategoryRepository categoryRepository;

	private final TagRepository tagRepository;

	private final ArticleTagRepository articleTagRepository;

	private final UserRepository userRepository;
	
	private final ArticleMapper articleMapper;

	@Override
	public ArticleResponse createArticle(ArticleCreateRequest request) {
	
		Article article = articleMapper.toEntity(request);
		
		Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(
				()-> new ResourceNotFoundException("Category Not Found")
				);
		article.setCategory(category);
		
		article.setAuthor(getCurrentUser());
		
		Article savedArticle = articleRepository.save(article);
		syncTags(savedArticle, request.getTagIds());

		return articleMapper.toDto(savedArticle);
	}

	@Override
	public ArticleResponse updateArticle(UUID articleId, ArticleUpdateRequest request) {
		
		Article article = articleRepository.findById(articleId).orElseThrow(
				()-> new ResourceNotFoundException("Article Not Found")
				);
		validateEditableByCurrentUser(article);
		articleMapper.updateEntity(request, article);
		
		if(request.getCategoryId()!=null) {
			Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(
					()-> new ResourceNotFoundException("Category Not Found")
					);
			article.setCategory(category);
		}
		if (request.getTagIds() != null) {
			syncTags(article, request.getTagIds());
		}
		return articleMapper.toDto(article);
	}

	@Override
	public ArticleResponse getArticleById(UUID articleId) {
		Article article = articleRepository.findById(articleId).orElseThrow(
				()-> new ResourceNotFoundException("Article Not Found")
				);
		if (!article.getStatus().equals(ArticleStatus.APPROVED) && !isAdmin() && !isCurrentUserAuthor(article)) {
			throw new UnauthorizedException("You are not allowed to view this article");
		}
		return articleMapper.toDto(article);
	}

	@Override
	public Page<ArticleResponse> getAllArticles(Pageable pageable) {
		return articleRepository.findAll(pageable)
				.map(articleMapper::toDto);
	}

	@Override
	public Page<ArticleResponse> getApprovedArticles(Pageable pageable) {
		return articleRepository.findByStatus(ArticleStatus.APPROVED, pageable)
				.map(articleMapper::toDto);
	}

	@Override
	public Page<ArticleResponse> getPendingArticles(Pageable pageable) {
		return articleRepository.findByStatus(ArticleStatus.PENDING, pageable)
				.map(articleMapper::toDto);
	}

	@Override
	public ArticleResponse submitForReview(UUID articleId) {
		Article article = articleRepository.findById(articleId).orElseThrow(
				()-> new ResourceNotFoundException("Article Not Found")
				);
		validateEditableByCurrentUser(article);
		if (!article.getStatus().equals(ArticleStatus.DRAFT) && !article.getStatus().equals(ArticleStatus.REJECTED)) {
			throw new BadRequestException("Only draft or rejected articles can be submitted for review");
		}
		article.setStatus(ArticleStatus.PENDING);
		return articleMapper.toDto(article);
	}

	@Override
	public ArticleResponse approveArticle(UUID articleId) {
		Article article = articleRepository.findById(articleId).orElseThrow(
				()-> new ResourceNotFoundException("Article Not Found")
				);
		if (!article.getStatus().equals(ArticleStatus.PENDING)) {
			throw new BadRequestException("Only pending articles can be approved");
		}
		article.setStatus(ArticleStatus.APPROVED);
		article.setPublishedAt(LocalDateTime.now());
		return articleMapper.toDto(article);
	}

	@Override
	public ArticleResponse rejectArticle(UUID articleId) {
		Article article = articleRepository.findById(articleId).orElseThrow(
				()-> new ResourceNotFoundException("Article Not Found")
				);
		if (!article.getStatus().equals(ArticleStatus.PENDING)) {
			throw new BadRequestException("Only pending articles can be rejected");
		}
		article.setStatus(ArticleStatus.REJECTED);
		article.setPublishedAt(null);
		return articleMapper.toDto(article);
	}

	@Override
	public void deleteArticle(UUID articleId) {
		Article article = articleRepository.findById(articleId).orElseThrow(
				()-> new ResourceNotFoundException("Article Not Found")
				);
		articleTagRepository.deleteByArticleId(article.getId());
		articleRepository.delete(article);
	}

	private User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()) {
			throw new UnauthorizedException("Authenticated user is required");
		}

		return userRepository.findByUsername(authentication.getName())
				.orElseThrow(() -> new UnauthorizedException("Authenticated user was not found"));
	}

	private void validateEditableByCurrentUser(Article article) {
		if (isAdmin()) {
			return;
		}
		if (!isCurrentUserAuthor(article)) {
			throw new UnauthorizedException("You are not allowed to edit this article");
		}
		if (article.getStatus().equals(ArticleStatus.PENDING) || article.getStatus().equals(ArticleStatus.APPROVED)) {
			throw new BadRequestException("Pending or approved articles cannot be edited by reporters");
		}
	}

	private boolean isAdmin() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication != null && authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.anyMatch("ROLE_ADMIN"::equals);
	}

	private boolean isCurrentUserAuthor(Article article) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication != null
				&& article.getAuthor() != null
				&& authentication.getName().equals(article.getAuthor().getUsername());
	}

	private void syncTags(Article article, List<UUID> tagIds) {
		article.getArticleTags().clear();
		articleTagRepository.deleteByArticleId(article.getId());

		if (tagIds == null || tagIds.isEmpty()) {
			return;
		}

		List<UUID> uniqueTagIds = tagIds.stream().distinct().toList();
		List<Tag> tags = tagRepository.findAllById(uniqueTagIds);
		if (tags.size() != uniqueTagIds.size()) {
			throw new BadRequestException("One or more tags were not found");
		}

		for (Tag tag : tags) {
			ArticleTag articleTag = new ArticleTag();
			articleTag.setArticle(article);
			articleTag.setTag(tag);
			ArticleTag savedArticleTag = articleTagRepository.save(articleTag);
			article.getArticleTags().add(savedArticleTag);
		}
	}

}
