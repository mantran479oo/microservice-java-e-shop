package org.example.userservice.dto.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserResponse {

    private String fullName;

    private String username;

    private String role;

    private String email;
}
