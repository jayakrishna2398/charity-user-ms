package com.charityapp.userappms.exception;

public class ValidatorException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ValidatorException(String exceptionMessage)
	{
		super(exceptionMessage);
	}
	public ValidatorException(String exceptionMessage, Throwable t)
	{
		super(exceptionMessage, t);
	}
}
