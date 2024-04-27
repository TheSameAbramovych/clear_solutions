package com.bohdan.abramovych.clear_solutions.infra.controller.mapper;

import com.bohdan.abramovych.clear_solutions.core.service.model.User;
import com.bohdan.abramovych.clear_solutions.infra.controller.model.UserView;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import java.util.List;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface UserViewMapper {

    List<UserView> toUserViews(List<User> user);

    UserView toUserView(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "address.id", ignore = true)
    @Mapping(target = "address.userId", ignore = true)
    User toUser(UserView userService);
}
