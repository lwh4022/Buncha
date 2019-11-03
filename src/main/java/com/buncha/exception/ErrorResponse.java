package com.buncha.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ErrorResponse {

	private final HttpStatus status;
	private final String message;
	private final Date timestamp;
	
	protected ErrorResponse(final String message, HttpStatus status) {
		this.message = message;
		this.status = status;
		this.timestamp = new Date();
	}
	
	public static ErrorResponse of(final String message, HttpStatus status) {
		return new ErrorResponse(message, status);
	}
	
	
	
}
