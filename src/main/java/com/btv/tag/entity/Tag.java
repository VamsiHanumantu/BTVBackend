package com.btv.tag.entity;

import java.util.ArrayList;
import java.util.List;

import com.btv.article.entity.ArticleTag;
import com.btv.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name= "tags")
@Setter
@Getter
public class Tag extends BaseEntity{
	
	@Column(nullable = false, length = 50)
	private String name;
	
	@Column(nullable = false, unique = true, length=60)
	private String slug;
	
	@OneToMany(mappedBy = "tag", fetch = FetchType.LAZY)
	private List<ArticleTag> articleTags = new ArrayList<>();
}
