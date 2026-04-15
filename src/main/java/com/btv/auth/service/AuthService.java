package com.btv.auth.service;

import com.btv.auth.dto.LoginRequest;
import com.btv.auth.dto.LoginResponse;
import com.btv.security.jwt.JwtUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final AuthenticationManager authenticationManager;
	
	private final JwtUtil jwtUtil;

	
	public LoginResponse login(LoginRequest request) {

		authenticationManager.authenticate(
		        new UsernamePasswordAuthenticationToken(
		                request.getUsername(),
		                request.getPassword()
		        )
		);

		String token = jwtUtil.generateToken(request.getUsername());

		return new LoginResponse(token);
		

		}


}
