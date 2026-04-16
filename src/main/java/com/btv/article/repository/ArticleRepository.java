package com.btv.article.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.btv.article.entity.Article;
import com.btv.article.enums.ArticleStatus;

@Repository
public interface ArticleRepository extends JpaRepository<Article, UUID> {

	Page<Article> findByStatus(ArticleStatus status, Pageable pageable);
}
