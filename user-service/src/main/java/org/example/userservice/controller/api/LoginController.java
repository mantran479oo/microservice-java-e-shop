package org.example.userservice.controller.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.commonsservice.exception.ApiResponse;
import org.example.commonsservice.exception.ResponseUtil;
import org.example.userservice.dto.request.AuthenticationRequest;
import org.example.userservice.dto.response.AuthenticationResponse;
import org.example.userservice.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class LoginController {

    private final CustomerService customerService;

    @PostMapping("/token")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> login(@Valid @RequestBody AuthenticationRequest request, BindingResult bindingResult){
        Map<String, String> authen = customerService.login(request, bindingResult);
        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                .token(authen.get("token"))
                .refreshToken(authen.get("refresh_token"))
                .build();
        return ResponseUtil.success(authenticationResponse, "success");
    }
}
