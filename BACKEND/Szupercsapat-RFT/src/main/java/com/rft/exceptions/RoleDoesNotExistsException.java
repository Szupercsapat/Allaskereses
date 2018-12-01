package com.rft.exceptions;

public class RoleDoesNotExistsException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public RoleDoesNotExistsException(String message) {
		super(message);
	}
}
