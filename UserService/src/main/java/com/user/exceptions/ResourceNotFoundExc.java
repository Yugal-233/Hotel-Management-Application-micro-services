package com.user.exceptions;

public class ResourceNotFoundExc extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public ResourceNotFoundExc() {
		super("Resource not found on the server");
	}
	public ResourceNotFoundExc(String msg) {
		super(msg);
	}
}
