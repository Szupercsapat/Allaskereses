package com.rft.exceptions;

public class JobCategoryDoesNotExistsException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public JobCategoryDoesNotExistsException(String message) {
		super(message);
	}
}
