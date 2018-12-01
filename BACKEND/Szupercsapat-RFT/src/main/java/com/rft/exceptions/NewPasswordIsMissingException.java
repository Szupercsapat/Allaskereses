package com.rft.exceptions;

public class NewPasswordIsMissingException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public NewPasswordIsMissingException(String message) {
		super(message);
	}
}
