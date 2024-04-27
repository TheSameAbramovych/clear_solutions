package com.bohdan.abramovych.clear_solutions.infra.service.infra.controller;

import com.bohdan.abramovych.clear_solutions.configuration.WebConfig;
import com.bohdan.abramovych.clear_solutions.core.exception.NotFoundException;
import com.bohdan.abramovych.clear_solutions.core.service.UserService;
import com.bohdan.abramovych.clear_solutions.core.service.model.User;
import com.bohdan.abramovych.clear_solutions.infra.RestResponseEntityExceptionHandler;
import com.bohdan.abramovych.clear_solutions.infra.controller.UserController;
import com.bohdan.abramovych.clear_solutions.infra.controller.mapper.UserViewMapper;
import com.bohdan.abramovych.clear_solutions.infra.controller.mapper.UserViewMapperImpl;
import com.bohdan.abramovych.clear_solutions.infra.controller.model.UserView;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import static com.bohdan.abramovych.clear_solutions.infra.service.TestDataProvider.createUser;
import static com.bohdan.abramovych.clear_solutions.infra.service.TestDataProvider.createUserView;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@ContextConfiguration(classes = {
    RestResponseEntityExceptionHandler.class,
    UserController.class,
    UserViewMapperImpl.class
})
@Import(WebConfig.class)
class UserControllerTest {

    public static final DateTimeFormatter DATE_TIME_FORMATTER =
        DateTimeFormatter.ofPattern("[d][-][M][-][yyyy]");


    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserViewMapper userViewMapper;
    @Autowired
    ObjectMapper objectMapper;


    @MockBean
    UserService userService;

    @SneakyThrows
    @Test
    @DisplayName("Successfully get user by id")
    void get_ok() {
        User user = createUser(true);

        when(userService.get(user.getId()))
            .thenReturn(user);

        MockHttpServletRequestBuilder requestBuilder = get("/users/{userId}", user.getId());

        assertValidUserResponse(mockMvc.perform(requestBuilder), user);
    }

    @SneakyThrows
    @Test
    @DisplayName("Successfully find users by birthday range")
    void findByBirthdayRange_ok() {
        User user = createUser(true);

        LocalDate end = LocalDate.now();
        LocalDate start = end.minusYears(1);
        when(userService.findByBirthdayRange(start, end))
            .thenReturn(List.of(user));

        mockMvc.perform(get("/users/{from}/{to}",
                start.format(DATE_TIME_FORMATTER), end.format(DATE_TIME_FORMATTER)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.[0].id").value(user.getId()));

    }

    @SneakyThrows
    @Test
    @DisplayName("Failed get user by id, 404")
    void get_notFound_fail() {

        when(userService.get(any()))
            .thenThrow(NotFoundException.class);

        String notFoundUserId = UUID.randomUUID().toString();
        mockMvc.perform(get("/users/{userId}", notFoundUserId))
            .andExpect(status().isNotFound());
    }

    @SneakyThrows
    @Test
    @DisplayName("Successfully create user")
    void create_ok() {
        UserView userView = createUserView();

        User user = userViewMapper.toUser(userView);
        when(userService.store(refEq(user)))
            .thenReturn(user);

        MockHttpServletRequestBuilder requestBuilder = post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userView));

        assertValidUserResponse(mockMvc.perform(requestBuilder), user);
    }

    @SneakyThrows
    @Test
    @DisplayName("Successfully delete user")
    void delete_ok() {
        User user = createUser(false);

        when(userService.remove(user.getId()))
            .thenReturn(user);

        MockHttpServletRequestBuilder requestBuilder = delete("/users/{userId}", user.getId());

        assertValidUserResponse(mockMvc.perform(requestBuilder), user);
    }

    private void assertValidUserResponse(ResultActions perform, User user) throws Exception {
        perform
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(user.getId()))
            .andExpect(jsonPath("$.email").value(user.getEmail()))
            .andExpect(jsonPath("$.address.postCode").value(user.getAddress().getPostCode()))
            .andReturn();
    }

    // TODO add other test for controller
}
