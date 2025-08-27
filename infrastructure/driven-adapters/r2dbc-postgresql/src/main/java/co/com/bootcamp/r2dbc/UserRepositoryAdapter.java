package co.com.bootcamp.r2dbc;

import co.com.bootcamp.model.user.User;
import co.com.bootcamp.model.user.gateways.UserRepository;
import co.com.bootcamp.r2dbc.entity.UserEntity;
import co.com.bootcamp.r2dbc.helper.ReactiveAdapterOperations;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

@Slf4j
@Repository
public class UserRepositoryAdapter extends ReactiveAdapterOperations<User, UserEntity, String, UserReactiveRepository> implements UserRepository {

    private final TransactionalOperator transactionalOperator;

    public UserRepositoryAdapter(UserReactiveRepository repository, ObjectMapper mapper, TransactionalOperator transactionalOperator) {
        super(repository, mapper, d -> mapper.map(d, User.class));
        this.transactionalOperator = transactionalOperator;
    }

    @Override
    public Mono<User> createUser(User user) {
        return super.save(user).as(transactionalOperator::transactional);
    }

    @Override
    public Mono<Boolean> userExistsByEmail(String email) {
        return repository.existsByEmail(email);
    }
}
