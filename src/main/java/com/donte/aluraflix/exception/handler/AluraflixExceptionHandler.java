package com.donte.aluraflix.exception.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.donte.aluraflix.exception.BusinessException;
import com.donte.aluraflix.exception.CustomError;

@RestControllerAdvice
public class AluraflixExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);		
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		CustomError error = new CustomError(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
		return handleExceptionInternal(ex, error, headers, HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		CustomError error = new CustomError(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
		return handleExceptionInternal(ex, error, headers, HttpStatus.BAD_REQUEST, request);				
	}

	@ExceptionHandler(value = BusinessException.class)
	public ResponseEntity<CustomError> handleBadCredentialsException(BusinessException ex) {
		CustomError error = new CustomError(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}  

	@ExceptionHandler({ EmptyResultDataAccessException.class })
	public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex) {
		CustomError error = new CustomError("Entity not found", HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
/*
	@ExceptionHandler({ DataIntegrityViolationException.class })
	public ResponseEntity<Object> handleConstraintViolation(Exception ex, HttpHeaders headers, WebRequest request) {
		Throwable cause = ((DataIntegrityViolationException) ex).getRootCause();
		if (cause instanceof ConstraintViolationException) {        
			ConstraintViolationException consEx= (ConstraintViolationException) cause;
			Map<String, String> errors = new HashMap<>();
			for (final ConstraintViolation<?> violation : consEx.getConstraintViolations()) {
				errors.put(violation.getPropertyPath().toString(), violation.getMessage());
			}
			return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
		}
		return handleExceptionInternal(ex, ex.getLocalizedMessage(), headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
	}
	*/

	/*
	 @ExceptionHandler(value = EntityNotFoundException.class)
	public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
		CustomError error = new CustomError("Entity not found", HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = BadCredentialsException.class)
	public ResponseEntity<CustomError> handleBadCredentialsException(BadCredentialsException e) {
		CustomError error = new CustomError(e.getMessage(), HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	} */

}
