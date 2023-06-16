package org.example.exceptions;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class UrlShortenerExceptionResponse {

    private String message;
    private HttpStatus status;

}
