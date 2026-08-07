package com.example.productservice.controller;

import org.example.commonsservice.constants.PathConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping(PathConstants.API_V1 + "/products")
public class ProductController {

    @GetMapping("/ping")
    public ResponseEntity<Map<String, Object>> ping() {
        return ResponseEntity.ok(Map.of(
                "service", "product-service",
                "status", "UP",
                "timestamp", Instant.now().toString()
        ));
    }
}
