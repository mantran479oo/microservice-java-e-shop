package org.example.userservice.service.impl;

import lombok.AllArgsConstructor;
import org.example.commonsservice.exception.InputFieldException;
import org.example.commonsservice.security.JwtService;
import org.example.userservice.dto.request.AuthenticationRequest;
import org.example.userservice.dto.request.RegistrationRequestDTO;
import org.example.userservice.dto.response.UserResponse;
import org.example.userservice.helper.HelperData;
import org.example.userservice.mapper.CustomerMapper;
import org.example.userservice.model.Customer;
import org.example.userservice.model.UserRole;
import org.example.userservice.repository.CustomerRepository;
import org.example.userservice.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import java.util.Map;
import java.util.Locale;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final HelperData helperData;
    private final JwtService jwtService;
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     *
     * @param request
     * @param bindingResult
     * @return
     */
    @Transactional
    @Override
    public UserResponse registration(RegistrationRequestDTO request, BindingResult bindingResult){
        helperData.processInputErrors(bindingResult);
        request.setEmail(request.getEmail().trim().toLowerCase(Locale.ROOT));
        if (customerRepository.getCustomerByEmail(request.getEmail()).isPresent()) {
            throw new InputFieldException(HttpStatus.CONFLICT, "Email already exists");
        }
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        Customer user = customerMapper.toEntity(request);
        user.setRole(UserRole.USER);

        return customerMapper.toResponse(customerRepository.save(user));
    }

    /**
     *
     * @param request
     * @param bindingResult
     * @return
     */
    @Override
    public Map<String, String> login(AuthenticationRequest request, BindingResult bindingResult){
        helperData.processInputErrors(bindingResult);
        Customer customer = customerRepository.getCustomerByEmail(request.getEmail()).orElseThrow(()
                -> new InputFieldException(HttpStatus.UNAUTHORIZED, "Invalid email or password"));
        String password = customer.getPassword();
        if (!passwordEncoder.matches(request.getPassword(), password)){
            throw new InputFieldException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }
        String token = jwtService.generateToken(customer.getId(), customer.getEmail(),  customer.getRole().name());
        String refreshToken = jwtService.generateRefreshToken(customer.getId(), customer.getEmail(),  customer.getRole().name());
        return Map.of(
                "token", token,
                "refresh_token", refreshToken
        );
    }
}
