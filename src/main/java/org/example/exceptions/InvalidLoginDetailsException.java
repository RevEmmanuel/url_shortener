package org.example.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidLoginDetailsException extends UrlShortenerException {

    public InvalidLoginDetailsException() {
        this("Login credentials incorrect!");
    }

    public InvalidLoginDetailsException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
