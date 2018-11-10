package com.rft.exceptions;

public class UsernameIsMissingException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public UsernameIsMissingException(String message) {
		super(message);
	}
}
