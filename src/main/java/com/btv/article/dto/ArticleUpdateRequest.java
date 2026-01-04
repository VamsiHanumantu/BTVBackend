package com.btv.article.dto;

import java.util.UUID;

import jakarta.validation.constraints.Size;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleUpdateRequest {
	
	@Size(max=150)
	private String title;
	
	@Size(max=300)
	private String summary;
	
	private String content;
	
	private UUID categoryId;
	
	private List<UUID> tagIds;
}
