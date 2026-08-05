package org.example.commonsservice.exception;

import org.springframework.http.HttpStatus;

public class JwtAuthenticationException extends RuntimeException {

    private HttpStatus httpStatus;

    /**
     *
     * @param msg
     */
    public JwtAuthenticationException(String msg) {
        super(msg);
    }


    /**
     *
     * @param msg
     * @param httpStatus
     */
    public JwtAuthenticationException(String msg, HttpStatus httpStatus) {
        super(msg);
        this.httpStatus = httpStatus;
    }
}
