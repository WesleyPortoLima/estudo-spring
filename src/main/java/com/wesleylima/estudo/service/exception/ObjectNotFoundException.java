package com.wesleylima.estudo.service.exception;

public class ObjectNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 4434243638181134638L;

	public ObjectNotFoundException(String msg) {
		super(msg);
	}
	
	public ObjectNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
