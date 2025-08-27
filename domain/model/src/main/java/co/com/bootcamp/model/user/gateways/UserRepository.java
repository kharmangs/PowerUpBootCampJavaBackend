package co.com.bootcamp.model.user.gateways;

import co.com.bootcamp.model.user.User;
import reactor.core.publisher.Mono;

public interface UserRepository {

    Mono<User> createUser(User user);
    
    Mono<Boolean> userExistsByEmail(String email);
}
