package com.bookmanagement.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(BookNotFoundException.class)
	public ResponseEntity<String> handleBookNotFound(BookNotFoundException e){
		return ResponseEntity.status(404).body(e.getMessage());
	}
	
	@ExceptionHandler(InvalidBookException.class)
	public ResponseEntity<String> handleInvalidBook(InvalidBookException e){
		return ResponseEntity.badRequest().body(e.getMessage());
	}
	
	@ExceptionHandler(DuplicateBookException.class)
	public ResponseEntity<String> handleDuplicateBook(DuplicateBookException e){
		return ResponseEntity.status(409).body(e.getMessage());
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> handleValidation(MethodArgumentNotValidException e){
		String error=e.getBindingResult().getFieldErrors().stream().map(err->err.getField()+": "+err.getDefaultMessage()).findFirst().orElse("Invalid input");
		return ResponseEntity.badRequest().body(error);
	}
}
