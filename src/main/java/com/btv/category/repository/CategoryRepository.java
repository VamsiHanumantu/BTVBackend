package com.btv.category.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.btv.category.entity.Category;

@Repository 
public interface CategoryRepository extends JpaRepository<Category, UUID> {

	List<Category> findByisActiveTrue();

}
