package com.khun.exception;

public class AccountDisableException extends RuntimeException
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2785664863212192472L;
	public AccountDisableException() {
		super();
	}
	public AccountDisableException(String message) {
		super(message);
	}
}
