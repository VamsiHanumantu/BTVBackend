package com.btv.user.service;

import java.util.List;
import java.util.UUID;

import com.btv.user.dto.UserCreateRequest;
import com.btv.user.dto.UserResponse;
import com.btv.user.dto.UserUpdateRequest;

public interface UserService {
	
	UserResponse createUser(UserCreateRequest request);
	
	UserResponse updateUser(UUID userId,UserUpdateRequest request);
	
	UserResponse getUserById(UUID userId);
	
	List<UserResponse> getAllUsers();
}
