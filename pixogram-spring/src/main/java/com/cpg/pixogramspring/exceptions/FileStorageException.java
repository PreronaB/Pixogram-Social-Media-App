package com.cpg.pixogramspring.exceptions;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class FileStorageException extends RuntimeException {
	
	public FileStorageException(String message) {
		super(message);
	
	}
	
	public FileStorageException(String message, IOException ex) {
		super(message);
	
	}

}
