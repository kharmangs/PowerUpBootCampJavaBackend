package co.com.bootcamp.api;

import co.com.bootcamp.api.dto.UserRequest;
import co.com.bootcamp.api.exception.ApiException;
import co.com.bootcamp.api.mapper.UserMapper;
import co.com.bootcamp.usecase.user.UserUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Slf4j
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Component
@RequiredArgsConstructor
public class Handler {

    private final UserUseCase userUseCase;

    private final UserMapper userMapper;

    public Mono<ServerResponse> createUser(ServerRequest request) {
        return request.bodyToMono(UserRequest.class)
                .doOnNext(userRequest -> log.info(">>> CREATE USER. NEW CALL >>> {}", userRequest))
                .onErrorResume(e -> Mono.error(new ApiException(HttpStatus.BAD_REQUEST, e.getCause().getMessage())))
                .flatMap(userRequest -> userUseCase.createUser(userMapper.toModel(userRequest))
                        .onErrorResume(e -> Mono.error(new ApiException(HttpStatus.CONFLICT, e.getMessage())))
                        .flatMap(newUser -> ServerResponse.created(URI.create("/users/" + newUser.getId()))
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(userMapper.toUserRequest(newUser)))
                        .doOnNext(response -> log.info("<<< CREATE USER. SUCCESSFUL END <<<"))
                );
    }
}
