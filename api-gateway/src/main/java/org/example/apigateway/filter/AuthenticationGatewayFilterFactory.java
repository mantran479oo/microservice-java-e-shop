package org.example.apigateway.filter;

import io.jsonwebtoken.Claims;
import org.example.commonsservice.security.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class AuthenticationGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthenticationGatewayFilterFactory.Config> {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationGatewayFilterFactory.class);
    private final JwtService jwtService;

    public AuthenticationGatewayFilterFactory(JwtService jwtService) {
        super(Config.class);
        this.jwtService = jwtService;
        log.info("===================----------------------------------");
    }

    @Override
    public GatewayFilter apply(Config config) {
        log.info("=============12321321312======----------------------------------");
        return ((exchange, chain) -> {
            List<String> authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || authHeader.isEmpty()) {
                return this.onError(exchange, "Empty Authorization Header", HttpStatus.UNAUTHORIZED);
            }
            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return this.onError(exchange, "Missing Authorization Header", HttpStatus.UNAUTHORIZED);
            }
            String auther = authHeader.get(0);
            if (auther.startsWith("Bearer ")) {
                auther = auther.substring(7);
            }
            try {
                jwtService.validateToken(auther);
                Claims claims = jwtService.getClaims(auther).getPayload();
                exchange = exchange.mutate()
                        .request(r -> r.header("X-User-Id", claims.getSubject())
                                .header("X-User-Roles", claims.get("roles", String.class)))
                        .build();
            } catch (Exception e) {
                return this.onError(exchange, "Invalid or Expired API Token", HttpStatus.UNAUTHORIZED);
            }
            log.info("===================---12321312");
            return chain.filter(exchange);
        });
    }
    private Mono onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }
    public static class Config {
    }
}
