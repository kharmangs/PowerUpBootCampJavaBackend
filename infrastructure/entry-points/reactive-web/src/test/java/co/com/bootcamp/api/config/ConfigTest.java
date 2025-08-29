package co.com.bootcamp.api.config;

import co.com.bootcamp.api.Handler;
import co.com.bootcamp.api.RouterRest;
import co.com.bootcamp.api.mapper.UserMapper;
import co.com.bootcamp.usecase.user.UserUseCase;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.ServerResponse;

@WebFluxTest
@Import({CorsConfig.class, SecurityHeadersConfig.class})
class ConfigTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void corsConfigurationShouldAllowOrigins() {
        Handler handler = Mockito.mock(Handler.class);
        Mockito.when(handler.createUser(Mockito.any()))
                .thenReturn(ServerResponse.ok().bodyValue("user created"));

        webTestClient.post()
                .uri("/api/v1/users")
                .exchange()
                .expectStatus().is4xxClientError()
                .expectHeader().valueEquals("Content-Security-Policy",
                        "default-src 'self'; frame-ancestors 'self'; form-action 'self'")
                .expectHeader().valueEquals("Strict-Transport-Security", "max-age=31536000;")
                .expectHeader().valueEquals("X-Content-Type-Options", "nosniff")
                .expectHeader().valueEquals("Server", "")
                .expectHeader().valueEquals("Cache-Control", "no-store")
                .expectHeader().valueEquals("Pragma", "no-cache")
                .expectHeader().valueEquals("Referrer-Policy", "strict-origin-when-cross-origin");
    }

    @Configuration
    static class TestConfig {
        @Bean
        public UserUseCase userUseCase() {
            return Mockito.mock(UserUseCase.class);
        }

        @Bean
        public UserMapper userMapper() {
            return Mockito.mock(UserMapper.class);
        }

        @Bean
        public RouterRest routerRest() {
            return new RouterRest(new UserPath() {{
                setUsers("/api/v1/users");
            }});
        }
    }

}