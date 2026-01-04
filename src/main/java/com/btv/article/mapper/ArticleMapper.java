package com.btv.article.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.btv.article.dto.ArticleCreateRequest;
import com.btv.article.dto.ArticleResponse;
import com.btv.article.dto.ArticleUpdateRequest;
import com.btv.article.entity.Article;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ArticleMapper {

	Article toEntity(ArticleCreateRequest request);
	
	@BeanMapping(nullValuePropertyMappingStrategy  = NullValuePropertyMappingStrategy.IGNORE)
	void updateEntity(ArticleUpdateRequest request, @MappingTarget Article article);
	
	@Mapping(source = "category.id", target = "categoryId")   
	@Mapping(source = "category.name", target = "categoryName")
	@Mapping(source = "author.id", target = "authorId")
	@Mapping(source = "author.username", target = "authorUsername")
	ArticleResponse toDto(Article article);
	
	
	
}
