package org.example.commonsservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.Map;

public final class ResponseUtil {
    public static <T> ResponseEntity<ApiResponse<T>> success(T data, String message) {
        return ResponseEntity.ok(
                ApiResponse.<T>builder()
                        .timestamp(Instant.now())
                        .status(HttpStatus.OK.value())
                        .message(message)
                        .data(data)
                        .build()
        );
    }

    public static ResponseEntity<ApiResponse<Void>> error(
            HttpStatus status,
            String message,
            Map<String, String> errors,
            String path) {

        return ResponseEntity.status(status)
                .body(
                        ApiResponse.<Void>builder()
                                .timestamp(Instant.now())
                                .status(status.value())
                                .message(message)
                                .errors(errors)
                                .build()
                );
    }

}
