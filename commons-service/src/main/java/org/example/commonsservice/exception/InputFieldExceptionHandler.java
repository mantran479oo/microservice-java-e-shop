package org.example.commonsservice.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class InputFieldExceptionHandler {
    @ExceptionHandler(InputFieldException.class)
    public ResponseEntity<ApiResponse<Void>> handleInputFieldException(InputFieldException exception, HttpServletRequest request) {
        return ResponseUtil.error(
                exception.getStatus(),
                exception.getMessage(),
                exception.getErrorsMap(),
                request.getRequestURI()
        );
    }
}
