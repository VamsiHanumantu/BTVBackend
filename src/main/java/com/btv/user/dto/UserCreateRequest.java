package com.btv.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateRequest {

	@NotBlank(message = "Username is required")
	@Size(max = 50)
	private String username;
	
	@NotBlank(message = "Email is required")
	@Email(message = "Invalid message Format")
	private String email;
	
	@NotBlank(message = "Password is required")
	@Size(min = 6)
	private String password;
}
