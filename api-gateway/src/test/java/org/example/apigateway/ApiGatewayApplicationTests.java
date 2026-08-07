package org.example.apigateway;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import reactor.netty.DisposableServer;
import reactor.netty.http.server.HttpServer;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT, properties = {
        "spring.cloud.config.enabled=false",
        "eureka.client.enabled=false",
        "jwt.secret=dGVzdC1qd3Qtc2VjcmV0LXRlc3Qtand0LXNlY3JldC10ZXN0LWp3dC1zZWNyZXQ="
})
@AutoConfigureWebTestClient
class ApiGatewayApplicationTests {

    private static final DisposableServer DOWNSTREAM = HttpServer.create()
            .host("127.0.0.1")
            .port(0)
            .handle((request, response) -> response.sendString(Mono.just("pong")))
            .bindNow();

    private final WebTestClient webTestClient;

    @Autowired
    ApiGatewayApplicationTests(WebTestClient webTestClient) {
        this.webTestClient = webTestClient;
    }

    @DynamicPropertySource
    static void gatewayRoute(DynamicPropertyRegistry registry) {
        registry.add("spring.cloud.gateway.routes[0].id", () -> "runtime-compatibility");
        registry.add("spring.cloud.gateway.routes[0].uri",
                () -> "http://127.0.0.1:" + DOWNSTREAM.port());
        registry.add("spring.cloud.gateway.routes[0].predicates[0]",
                () -> "Path=/runtime-compatibility");
    }

    @AfterAll
    static void stopDownstream() {
        DOWNSTREAM.disposeNow();
    }

    @Test
    void routesRequestWithoutBinaryCompatibilityErrors() {
        webTestClient.get()
                .uri("/runtime-compatibility")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("pong");
    }

}
