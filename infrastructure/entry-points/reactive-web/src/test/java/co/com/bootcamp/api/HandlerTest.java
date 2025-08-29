package co.com.bootcamp.api;

import co.com.bootcamp.api.dto.UserRequest;
import co.com.bootcamp.api.exception.ApiException;
import co.com.bootcamp.api.mapper.UserMapper;
import co.com.bootcamp.model.user.User;
import co.com.bootcamp.usecase.user.UserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.Mockito.*;

class HandlerTest {

    private ServerRequest serverRequest;

    private UserUseCase userUseCase;
    private UserMapper userMapper;
    private Handler handler;

    private UserRequest userRequest;

    @BeforeEach
    void setUp() {
        serverRequest = mock(ServerRequest.class);
        userUseCase = mock(UserUseCase.class);
        userMapper = mock(UserMapper.class);

        handler = new Handler(userUseCase, userMapper);

        userRequest = UserRequest.builder()
                .names("Pedro")
                .surnames("Perez")
                .identification("0123456789")
                .email("pedroperez@mail.com")
                .roleId(3)
                .salary(BigDecimal.valueOf(1000.00))
                .birthdate(LocalDate.parse("2000-01-01"))
                .phone("0123456789")
                .build();
    }

    @Test
    void createUser_shouldReturnCreatedResponse() {
        User user = User.builder().build();

        when(serverRequest.bodyToMono(UserRequest.class)).thenReturn(Mono.just(userRequest));
        when(userMapper.toModel(userRequest)).thenReturn(user);
        when(userUseCase.createUser(user)).thenReturn(Mono.just(user));
        when(userMapper.toUserRequest(user)).thenReturn(userRequest);

        Mono<ServerResponse> responseMono = handler.createUser(serverRequest);
        StepVerifier.create(responseMono)
                .assertNext(response -> {
                    assert response.statusCode().equals(HttpStatus.CREATED);
                })
                .verifyComplete();

        verify(userUseCase).createUser(user);
        verify(userMapper).toModel(userRequest);
        verify(userMapper).toUserRequest(user);
    }

    @Test
    void createUser_shouldReturnBadRequestOnBodyError() {
        when(serverRequest.bodyToMono(UserRequest.class))
                .thenReturn(Mono.error(new RuntimeException("Invalid body")));

        Mono<ServerResponse> responseMono = handler.createUser(serverRequest);
        StepVerifier.create(responseMono)
                .expectErrorSatisfies(error -> {
                    assert error instanceof ApiException;
                    ApiException apiEx = (ApiException) error;
                    assert apiEx.getStatus().equals(HttpStatus.BAD_REQUEST);
                })
                .verify();
    }

    @Test
    void createUser_shouldReturnConflictOnUseCaseError() {
        User user = User.builder().build();

        when(serverRequest.bodyToMono(UserRequest.class)).thenReturn(Mono.just(userRequest));
        when(userMapper.toModel(userRequest)).thenReturn(user);
        when(userUseCase.createUser(user)).thenReturn(Mono.error(new RuntimeException("Duplicate")));

        Mono<ServerResponse> responseMono = handler.createUser(serverRequest);

        StepVerifier.create(responseMono)
                .expectErrorSatisfies(error -> {
                    assert error instanceof ApiException;
                    ApiException apiEx = (ApiException) error;
                    assert apiEx.getStatus().equals(HttpStatus.CONFLICT);
                })
                .verify();
    }
}