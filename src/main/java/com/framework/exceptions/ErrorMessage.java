package com.framework.exceptions;

/**
 * This class contains error messages
 * 
 * @author abhishek.tewari
 *
 */
public enum ErrorMessage {

	VALIDATION_FAILED("BAD_REQUEST", "Validation failed"),
	WRONG_FORMAT("BAD_REQUEST", "Wrong content-type of the request format. Expected content-type is application/json."),
	CONFLICT("CONFLICT", "Database error");

	private String status;
	private String message;

	private ErrorMessage(String status, String message) {
		this.status = status;
		this.message = message;
	}

	public String getStatus() {
		return this.status;
	}

	public String getMessage() {
		return this.message;
	}
}
