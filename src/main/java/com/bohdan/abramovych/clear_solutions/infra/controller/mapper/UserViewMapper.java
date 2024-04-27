package com.clear.solutions.task.infra.controller.mapper;

import com.clear.solutions.task.core.service.model.User;
import com.clear.solutions.task.infra.controller.model.UserView;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface UserViewMapper {

    List<UserView> toUserViews(List<User> user);

    UserView toUserView(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "address.id", ignore = true)
    @Mapping(target = "address.userId", ignore = true)
    User toUser(UserView userService);
}
