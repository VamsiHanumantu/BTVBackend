package com.btv.article.dto;

import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleCreateRequest {

	@NotBlank(message = "Title is required")
	@Size(max = 150)
	private String title;
	
	@NotBlank(message = "Summary is required")
	@Size(max = 300)
	private String summary;
	
	@NotBlank(message = "Content is required")
	private String content;
	
	@NotNull(message = "Category Id is required")
	private UUID categoryId;
	
	private List<UUID> tagIds;
}
