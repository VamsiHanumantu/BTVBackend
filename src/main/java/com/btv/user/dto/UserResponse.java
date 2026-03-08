package com.btv.user.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {

	private UUID id;
	
	private String username;
	
	private String email;
	
	private String isActive;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
}
