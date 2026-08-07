package org.example.commonsservice.security;

import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.example.commonsservice.constants.ErrorMessage;
import org.example.commonsservice.exception.JwtAuthenticationException;
import org.springframework.http.HttpStatus;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.io.Decoders;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;

@RequiredArgsConstructor
public class JwtService {
    private static final String TOKEN_TYPE_CLAIM = "token_type";
    private static final String ACCESS_TOKEN = "ACCESS";
    private static final String REFRESH_TOKEN = "REFRESH";

    private final JwtProperties jwtProperties;

    /**
     *
     * @param userId
     * @param username
     * @param role
     * @return
     */
    public String generateRefreshToken(Long userId, String username, String role) {
        return buildToken(userId, username, role, jwtProperties.getRefreshTokenExpiration(), REFRESH_TOKEN);
    }

    /**
     *
     * @param userId
     * @param username
     * @param role
     * @return
     */
    public String generateToken(Long userId, String username, String role) {
        return buildToken(userId, username, role, jwtProperties.getAccessTokenExpiration(), ACCESS_TOKEN);
    }

    /**
     *
     * @param userId
     * @param username
     * @param role
     * @param expiration
     * @return String
     */
    private String buildToken(Long userId, String username, String role, Duration expiration, String tokenType) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expiration.toMillis());
        return Jwts.builder()
                .subject(String.valueOf(userId))
                .issuer(jwtProperties.getIssuer())
                .issuedAt(now)
                .expiration(expiry)
                .claim("username", username)
                .claim("role", role)
                .claim(TOKEN_TYPE_CLAIM, tokenType)
                .signWith(getSignKey())
                .compact();
    }

    /**
     *
     * @param token
     * @return
     */
    public boolean validateToken(final String token) {
        parseAccessToken(token);
        return true;
    }

    public Claims parseAccessToken(final String token) {
        try {
            Jws<Claims> claimsJws = getClaims(token);
            Claims claims = claimsJws.getPayload();
            if (claims.getExpiration().before(new Date())
                    || !ACCESS_TOKEN.equals(claims.get(TOKEN_TYPE_CLAIM, String.class))
                    || !jwtProperties.getIssuer().equals(claims.getIssuer())) {
                throw new IllegalArgumentException("Invalid access token claims");
            }
            return claims;
        } catch (JwtException | IllegalArgumentException exception) {
            throw new JwtAuthenticationException(ErrorMessage.JWT_TOKEN_EXPIRED, HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     *
     * @param  token
     */
    public Jws<Claims> getClaims(final String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) getSignKey())
                .build()
                .parseSignedClaims(token);
    }

    /**
     *
     * @return
     */
    private SecretKey getSignKey() {
        if (jwtProperties.getSecret() == null || jwtProperties.getSecret().isBlank()) {
            throw new IllegalStateException("jwt.secret must be configured");
        }
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecret());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
