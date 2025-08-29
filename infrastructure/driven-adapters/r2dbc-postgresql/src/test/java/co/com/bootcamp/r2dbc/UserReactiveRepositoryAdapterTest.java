package co.com.bootcamp.r2dbc;

import co.com.bootcamp.model.user.User;
import co.com.bootcamp.r2dbc.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserReactiveRepositoryAdapterTest {

    @InjectMocks
    UserRepositoryAdapter repositoryAdapter;

    @Mock
    UserReactiveRepository repository;

    @Mock
    ObjectMapper mapper;

    @Test
    void test_whenSaveUser_thenSuccessful() {
        User user = User.builder().build();
        UserEntity userEntity = UserEntity.builder().build();

        when(mapper.map(user, UserEntity.class)).thenReturn(userEntity);
        when(repository.save(userEntity)).thenReturn(Mono.just(userEntity));
        when(mapper.map(userEntity, User.class)).thenReturn(user);

        Mono<User> result = repositoryAdapter.save(user);

        StepVerifier.create(result)
                .expectNext(user)
                .verifyComplete();
    }

    @Test
    void test_whenUserExistsByEmail_thenReturnTrue() {
        String email = "test@example.com";
        when(repository.existsByEmail(email)).thenReturn(Mono.just(true));

        StepVerifier.create(repositoryAdapter.userExistsByEmail(email))
                .expectNext(true)
                .verifyComplete();
    }

    @Test
    void test_createUser_appliesTransactionAndSavesUser() {
        User user = User.builder().build();
        UserEntity userEntity = UserEntity.builder().build();

        when(mapper.map(user, UserEntity.class)).thenReturn(userEntity);
        when(repository.save(userEntity)).thenReturn(Mono.just(userEntity));
        when(mapper.map(userEntity, User.class)).thenReturn(user);

        // Mock transactional operator to just return the Mono as is
        TransactionalOperator transactionalOperator = Mockito.mock(TransactionalOperator.class);
        //noinspection unchecked
        when(transactionalOperator.transactional(Mockito.any(Mono.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        UserRepositoryAdapter adapter = new UserRepositoryAdapter(repository, mapper, transactionalOperator);

        StepVerifier.create(adapter.createUser(user))
                .expectNext(user)
                .verifyComplete();
    }

    @Test
    void test_whenUserDoesNotExistByEmail_thenReturnFalse() {
        String email = "notfound@example.com";
        when(repository.existsByEmail(email)).thenReturn(Mono.just(false));

        StepVerifier.create(repositoryAdapter.userExistsByEmail(email))
                .expectNext(false)
                .verifyComplete();
    }

    @Test
    void test_whenSaveUser_thenRepositoryThrowsError() {
        User user = User.builder().build();
        UserEntity userEntity = UserEntity.builder().build();

        when(mapper.map(user, UserEntity.class)).thenReturn(userEntity);
        when(repository.save(userEntity)).thenReturn(Mono.error(new RuntimeException("DB error")));

        Mono<User> result = repositoryAdapter.save(user);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("DB error"))
                .verify();
    }
}