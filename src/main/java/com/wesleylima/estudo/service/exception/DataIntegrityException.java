package com.wesleylima.estudo.service.exception;

public class DataIntegrityException extends RuntimeException {
	private static final long serialVersionUID = 4434243638181134638L;

	public DataIntegrityException(String msg) {
		super(msg);
	}
	
	public DataIntegrityException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
