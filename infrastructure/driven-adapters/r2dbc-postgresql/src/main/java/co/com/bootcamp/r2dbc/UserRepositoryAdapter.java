package co.com.bootcamp.r2dbc;

import co.com.bootcamp.model.user.User;
import co.com.bootcamp.model.user.gateways.UserRepository;
import co.com.bootcamp.r2dbc.entity.UserEntity;
import co.com.bootcamp.r2dbc.helper.ReactiveAdapterOperations;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Slf4j
@Repository
public class UserRepositoryAdapter extends ReactiveAdapterOperations<
        User,
        UserEntity,
        String,
        UserReactiveRepository
        > implements UserRepository {
    public UserRepositoryAdapter(UserReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, User.class));
    }

    @Override
    public Mono<User> getUserByEmail(String email) {
        return repository.findUserByEmail(email)
                .doOnNext(userEntity -> log.info("UserEntity: {}", userEntity))
                .map(entity -> mapper.map(entity, User.class));
    }
}
