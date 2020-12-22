package com.cpg.pixogramspring.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ValidationException extends Exception {
	String message;

	public ValidationException(String message) {
		super();
		this.message = message;
	}

	@Override
	public String toString() {
		return "ValidationException [message=" + message + "]";
	}

}
