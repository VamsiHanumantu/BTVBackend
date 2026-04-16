package com.btv.category.service.impl;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.btv.category.dto.CategoryCreateRequest;
import com.btv.category.dto.CategoryResponse;
import com.btv.category.dto.CategoryUpdateRequest;
import com.btv.category.entity.Category;
import com.btv.category.mapper.CategoryMapper;
import com.btv.category.repository.CategoryRepository;
import com.btv.category.service.CategoryService;
import com.btv.common.exception.ResourceNotFoundException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
	
	private final CategoryRepository categoryRepository;
	
	private final CategoryMapper categoryMapper;
	
	@Override
	public CategoryResponse createCategory(CategoryCreateRequest request) {
		Category category = categoryMapper.toEnity(request);
		Category savedCategory = categoryRepository.save(category);		
		return categoryMapper.toResponse(savedCategory);
	}

	@Override
	public CategoryResponse updateCategory(UUID categoryId,CategoryUpdateRequest request) {
		Category category = categoryRepository.findById(categoryId).orElseThrow(
				()-> new ResourceNotFoundException("Category not found")
				);
		categoryMapper.updateEntity(request, category);
		
		return categoryMapper.toResponse(category);
	}

	@Override
	public CategoryResponse getCategoryById(UUID id) {
		Category category = categoryRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("Category not found")
				);
		return categoryMapper.toResponse(category);
	}

	@Override
	public Page<CategoryResponse> getAllActiveCategories(Pageable pageable) {
		
		return categoryRepository.findByIsActiveTrue(pageable).map(categoryMapper::toResponse);
	}

	@Override
	public Page<CategoryResponse> getAllCategories(Pageable pageable) {
		return categoryRepository.findAll(pageable).map(categoryMapper::toResponse);
	}

	@Override
	public void deactivateCategory(UUID categoryId) {
		Category category = categoryRepository.findById(categoryId).orElseThrow(
				()-> new ResourceNotFoundException("Category not found")
				);
		category.setIsActive(false);
	}

}
