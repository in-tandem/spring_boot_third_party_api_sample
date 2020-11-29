package com.org.somak.event_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomizedExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(CustomEventException.class)
	public ResponseEntity<ExceptionBean> handleAllCustomException(CustomEventException ex, WebRequest request) throws Exception {

		return new ResponseEntity<ExceptionBean>(ex.getException(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
