package org.example.exceptions;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends UrlShortenerException {

    public UserNotFoundException() {
        this("Not Found");
    }

    public UserNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}

