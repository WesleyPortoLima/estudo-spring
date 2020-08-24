package com.wesleylima.estudo.service.exception;

public class FileException extends RuntimeException {
	private static final long serialVersionUID = 4434243638181134638L;

	public FileException(String msg) {
		super(msg);
	}
	
	public FileException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
