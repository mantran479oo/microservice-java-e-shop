package org.example.userservice.service;

import org.example.userservice.dto.request.AuthenticationRequest;
import org.example.userservice.dto.request.RegistrationRequestDTO;
import org.example.commonsservice.dto.auth.UserAuthenticationInfo;
import org.example.userservice.dto.response.UserResponse;
import org.springframework.validation.BindingResult;

import java.util.Map;

public interface CustomerService {
    UserResponse registration(RegistrationRequestDTO request, BindingResult bindingResult);
    Map<String, String> login(AuthenticationRequest request, BindingResult bindingResult);
}
