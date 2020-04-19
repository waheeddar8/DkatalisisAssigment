package com.dkatalis.parkinglot.exception;

public class LogicError extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1727578352648112L;

	public LogicError(String message, Throwable cause) {
		super(message, cause);
    }
	
	public LogicError(String message) {
		super(message);
    }
}
