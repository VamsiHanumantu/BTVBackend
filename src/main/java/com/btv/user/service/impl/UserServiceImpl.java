package com.btv.user.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.btv.common.exception.ResourceNotFoundException;
import com.btv.user.dto.UserCreateRequest;
import com.btv.user.dto.UserResponse;
import com.btv.user.dto.UserUpdateRequest;
import com.btv.user.entity.User;
import com.btv.user.mapper.UserMapper;
import com.btv.user.repository.UserRepository;
import com.btv.user.service.UserService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	
	private final UserMapper userMapper;
	
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public UserResponse createUser(UserCreateRequest request) {
	    User user = userMapper.toEntity(request);
	    user.setPassword(passwordEncoder.encode(request.getPassword()));
	    User savedUser = userRepository.save(user);
		return userMapper.toResponse(savedUser);
	}

	@Override
	public UserResponse updateUser(UUID userId,UserUpdateRequest request) {
		User user = userRepository.findById(userId).orElseThrow(
				()-> new ResourceNotFoundException("User Not Found")
				);
		userMapper.updateEntity(request, user);
		
		return userMapper.toResponse(user);
	}

	@Override
	public UserResponse getUserById(UUID userId) {
		User user = userRepository.findById(userId).orElseThrow(
				()-> new ResourceNotFoundException("User not found")
				);
		return userMapper.toResponse(user);
	}

	@Override
	public List<UserResponse> getAllUsers() {
		List<User> users = userRepository.findAll();
		return users.stream().map(userMapper::toResponse).toList();
	}

}
