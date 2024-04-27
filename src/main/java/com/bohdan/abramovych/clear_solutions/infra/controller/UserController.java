package com.clear.solutions.task.infra.controller;

import com.clear.solutions.task.core.service.UserService;
import com.clear.solutions.task.core.service.model.User;
import com.clear.solutions.task.infra.controller.mapper.UserViewMapper;
import com.clear.solutions.task.infra.controller.model.UserView;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;
    UserViewMapper userViewMapper;

    @GetMapping("/users/")
    public List<UserView> users() {
        //TODO implement users.
        throw new NotImplementedException("users is not yet implemented");
    }

    @PostMapping("/users/")
    public UserView createUser(UserView userView) {
        log.debug("Creating user by params {}...", userView);

        User user = userService.store(userViewMapper.toUser(userView));

        log.info("Created user {} by params {}.", user.getId(), userView);
        return userViewMapper.toUserView(user);
    }

    @GetMapping("/users/{userId}")
    public UserView user(@PathVariable String userId) {
        log.debug("Getting user by id {}...", userId);

        User user = userService.get(userId);

        log.info("Got {} user by id {}.", user, userId);
        return userViewMapper.toUserView(user);
    }


}
