package com.api_gateway.api_gateway.exception;

public class MissingHeaderException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7796471886309562069L;
	public MissingHeaderException(String message) {
        super(message);
    }
}
