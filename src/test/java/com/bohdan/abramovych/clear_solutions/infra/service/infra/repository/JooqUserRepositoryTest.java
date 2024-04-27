package com.bohdan.abramovych.clear_solutions.infra.service.infra.repository;


import com.bohdan.abramovych.clear_solutions.core.exception.NotFoundException;
import com.bohdan.abramovych.clear_solutions.core.repository.mapper.UsersRecordMapperImpl;
import com.bohdan.abramovych.clear_solutions.core.service.model.User;
import com.bohdan.abramovych.clear_solutions.infra.repository.JooqUserRepository;
import com.bohdan.abramovych.clear_solutions.persistence.tables.records.UsersRecord;
import org.assertj.core.api.AbstractObjectAssert;
import org.jooq.DSLContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static com.bohdan.abramovych.clear_solutions.persistence.tables.Users.USERS;
import static com.bohdan.abramovych.clear_solutions.infra.service.TestDataProvider.createUser;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@JooqTest
@ContextConfiguration(classes = {
    JooqUserRepository.class,
    UsersRecordMapperImpl.class
})
class JooqUserRepositoryTest {

    @Autowired
    DSLContext context;
    @Autowired
    JooqUserRepository userRepository;

    @Test
    public void upsert() {
        User user = createUser(false);

        userRepository.upsert(user);

        UsersRecord usersRecord = context.selectFrom(USERS)
            .where(USERS.ID.eq(user.getId()))
            .fetchAny();

        assertUser(assertThat(usersRecord), user);
    }

    @Test
    public void getByUserId() {
        User user = createUser(false);

        userRepository.upsert(user);
        Optional<UsersRecord> byUserId = userRepository.getByUserId(user.getId());

        assertUser(assertThat(byUserId).isPresent().get(), user);
    }

    @Test
    public void findByBirthdayRange() {
        User user = createUser(false);

        userRepository.upsert(user);
        LocalDate end = LocalDate.now();
        Stream<UsersRecord> byBirthdayRange = userRepository.findByBirthdayRange(end.minusYears(1), end);

        assertUser(assertThat(byBirthdayRange).hasSize(1).first(), user);
    }

    @Test
    public void remove() {
        User user = createUser(false);

        userRepository.upsert(user);
        Optional<UsersRecord> byUserId = userRepository.remove(user.getId());

        Long count = context.selectCount()
            .from(USERS)
            .where(USERS.ID.eq(user.getId()))
            .fetchOneInto(Long.class);
        assertEquals(0L, count);
        assertUser(assertThat(byUserId).isPresent().get(), user);
    }

    @Test
    public void remove_notFound() {
        String notFoundUserId = UUID.randomUUID().toString();
        assertThrows(NotFoundException.class, () -> userRepository.remove(notFoundUserId));
    }

    private static void assertUser(AbstractObjectAssert<?, UsersRecord> usersRecordAbstractComparableAssert, User user) {
        usersRecordAbstractComparableAssert
            .returns(user.getId(), UsersRecord::getId)
            .returns(user.getEmail(), UsersRecord::getEmail)
            .returns(user.getBirthday(), UsersRecord::getBirthday)
            .returns(user.getFirstName(), UsersRecord::getFirstName)
            .returns(user.getSecondName(), UsersRecord::getSecondName);
    }

}
