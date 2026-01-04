package com.btv.category.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryUpdateRequest {

	@Size(max = 50)
	private String name;

	@Size(max = 60)
	private String slug;
	
	@Size(max = 150)
	private String description;
	
	private Boolean isActive;
}
