package com.buncha.exception;

public class FileUploadException extends RuntimeException{

	private static final long serialVersionUID = -5793647472816024971L;
	
	public FileUploadException() {
	}


	public FileUploadException(String message) {
		super(message);
	}


	public FileUploadException(String message, Throwable cause) {
		super(message, cause);
	}

	
}
