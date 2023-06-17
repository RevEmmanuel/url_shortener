package org.example.exceptions;

import org.springframework.http.HttpStatus;

public class UserNotAuthorizedException extends UrlShortenerException {

    public UserNotAuthorizedException() {
        this("Unauthorized");
    }

    public UserNotAuthorizedException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
