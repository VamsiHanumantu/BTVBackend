package com.btv.category.service;

import java.util.List;
import java.util.UUID;

import com.btv.category.dto.CategoryCreateRequest;
import com.btv.category.dto.CategoryResponse;
import com.btv.category.dto.CategoryUpdateRequest;

public interface CategoryService {

	CategoryResponse createCategory(CategoryCreateRequest request);
	
	CategoryResponse updateCategory(UUID categoryId,CategoryUpdateRequest request);
	
	CategoryResponse getCategoryById(UUID id);
	
	List<CategoryResponse> getAllActiveCategories();
}
