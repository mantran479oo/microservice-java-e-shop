package org.example.commonsservice;

import org.example.commonsservice.config.JwtAutoConfiguration;
import org.example.commonsservice.security.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

class CommonsServiceApplicationTests {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(JwtAutoConfiguration.class));

    @Test
    void jwtAutoConfigurationCanBeDisabled() {
        contextRunner
                .withPropertyValues("jwt.enabled=false")
                .run(context -> assertThat(context).doesNotHaveBean(JwtService.class));
    }
}
