package com.btv.user.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.btv.user.entity.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UUID> {

	List<UserRole> findByUserId(UUID userId);

	void deleteByUserId(UUID userId);

	boolean existsByRoleId(UUID roleId);
}
