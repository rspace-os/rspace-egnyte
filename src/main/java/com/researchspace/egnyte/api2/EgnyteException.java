package com.researchspace.egnyte.api2;

public class EgnyteException extends RuntimeException {

	public EgnyteException(String string, Exception e) {
		super(string,e);
	}

	public EgnyteException(String string) {
		super(string);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
