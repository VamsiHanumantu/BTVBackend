package com.btv.user.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.btv.user.dto.UserCreateRequest;
import com.btv.user.dto.UserResponse;
import com.btv.user.dto.UserUpdateRequest;
import com.btv.auth.dto.SignupRequest;

public interface UserService {
	
	UserResponse createUser(UserCreateRequest request);

	UserResponse signup(SignupRequest request);
	
	UserResponse updateUser(UUID userId,UserUpdateRequest request);
	
	UserResponse getUserById(UUID userId);
	
	Page<UserResponse> getAllUsers(Pageable pageable);

	void deactivateUser(UUID userId);
}
