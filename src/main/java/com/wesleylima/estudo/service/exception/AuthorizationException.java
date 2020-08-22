package com.wesleylima.estudo.service.exception;

public class AuthorizationException extends RuntimeException {
	private static final long serialVersionUID = 4434243638181134638L;

	public AuthorizationException(String msg) {
		super(msg);
	}
	
	public AuthorizationException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
