package org.example.userservice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class AuthenticationResponse {

    private String token;

    @JsonProperty("refresh_token")
    private String refreshToken;
}
