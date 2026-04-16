package com.btv.auth;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.btv.auth.dto.SignupRequest;
import com.btv.user.dto.UserResponse;
import com.btv.user.service.UserService;

@SpringBootTest
@Transactional
class AuthSignupIntegrationTests {

	@Autowired
	private UserService userService;

	@Test
	void signupCreatesUserWithDefaultUserRole() {
		String suffix = UUID.randomUUID().toString();
		SignupRequest request = new SignupRequest();
		request.setUsername("signup-" + suffix);
		request.setEmail("signup-" + suffix + "@example.com");
		request.setPassword("password123");

		UserResponse response = userService.signup(request);

		assertThat(response.getId()).isNotNull();
		assertThat(response.getRoles()).containsExactly("USER");
	}
}
