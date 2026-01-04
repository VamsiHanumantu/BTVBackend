package com.btv.common.exception;

public class UnauthorizedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8845889742263100331L;
	
	public UnauthorizedException(String message) {
		super(message);
	}
}
