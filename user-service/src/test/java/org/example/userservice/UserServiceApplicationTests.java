package org.example.userservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "spring.cloud.config.enabled=false",
        "eureka.client.enabled=false",
        "spring.datasource.url=jdbc:h2:mem:user-service;MODE=MySQL;DB_CLOSE_DELAY=-1",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "jwt.secret=dGVzdC1qd3Qtc2VjcmV0LXRlc3Qtand0LXNlY3JldC10ZXN0LWp3dC1zZWNyZXQ="
})
class UserServiceApplicationTests {

    @Test
    void contextLoads() {
    }

}
