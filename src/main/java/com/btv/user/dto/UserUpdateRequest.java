package com.btv.user.dto;

import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequest {

	@Size(max = 50)
	private String username;
	
	@Email(message = "Invalid email format")
	private String email;
	
	private Boolean isActive;

	private List<UUID> roleIds;
	
}
