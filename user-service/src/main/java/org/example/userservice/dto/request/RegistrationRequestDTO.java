package org.example.userservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class RegistrationRequestDTO {
    @NotBlank
    private String fullName;

    @NotBlank
    private String username;

    @NotBlank
    private String role;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
}
