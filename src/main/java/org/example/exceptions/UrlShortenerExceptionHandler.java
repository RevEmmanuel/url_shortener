package org.example.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UrlShortenerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UrlShortenerException.class)
    public ResponseEntity<UrlShortenerExceptionResponse> apiRequestExceptionHandler(UrlShortenerException e) {
        UrlShortenerExceptionResponse response =
                UrlShortenerExceptionResponse.builder()
                        .message(e.getMessage())
                        .status(e.getStatus())
                        .build();
        return new ResponseEntity<>(response, e.getStatus());
    }
}