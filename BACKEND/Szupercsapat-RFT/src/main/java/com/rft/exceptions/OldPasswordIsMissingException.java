package com.rft.exceptions;

public class OldPasswordIsMissingException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public OldPasswordIsMissingException(String message) {
		super(message);
	}
}
