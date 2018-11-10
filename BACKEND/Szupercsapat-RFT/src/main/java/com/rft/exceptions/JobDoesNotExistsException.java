package com.rft.exceptions;

public class JobDoesNotExistsException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public JobDoesNotExistsException(String message) {
		super(message);
	}
}
