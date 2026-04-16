package com.btv.category.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.btv.category.entity.Category;

@Repository 
public interface CategoryRepository extends JpaRepository<Category, UUID> {

	Page<Category> findByIsActiveTrue(Pageable pageable);

}
