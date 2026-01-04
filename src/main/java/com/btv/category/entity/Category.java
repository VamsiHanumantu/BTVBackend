package com.btv.category.entity;

import java.util.ArrayList;
import java.util.List;

import com.btv.article.entity.Article;
import com.btv.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name= "categories")
@Setter
@Getter
public class Category extends BaseEntity{
 
	@Column( nullable = false, length = 50)
	private String name;
	
	@Column( nullable = false, length = 60, unique = true)
	private String slug;
	
	@Column(length = 150)
	private String description;
	
	@Column(nullable = false)
	private Boolean isActive = true;
	
	@OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
	private List<Article> articles = new ArrayList<>();
	
}
