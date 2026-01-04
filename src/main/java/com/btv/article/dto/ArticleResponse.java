package com.btv.article.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.btv.article.enums.ArticleStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleResponse {

	private UUID id;
	
	private String title;
	
	private String summary;
	
	private String content;
	
	private ArticleStatus status;
	
	private Long viewCount;
	
	private UUID categoryId;
	
	private String categoryName;
	
	private UUID authorId;
	
	private String authorUsername;
	
	private List<String> tags;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime publishedAt;
}
