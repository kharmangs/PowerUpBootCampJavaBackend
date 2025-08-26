package co.com.bootcamp.r2dbc;

import co.com.bootcamp.r2dbc.entity.UserEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserReactiveRepository extends ReactiveCrudRepository<UserEntity, String>, ReactiveQueryByExampleExecutor<UserEntity> {

    @Query("SELECT u.*, r.description AS role " +
            "FROM users u " +
            "JOIN roles r ON u.role_id = r.id " +
            "WHERE u.email = :email")
    Mono<UserEntity> findUserByEmail(String email);
}
