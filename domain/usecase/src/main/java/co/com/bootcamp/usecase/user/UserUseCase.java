package co.com.bootcamp.usecase.user;

import co.com.bootcamp.model.user.User;
import co.com.bootcamp.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserUseCase {

    private final UserRepository userRepository;

    public Mono<User> createUser(User user) {
        return userExistsByEmail(user.getEmail())
                .flatMap(exists -> {
                    if (Boolean.TRUE.equals(exists)) {
                        return Mono.error(new IllegalArgumentException("User with email " + user.getEmail() + " already exists"));
                    }
                    return userRepository.createUser(user);
                });
    }

    private Mono<Boolean> userExistsByEmail(String email) {
        return userRepository.userExistsByEmail(email);
    }
}
