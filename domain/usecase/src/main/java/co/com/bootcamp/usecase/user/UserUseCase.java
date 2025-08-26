package co.com.bootcamp.usecase.user;

import co.com.bootcamp.model.user.User;
import co.com.bootcamp.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserUseCase {

    private final UserRepository userRepository;

    public Mono<User> getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }
}
