package com.barclays.cardsystem.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.barclays.cardsystem.utility.ErrorInfo;

/**
 * Exception Handler 
 * @author Akshata
 *
 */
@RestControllerAdvice
public class ExceptionControllerAdvice {
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorInfo> exceptionHandler(Exception exception) {
		ErrorInfo error = new ErrorInfo();
		error.setErrorMessage("Request cannot be processed");
		error.setErrorCode(HttpStatus.ACCEPTED.value());
		error.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<ErrorInfo>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@ExceptionHandler(SystemException.class)
	public ResponseEntity<ErrorInfo> barclaysExceptionHandler(SystemException exception) {
		ErrorInfo error = new ErrorInfo();
		error.setErrorMessage(exception.getMessage());
		error.setTimestamp(LocalDateTime.now());
		error.setErrorCode(HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<ErrorInfo>(error, HttpStatus.NOT_FOUND);
	}

}