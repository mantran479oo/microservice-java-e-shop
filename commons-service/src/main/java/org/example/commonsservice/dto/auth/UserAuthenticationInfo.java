package org.example.commonsservice.dto.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class UserAuthenticationInfo {
    private Long id;
    private String fullName;
    private String username;
    private String password;
    private String role;
    private String email;
}
