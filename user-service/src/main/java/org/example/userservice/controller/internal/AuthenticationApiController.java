package org.example.userservice.controller.internal;

import lombok.RequiredArgsConstructor;
import org.example.commonsservice.dto.auth.UserAuthenticationInfo;
import org.example.userservice.service.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class AuthenticationApiController {
    private final CustomerService customerService;

//    @GetMapping("/{email}")
//    public UserAuthenticationInfo getAuthenticationInfo(@PathVariable String email) {
//        return customerService.getAuthenticationInfo(email);
//    }
}
