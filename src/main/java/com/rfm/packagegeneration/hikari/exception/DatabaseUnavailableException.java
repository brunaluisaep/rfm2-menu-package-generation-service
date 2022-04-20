package com.rfm.packagegeneration.hikari.exception;

public class DatabaseUnavailableException extends RuntimeException {

	private static final long serialVersionUID = -5712853377805711491L;

	private final String message;
	
	public DatabaseUnavailableException(String name) {
		this.message = "Database " + name + " is unavailable. Try again later.";
	}

	public String getMessage() {
		return this.message;
	}
}	