package com.rfm.packagegeneration.exception;

import java.util.Date;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleAllExceptions( Exception ex, WebRequest request) {
        
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Date(),
                "An error occured.",
                request.getDescription(false));
        
        return new ResponseEntity<>( exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler( EmptyResultDataAccessException.class)
    public final ResponseEntity<ExceptionResponse> handleNotFoundExceptions(Exception ex, WebRequest request) {
        
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Date(),
                "It was not possible to process the request. "
                + "One or more parameters results in a query with no results "
                + "in the database. Verify the given informations and try again. "
                + "If the problem persists, please inform the support team",
                request.getDescription(false));
        
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }
}
