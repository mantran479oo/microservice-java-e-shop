package org.example.commonsservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Getter
@AllArgsConstructor
public class InputFieldException extends RuntimeException {
    private final HttpStatus status;
    private final Map<String, String> errorsMap;
    private BindingResult bindingResult;

    public InputFieldException(BindingResult bindingResult) {
        super("Input validation failed");
        this.status = HttpStatus.BAD_REQUEST;
        this.errorsMap = handleErrors(bindingResult);
        this.bindingResult = bindingResult;
    }

    public InputFieldException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.errorsMap = null;
    }

    private Map<String, String> handleErrors(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        bindingResult.getFieldErrors().forEach(fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage()));
        return errors;
    }
}
