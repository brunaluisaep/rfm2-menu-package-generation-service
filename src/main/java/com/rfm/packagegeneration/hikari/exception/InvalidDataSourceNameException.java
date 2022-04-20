package com.rfm.packagegeneration.hikari.exception;

public class InvalidDataSourceNameException extends RuntimeException {

	private static final long serialVersionUID = -5712853377805711491L;

	private final String message;
	
	public InvalidDataSourceNameException(String message) {
		this.message = "Datasource " + message + " not found. Please check your datasources configuration";
	}

	public String getMessage() {
		return this.message;
	}
}	