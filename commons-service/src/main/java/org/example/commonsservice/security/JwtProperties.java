package org.example.commonsservice.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "jwt")
@Getter
@Setter
public class JwtProperties {
    private String secret;
    private String issuer = "project-javajava";
    private Duration accessTokenExpiration = Duration.ofMinutes(15);
    private Duration refreshTokenExpiration = Duration.ofDays(7);
}
