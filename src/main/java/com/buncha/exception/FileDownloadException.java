package com.buncha.exception;

public class FileDownloadException extends RuntimeException{

	
	private static final long serialVersionUID = 5220119001152978439L;

	public FileDownloadException() {
	}

	public FileDownloadException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileDownloadException(String message) {
		super(message);
	}

	
}
