package org.example.exceptions;

import org.springframework.http.HttpStatus;

public class LinkNotFoundException extends UrlShortenerException {

    public LinkNotFoundException() {
        this("Link cannot be found!");
    }

    public LinkNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
