package com.bohdan.abramovych.clear_solutions.infra.service.infra.service;

import com.bohdan.abramovych.clear_solutions.core.exception.AgeCheckerException;
import com.bohdan.abramovych.clear_solutions.core.exception.NotFoundException;
import com.bohdan.abramovych.clear_solutions.core.repository.AddressRepository;
import com.bohdan.abramovych.clear_solutions.core.repository.UserRepository;
import com.bohdan.abramovych.clear_solutions.core.service.mapper.AddressMapperImpl;
import com.bohdan.abramovych.clear_solutions.core.service.mapper.UserMapper;
import com.bohdan.abramovych.clear_solutions.core.service.mapper.UserMapperImpl;
import com.bohdan.abramovych.clear_solutions.core.service.model.User;
import com.bohdan.abramovych.clear_solutions.infra.service.UserServiceImpl;
import com.bohdan.abramovych.clear_solutions.persistence.tables.records.AddressRecord;
import com.bohdan.abramovych.clear_solutions.persistence.tables.records.UsersRecord;
import com.bohdan.abramovych.clear_solutions.infra.service.TestDataProvider;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Stream;

import static com.bohdan.abramovych.clear_solutions.infra.service.TestDataProvider.*;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
    UserServiceImpl.class,
    UserMapperImpl.class,
    AddressMapperImpl.class
})
@FieldDefaults(level = AccessLevel.PRIVATE)
class UserServiceImplTest {

    @Autowired
    UserServiceImpl userService;
    @Autowired
    UserMapper userMapper;

    @MockBean
    UserRepository userRepository;
    @MockBean
    AddressRepository addressRepository;

    public Stream<Arguments> usersOperations() {
        return Stream.of(
            Arguments.of((Function<User, User>) userService::store, "store"),
            Arguments.of((Function<User, User>) userService::update, "update")
        );
    }

    @DisplayName("Successfully got user by id")
    @Test
    void get_ok() {
        User user = createUser(true);
        UsersRecord userRecord = createUserRecord();
        AddressRecord addressRecord = createAddressRecord();

        when(userRepository.getByUserId(user.getId()))
            .thenReturn(Optional.of(userRecord));
        when(addressRepository.getByUserId(user.getId()))
            .thenReturn(Optional.of(addressRecord));

        User actualUser = userService.get(user.getId());

        assertEquals(userMapper.toUser(userRecord, addressRecord), actualUser);
    }

    @ParameterizedTest(name = "Successfully {1} user")
    @MethodSource("usersOperations")
    void validUser_ok(Function<User, User> function, String operationName) {
        User user = createUser(true);
        UsersRecord userRecord = createUserRecord();
        AddressRecord addressRecord = createAddressRecord();

        when(userRepository.upsert(any()))
            .thenReturn(userRecord);
        when(addressRepository.upsert(any()))
            .thenReturn(addressRecord);

        User actualUser = function.apply(user);

        assertEquals(userMapper.toUser(userRecord, addressRecord), actualUser);
    }

    @ParameterizedTest(name = "Failed to {1} user, not adult")
    @MethodSource("usersOperations")
    void notAdultUser_fail(Function<User, User> function, String operationName) {
        User user = createUser(false);

        assertThrows(AgeCheckerException.class, () -> function.apply(user));

        verify(userRepository, never()).upsert(any());
        verify(addressRepository, never()).upsert(any());
    }

    @DisplayName("Throes exception if user not found")
    @Test
    void throwExceptionIfUserNotFound() {

        when(userRepository.getByUserId(any()))
            .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.get(TestDataProvider.generateRandomId()));
    }

    @DisplayName("Successfully find users by birthday range")
    @Test
    void findByBirthdayRange_ok() {
        UsersRecord userRecord = createUserRecord();
        AddressRecord addressRecord = createAddressRecord();

        LocalDate end = LocalDate.now();
        LocalDate start = end.minusYears(5);
        when(userRepository.findByBirthdayRange(start, end))
            .thenReturn(Stream.of(userRecord));
        when(addressRepository.getByUserId(userRecord.getId()))
            .thenReturn(Optional.of(addressRecord));

        List<User> byBirthdayRange = userService.findByBirthdayRange(start, end);

        assertThat(byBirthdayRange)
            .hasSize(1)
            .contains(userMapper.toUser(userRecord, addressRecord));
    }


    @DisplayName("Successfully remove user")
    @Test
    void remove_ok() {
        UsersRecord userRecord = createUserRecord();

        when(userRepository.remove(userRecord.getId()))
            .thenReturn(Optional.of(userRecord));

        User removedUser = userService.remove(userRecord.getId());

        assertEquals(userMapper.toUser(userRecord, new AddressRecord()), removedUser);
    }

    @DisplayName("Successfully remove not exist user")
    @Test
    void remove_notExist_ok() {

        when(userRepository.remove(any()))
            .thenReturn(Optional.empty());

        String notExistUserId = UUID.randomUUID().toString();
        assertNull(userService.remove(notExistUserId));
    }

}
