package com.btv.user.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.btv.auth.dto.SignupRequest;
import com.btv.common.exception.BadRequestException;
import com.btv.common.exception.ResourceNotFoundException;
import com.btv.user.dto.UserCreateRequest;
import com.btv.user.dto.UserResponse;
import com.btv.user.dto.UserUpdateRequest;
import com.btv.user.entity.Role;
import com.btv.user.entity.User;
import com.btv.user.entity.UserRole;
import com.btv.user.enums.RoleType;
import com.btv.user.mapper.UserMapper;
import com.btv.user.repository.RoleRepository;
import com.btv.user.repository.UserRepository;
import com.btv.user.repository.UserRoleRepository;
import com.btv.user.service.UserService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;

	private final RoleRepository roleRepository;

	private final UserRoleRepository userRoleRepository;
	
	private final UserMapper userMapper;
	
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public UserResponse createUser(UserCreateRequest request) {
		validateUniqueUser(request.getUsername(), request.getEmail());
	    User user = userMapper.toEntity(request);
	    user.setPassword(passwordEncoder.encode(request.getPassword()));
	    User savedUser = userRepository.save(user);
	    syncRoles(savedUser, request.getRoleIds());
		return userMapper.toResponse(savedUser);
	}

	@Override
	public UserResponse signup(SignupRequest request) {
		validateUniqueUser(request.getUsername(), request.getEmail());

		User user = new User();
		user.setUsername(request.getUsername());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		User savedUser = userRepository.save(user);

		Role userRole = roleRepository.findByName(RoleType.USER)
				.orElseGet(this::createDefaultUserRole);
		assignRole(savedUser, userRole);

		return userMapper.toResponse(savedUser);
	}

	@Override
	public UserResponse updateUser(UUID userId,UserUpdateRequest request) {
		User user = userRepository.findById(userId).orElseThrow(
				()-> new ResourceNotFoundException("User Not Found")
				);
		userMapper.updateEntity(request, user);
		if (request.getRoleIds() != null) {
			syncRoles(user, request.getRoleIds());
		}
		
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
	public Page<UserResponse> getAllUsers(Pageable pageable) {
		return userRepository.findAll(pageable).map(userMapper::toResponse);
	}

	@Override
	public void deactivateUser(UUID userId) {
		User user = userRepository.findById(userId).orElseThrow(
				()-> new ResourceNotFoundException("User Not Found")
				);
		user.setIsActive(false);
	}

	private void syncRoles(User user, List<UUID> roleIds) {
		user.getUserRoles().clear();
		userRoleRepository.deleteByUserId(user.getId());

		if (roleIds == null || roleIds.isEmpty()) {
			return;
		}

		List<UUID> uniqueRoleIds = roleIds.stream().distinct().toList();
		List<Role> roles = roleRepository.findAllById(uniqueRoleIds);
		if (roles.size() != uniqueRoleIds.size()) {
			throw new BadRequestException("One or more roles were not found");
		}

		roles.forEach(role -> assignRole(user, role));
	}

	private void assignRole(User user, Role role) {
		UserRole userRole = new UserRole();
		userRole.setUser(user);
		userRole.setRole(role);
		UserRole savedUserRole = userRoleRepository.save(userRole);
		user.getUserRoles().add(savedUserRole);
	}

	private void validateUniqueUser(String username, String email) {
		if (userRepository.existsByUsername(username)) {
			throw new BadRequestException("Username is already taken");
		}
		if (userRepository.existsByEmail(email)) {
			throw new BadRequestException("Email is already taken");
		}
	}

	private Role createDefaultUserRole() {
		Role role = new Role();
		role.setName(RoleType.USER);
		role.setDescription("Default user role");
		return roleRepository.save(role);
	}

}
