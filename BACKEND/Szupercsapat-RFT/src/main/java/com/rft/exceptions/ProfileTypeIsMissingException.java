package com.rft.exceptions;

public class ProfileTypeIsMissingException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ProfileTypeIsMissingException(String message) {
		super(message);
	}
}
