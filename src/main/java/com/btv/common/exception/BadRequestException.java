package com.btv.common.exception;

public class BadRequestException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2914246835274371424L;
	
	public BadRequestException(String message) {
		super(message);
	}
}
