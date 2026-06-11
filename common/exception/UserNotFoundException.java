package com.ktn3.core_banking.common.exception;

public class UserNotFoundException
	extends RuntimeException {
	
	public UserNotFoundException() {	
		super("User not found");
	}
	
	public UserNotFoundException(String message) {	
		super(message);
	}
}
