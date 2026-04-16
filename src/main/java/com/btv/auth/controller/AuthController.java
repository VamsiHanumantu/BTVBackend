package com.btv.auth.controller;

import com.btv.auth.dto.LoginRequest;
import com.btv.auth.dto.LoginResponse;
import com.btv.auth.dto.SignupRequest;
import com.btv.auth.service.AuthService;
import com.btv.user.dto.UserResponse;
import com.btv.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	private final UserService userService;

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
		return ResponseEntity.ok(authService.login(request));
	}

	@PostMapping("/signup")
	public ResponseEntity<UserResponse> signup(@Valid @RequestBody SignupRequest request) {
		UserResponse response = userService.signup(request);
		return new ResponseEntity<UserResponse>(response, HttpStatus.CREATED);
	}

}
