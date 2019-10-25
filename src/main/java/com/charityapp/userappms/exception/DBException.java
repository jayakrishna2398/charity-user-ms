package com.charityapp.userappms.exception;

public class DBException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public DBException(String exceptionMessage)
	{
		super(exceptionMessage);
	}
	public DBException(String exceptionMessage, Throwable t)
	{
		super(exceptionMessage,t);
	}
}
