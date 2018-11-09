package com.rft.exceptions;

public class ProfileImageIsMissingForCvException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ProfileImageIsMissingForCvException(String message) {
		super(message);
	}
}
