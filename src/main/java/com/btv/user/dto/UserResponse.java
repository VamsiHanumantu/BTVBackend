package com.btv.user.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {

	private UUID id;
	
	private String username;
	
	private String email;
	
	private Boolean isActive;

	private List<String> roles;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
}
