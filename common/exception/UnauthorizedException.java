package com.ktn3.core_banking.common.exception;

// chặn anonymousUser
public class UnauthorizedException extends RuntimeException {
	
	public UnauthorizedException() {	
		super("User not authenticated");
	}
	
	public UnauthorizedException(String message) {	
		super(message);
	}

}
