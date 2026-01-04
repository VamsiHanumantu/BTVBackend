package com.btv.common.exception;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiErrorResponse {
	private boolean success;
	private String message;
	private int status;
	private LocalDateTime timestamps;
	private Map<String, String> errors;
}
