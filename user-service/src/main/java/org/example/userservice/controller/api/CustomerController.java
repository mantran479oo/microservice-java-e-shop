package org.example.userservice.controller.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.commonsservice.constants.PathConstants;
import org.example.commonsservice.exception.ApiResponse;
import org.example.commonsservice.exception.ResponseUtil;
import org.example.userservice.dto.request.AuthenticationRequest;
import org.example.userservice.dto.request.RegistrationRequestDTO;
import org.example.userservice.dto.response.AuthenticationResponse;
import org.example.userservice.dto.response.UserResponse;
import org.example.userservice.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(PathConstants.CUSTOMER)
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping(PathConstants.REGISTRATION)
    public ResponseEntity<ApiResponse<UserResponse>> registration(@Valid @RequestBody RegistrationRequestDTO request, BindingResult bindingResult){
        UserResponse customerRep = customerService.registration(request, bindingResult);
        return ResponseUtil.success(customerRep, "success");
    }
}
