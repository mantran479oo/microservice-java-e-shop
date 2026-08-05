package org.example.userservice.service.impl;

import lombok.AllArgsConstructor;
import org.example.commonsservice.dto.auth.UserAuthenticationInfo;
import org.example.commonsservice.exception.InputFieldException;
import org.example.commonsservice.security.JwtService;
import org.example.userservice.configuration.AuthConfiguration;
import org.example.userservice.dto.request.AuthenticationRequest;
import org.example.userservice.dto.request.RegistrationRequestDTO;
import org.example.userservice.dto.response.UserResponse;
import org.example.userservice.helper.HelperData;
import org.example.userservice.mapper.CustomerMapper;
import org.example.userservice.model.Customer;
import org.example.userservice.repository.CustomerRepository;
import org.example.userservice.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final HelperData helperData;
    private final JwtService jwtService;
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final AuthConfiguration authConfiguration;

    @Transactional
    @Override
    public UserResponse registration(RegistrationRequestDTO request, BindingResult bindingResult){
        helperData.processInputErrors(bindingResult);
        Optional<Customer> customer = customerRepository.getCustomerByEmail(request.getEmail());
        request.setPassword(authConfiguration.getPasswordEncoder().encode(request.getPassword()));
        if (customer.isPresent()) throw new InputFieldException(HttpStatus.BAD_REQUEST, "Email already exists");
        Customer user = customerMapper.toEntity(request);

        return customerMapper.toResponse(customerRepository.save(user));
    }

    @Override
    public Map<String, String> login(AuthenticationRequest request, BindingResult bindingResult){
        helperData.processInputErrors(bindingResult);
        Customer customer = customerRepository.getCustomerByEmail(request.getEmail()).orElseThrow(()
                -> new InputFieldException(HttpStatus.NOT_FOUND, "Email not found"));
        String password = customer.getPassword();
        if (!authConfiguration.getPasswordEncoder().matches(request.getPassword(), password)){
            throw new InputFieldException(HttpStatus.NOT_FOUND, "Password not correct");
        }
        String token = jwtService.generateToken(customer.getId(), customer.getEmail(),  customer.getRole().name());
        return Map.of(
                "token", token,
                "refresh_token", "asdasdsadas"
        );
    }
}
