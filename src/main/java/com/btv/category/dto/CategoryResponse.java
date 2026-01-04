package com.btv.category.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponse {
  
	private UUID id;
	
	private String name;
	
	private String slug;
	
	private String description;
	
	private Boolean isActive;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
}
