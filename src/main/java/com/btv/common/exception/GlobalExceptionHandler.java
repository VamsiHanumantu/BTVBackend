package com.btv.common.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;   
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiErrorResponse> handleResourceNotFound(ResourceNotFoundException ex){
		return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND, null);
	}
	
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ApiErrorResponse> handleBadRequest(BadRequestException ex){
		return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, null);
	}
	
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<ApiErrorResponse> handleUnauthorized(UnauthorizedException ex){
		return buildResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED, null);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiErrorResponse> handleValidation(MethodArgumentNotValidException ex){
		Map<String,String> errors = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(),error.getDefaultMessage()));
		return buildResponse("Validation Failed", HttpStatus.BAD_REQUEST, errors);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiErrorResponse> handleGeneric(Exception ex){
		return buildResponse("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR, null);
	}
	
	
	private ResponseEntity<ApiErrorResponse> buildResponse(String message,HttpStatus status, Map<String, String> errors){
		ApiErrorResponse response = ApiErrorResponse.builder()
				.success(false)
				.message(message)
				.status(status.value())
				.timestamps(LocalDateTime.now())
				.errors(errors)
				.build();
		return new ResponseEntity<>(response,status);
	}
}
