package com.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.user.payload.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundExc.class)
	public ResponseEntity<ApiResponse> handleResourceNotFoundExc(ResourceNotFoundExc ex){
		
		String msg = ex.getMessage();
		ApiResponse resp= new ApiResponse();
		resp.setMessage(msg);
		resp.setHttpStatus(HttpStatus.NOT_FOUND);
		resp.setSuccess(true);
		return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
	}
}
