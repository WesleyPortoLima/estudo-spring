package com.wesleylima.estudo.controller.exception;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError implements Serializable{
	private static final long serialVersionUID = 6489096073271614815L;

	private List<FieldMessage> errors = new ArrayList<>();

	public ValidationError(Long timestamp, Integer status, String error, String message, String path) {
		super(timestamp, status, error, message, path);
	}

	public List<FieldMessage> getList() {
		return errors;
	}

	public void setList(List<FieldMessage> list) {
		this.errors = list;
	}
	
	public void addError(String fieldName, String fieldMessage) {
		errors.add(new FieldMessage(fieldName, fieldMessage));
	}
	
}
