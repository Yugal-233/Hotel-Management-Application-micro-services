package com.hotel.exceptions;

public class ResourceNotFoundExc extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ResourceNotFoundExc(String msg) {
		super(msg);
	}
	public ResourceNotFoundExc() {
		super("Resource not found");
	}
}
