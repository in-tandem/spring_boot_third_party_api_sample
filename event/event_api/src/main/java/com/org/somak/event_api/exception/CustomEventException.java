package com.org.somak.event_api.exception;

public class CustomEventException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -271806888453342376L;

	private ExceptionBean exception;
	
	public CustomEventException() {
		super();
	}

	public CustomEventException(String message) {
		super(message);
	}
	
	public CustomEventException(ExceptionBean bean) {
		this.exception=bean;
	}

	public ExceptionBean getException() {
		return exception;
	}

	public void setException(ExceptionBean exception) {
		this.exception = exception;
	}
	
	

}
