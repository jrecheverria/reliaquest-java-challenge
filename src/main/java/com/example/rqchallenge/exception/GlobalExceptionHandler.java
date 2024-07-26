package com.example.rqchallenge.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientException;

import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    //Exception for when calling the external API
    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<String> handleRestClientException(RestClientException ex) {
        logger.error("Rest client error: {}", ex.getMessage());
        return new ResponseEntity<>("An error occurred while calling an external service.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //Exception for processing a request
    public ResponseEntity<String> handleIOException(IOException ex) {
        logger.error("IOException occurred: {}", ex.getMessage());
        return new ResponseEntity<>("An error occurred while processing the request", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
