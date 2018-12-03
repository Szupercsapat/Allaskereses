package com.rft.exceptions;

public class ProfileDoesNotExistsException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ProfileDoesNotExistsException(String message) {
		super(message);
	}
}
