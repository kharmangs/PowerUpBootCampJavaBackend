package co.com.bootcamp.model.user.gateways;

import co.com.bootcamp.model.user.User;
import reactor.core.publisher.Mono;

public interface UserRepository {
    
    Mono<User> getUserByEmail(String email);
}
