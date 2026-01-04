package com.btv.common.exception;

public class ResourceNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2100372114577643301L;

	public ResourceNotFoundException(String message) {
		super(message);
	}
}
