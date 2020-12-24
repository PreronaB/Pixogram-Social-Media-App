package com.cpg.pixogramspring.exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(UserAlreadyExistsException.class)
	public final ResponseEntity<Object> handleUserAlreadyExistsException(UserAlreadyExistsException ex, WebRequest request) {
	
	 ExceptionResponse exceptionResponse  = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
	 
	 return new ResponseEntity(exceptionResponse, HttpStatus.UNPROCESSABLE_ENTITY);
	
    }
	
	@ExceptionHandler(UserNotFoundException.class)
	public final ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
	
	 ExceptionResponse exceptionResponse  = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
	 
	 return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
	
    }
	
	@ExceptionHandler(ValidationException.class)
	public final ResponseEntity<Object> handleValidationException(ValidationException ex, WebRequest request) {
	
	 ExceptionResponse exceptionResponse  = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
	 
	 return new ResponseEntity(exceptionResponse, HttpStatus.UNPROCESSABLE_ENTITY);
	
    }
	
	@ExceptionHandler(ContentNotFoundException.class)
	public final ResponseEntity<Object> handleContentNotFoundException(ContentNotFoundException ex, WebRequest request) {
	
	 ExceptionResponse exceptionResponse  = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
	 
	 return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
	
    }

	@ExceptionHandler(CommentNotExistsException.class)
	public final ResponseEntity<Object> handleCommentNotExistsException(CommentNotExistsException ex, WebRequest request) {
	
	 ExceptionResponse exceptionResponse  = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
	 
	 return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
	
    }


}
