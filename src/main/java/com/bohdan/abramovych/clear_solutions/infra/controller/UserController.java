package com.bohdan.abramovych.clear_solutions.infra.controller;


import com.bohdan.abramovych.clear_solutions.core.service.UserService;
import com.bohdan.abramovych.clear_solutions.core.service.model.User;
import com.bohdan.abramovych.clear_solutions.infra.controller.mapper.UserViewMapper;
import com.bohdan.abramovych.clear_solutions.infra.controller.model.UserView;
import jakarta.websocket.server.PathParam;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;
    UserViewMapper userViewMapper;

    @GetMapping("/{from}/{to}")
    public List<UserView> users(@PathVariable("from") LocalDate from, @PathVariable("to") LocalDate to) {
        log.debug("Getting users by birthday range ({}-{})", from, to);
        return userViewMapper.toUserViews(userService.findByBirthdayRange(from, to));
    }

    @PostMapping
    public UserView createUser(@RequestBody UserView userView) {
        log.debug("Creating user by params {}...", userView);

        User user = userService.store(userViewMapper.toUser(userView));

        log.info("Created user {} by params {}.", user.getId(), userView);
        return userViewMapper.toUserView(user);
    }

    @DeleteMapping("/{userId}")
    public UserView deleteUser(@PathVariable String userId) {

        User user = userService.remove(userId);
        UserView userView = userViewMapper.toUserView(user);
        log.info("Delete user {}.", userView);
        return userView;
    }

    @GetMapping("/{userId}")
    public UserView user(@PathVariable String userId) {
        log.debug("Getting user by id {}...", userId);

        User user = userService.get(userId);

        log.info("Got {} user by id {}.", user, userId);
        return userViewMapper.toUserView(user);
    }
}
