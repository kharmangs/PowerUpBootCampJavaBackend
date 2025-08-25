package co.com.pragma.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class Handler {

    public Mono<ServerResponse> listenGETUseCase(ServerRequest serverRequest) {
        log.info("Received listenGETUseCase: {}", serverRequest.pathVariable("path"));
        return ServerResponse.ok().bodyValue("");
    }

    public Mono<ServerResponse> listenGETOtherUseCase(ServerRequest serverRequest) {
        log.info("Received listenGETOtherUseCase: {}", serverRequest.pathVariable("path"));
        return ServerResponse.ok().bodyValue("");
    }

    public Mono<ServerResponse> listenPOSTUseCase(ServerRequest serverRequest) {
        log.info("Received listenPOSTUseCase: {}", serverRequest.pathVariable("path"));

        return ServerResponse.ok().bodyValue("");
    }
}
