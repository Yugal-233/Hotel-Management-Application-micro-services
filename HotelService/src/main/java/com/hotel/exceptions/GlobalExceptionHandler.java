package com.hotel.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundExc.class)
	public ResponseEntity<Map<String, Object>> resNotFoundExc(ResourceNotFoundExc ex){
		Map myMap =  new HashMap<>();
		myMap.put("message", ex.getMessage());
		myMap.put("success", false);
		myMap.put("status", HttpStatus.NOT_FOUND);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(myMap);
		
	}
}
