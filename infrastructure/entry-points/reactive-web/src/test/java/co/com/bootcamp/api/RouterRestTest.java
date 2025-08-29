package co.com.bootcamp.api;

import co.com.bootcamp.api.config.CorsConfig;
import co.com.bootcamp.api.config.SecurityHeadersConfig;
import co.com.bootcamp.api.config.UserPath;
import co.com.bootcamp.api.mapper.UserMapper;
import co.com.bootcamp.usecase.user.UserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.ServerResponse;

@WebFluxTest(RouterRest.class)
@Import({Handler.class, RouterRestTest.TestConfig.class, CorsConfig.class, SecurityHeadersConfig.class})
class RouterRestTest {

    private static final String USER_RESOURCE_PATH = "/api/v1/users";

    private Handler handler;
    private WebTestClient client;

    @BeforeEach
    void setUp() {
        handler = Mockito.mock(Handler.class);
        client = WebTestClient.bindToRouterFunction(
                new RouterRest(new UserPath() {{
                    setUsers(USER_RESOURCE_PATH);
                }}).routerFunction(handler)
        ).build();
    }

    @Test
    void testRouteCreateUser_thenOk() {
        Mockito.when(handler.createUser(Mockito.any()))
                .thenReturn(ServerResponse.ok().bodyValue("user created"));

        client.post()
                .uri(RouterRestTest.USER_RESOURCE_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue("{}")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("user created");
    }

    @Test
    void testRouteCreateUser_thenForbidden() {
        Mockito.when(handler.createUser(Mockito.any()))
                .thenReturn(ServerResponse.status(403).bodyValue("Forbidden"));

        client.post()
                .uri(RouterRestTest.USER_RESOURCE_PATH)
                .header("Origin", "https://not-allowed.com")
                .exchange()
                .expectStatus().isForbidden();
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
    }
}
