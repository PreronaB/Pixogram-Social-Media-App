package com.cpg.pixogramspring.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotAUserException extends RuntimeException {
	
	public NotAUserException(String message) {
		super(message);
	}

}
