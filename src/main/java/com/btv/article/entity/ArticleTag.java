package com.btv.article.entity;

import com.btv.common.entity.BaseEntity;
import com.btv.tag.entity.Tag;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table( name= "article_tags",
	uniqueConstraints = @UniqueConstraint(columnNames = {"article_id","tag_id"})
		)
@Setter
@Getter
public class ArticleTag extends BaseEntity {
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name="article_id", nullable = false)
	private Article article;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name="tag_id", nullable = false)
	private Tag tag;
}
