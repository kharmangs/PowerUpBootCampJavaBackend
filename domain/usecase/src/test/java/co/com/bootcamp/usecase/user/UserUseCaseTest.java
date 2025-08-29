package co.com.bootcamp.usecase.user;

import co.com.bootcamp.model.user.User;
import co.com.bootcamp.model.user.gateways.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class UserUseCaseTest {

    private static final String EMAIL = "test@example.com";

    private UserRepository userRepository;
    private UserUseCase userUseCase;

    private final User user = new User();

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        userUseCase = new UserUseCase(userRepository);

        user.setEmail(EMAIL);
    }

    @Test
    void createUser_shouldCreateUserWhenEmailDoesNotExist() {
        when(userRepository.userExistsByEmail(EMAIL)).thenReturn(Mono.just(false));
        when(userRepository.createUser(user)).thenReturn(Mono.just(user));

        StepVerifier.create(userUseCase.createUser(user))
                .expectNext(user)
                .verifyComplete();

        verify(userRepository).userExistsByEmail(EMAIL);
        verify(userRepository).createUser(user);
    }

    @Test
    void createUser_shouldThrowErrorWhenEmailAlreadyExists() {
        when(userRepository.userExistsByEmail(EMAIL)).thenReturn(Mono.just(true));

        StepVerifier.create(userUseCase.createUser(user))
                .expectErrorMatches(throwable -> throwable instanceof IllegalArgumentException &&
                        throwable.getMessage().equals("User with email test@example.com already exists"))
                .verify();

        verify(userRepository).userExistsByEmail(EMAIL);
        verify(userRepository, never()).createUser(any());
    }

    @Test
    void createUser_shouldPropagateErrorFromRepository() {
        when(userRepository.userExistsByEmail(EMAIL)).thenReturn(Mono.error(new RuntimeException("Database error")));

        StepVerifier.create(userUseCase.createUser(user))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Database error"))
                .verify();

        verify(userRepository).userExistsByEmail(EMAIL);
        verify(userRepository, never()).createUser(any());
    }
}
