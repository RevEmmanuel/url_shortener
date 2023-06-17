package org.example.exceptions;

import org.springframework.http.HttpStatus;

public class UsernameAlreadyExistsException extends UrlShortenerException {

    public UsernameAlreadyExistsException() {
        this("This Username is taken already!");
    }

    public UsernameAlreadyExistsException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
