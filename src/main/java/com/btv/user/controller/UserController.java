package com.btv.user.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.btv.user.dto.UserCreateRequest;
import com.btv.user.dto.UserResponse;
import com.btv.user.dto.UserUpdateRequest;
import com.btv.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	
	@PostMapping
	public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserCreateRequest request){		
		UserResponse response = userService.createUser(request);
		return new ResponseEntity<UserResponse>(response, HttpStatus.CREATED);	
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<UserResponse> updateUser(@PathVariable UUID id, @Valid @RequestBody UserUpdateRequest request){
		UserResponse response = userService.updateUser(id, request);
		return new ResponseEntity<UserResponse>(response, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> getUserById(@PathVariable UUID id){
		UserResponse response = userService.getUserById(id);
		return new ResponseEntity<UserResponse>(response, HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<UserResponse>> getAllUser(){
		List<UserResponse> response  = userService.getAllUsers();
		return new ResponseEntity<List<UserResponse>>(response,HttpStatus.OK);
	}
}
