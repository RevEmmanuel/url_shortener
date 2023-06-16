package org.example.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class UrlShortenerException extends RuntimeException {

    @Getter
    private HttpStatus status = HttpStatus.BAD_REQUEST;

    public UrlShortenerException(){
        this("Error Processing Request!");
    }

    public UrlShortenerException(String message){
        super(message);
    }

    public UrlShortenerException(String message, HttpStatus status) {
        this(message);
        this.status = status;
    }

}
