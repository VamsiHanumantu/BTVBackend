package com.btv.article.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.btv.article.entity.ArticleTag;

@Repository
public interface ArticleTagRepository extends JpaRepository<ArticleTag, UUID> {

}
