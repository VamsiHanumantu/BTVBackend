package com.btv.tag.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.btv.tag.entity.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, UUID> {

	List<Tag> findByIsActiveTrue();


}
