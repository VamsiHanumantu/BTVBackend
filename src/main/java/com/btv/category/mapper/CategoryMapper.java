package com.btv.category.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.btv.category.dto.CategoryCreateRequest;
import com.btv.category.dto.CategoryResponse;
import com.btv.category.dto.CategoryUpdateRequest;
import com.btv.category.entity.Category;

@Mapper(
		componentModel = "spring",
		unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CategoryMapper {

	Category toEnity(CategoryCreateRequest request);
	
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateEntity(CategoryUpdateRequest request, @MappingTarget Category category);
	
	CategoryResponse toResponse(Category category)           ;
}
