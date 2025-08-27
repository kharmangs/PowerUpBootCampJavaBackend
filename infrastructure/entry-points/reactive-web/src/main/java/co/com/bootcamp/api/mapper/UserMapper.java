package co.com.bootcamp.api.mapper;

import co.com.bootcamp.api.dto.UserRequest;
import co.com.bootcamp.model.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toModel(UserRequest userRequest);

    UserRequest toUserRequest(User user);
}
