package com.khun.exception;

public class AccountNotFoundException extends RuntimeException {


	/**
	 * 
	 */
	private static final long serialVersionUID = -8524346981440479954L;

	public AccountNotFoundException() {
        super();
    }

    public AccountNotFoundException(String message) {
        super(message);
    }

    public AccountNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountNotFoundException(Throwable cause) {
        super(cause);
    }
}

