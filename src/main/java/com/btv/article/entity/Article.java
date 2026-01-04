package com.btv.article.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.btv.article.enums.ArticleStatus;
import com.btv.category.entity.Category;
import com.btv.common.entity.BaseEntity;
import com.btv.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "articles")
public class Article extends BaseEntity{

	@Column(nullable = false, length = 150)
	private String title;
	
	@Column(nullable = false, length = 300)
	private String summary;
	
	@Lob
	@Column(nullable = false)
	private String content;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private ArticleStatus status = ArticleStatus.DRAFT;
	
	@Column(nullable = false)
	private Long viewCount  = 0L;
	
	@Column
	private LocalDateTime publishedAt;
	
	@Column(nullable = false)
	private Boolean isFeatured = false;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "author_id", nullable = false)
	private User author;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;
	
	@OneToMany(mappedBy = "article",fetch = FetchType.LAZY)
	private List<ArticleTag> articleTags = new ArrayList<>();
	
}
