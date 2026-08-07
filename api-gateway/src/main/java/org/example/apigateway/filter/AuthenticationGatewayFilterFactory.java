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

@Component
public class AuthenticationGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthenticationGatewayFilterFactory.Config> {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationGatewayFilterFactory.class);
    private final JwtService jwtService;

    public AuthenticationGatewayFilterFactory(JwtService jwtService) {
        super(Config.class);
        this.jwtService = jwtService;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            String authorization = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (authorization == null || !authorization.startsWith("Bearer ")) {
                return this.onError(exchange, HttpStatus.UNAUTHORIZED);
            }
            String token = authorization.substring(7);
            try {
                Claims claims = jwtService.parseAccessToken(token);
                String role = claims.get("role", String.class);
                exchange = exchange.mutate()
                        .request(request -> request.headers(headers -> {
                            headers.remove("X-User-Id");
                            headers.remove("X-User-Roles");
                            headers.set("X-User-Id", claims.getSubject());
                            headers.set("X-User-Roles", role);
                        }))
                        .build();
            } catch (Exception e) {
                log.debug("Rejected invalid access token", e);
                return this.onError(exchange, HttpStatus.UNAUTHORIZED);
            }
            return chain.filter(exchange);
        });
    }

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }
    public static class Config {
    }
}
