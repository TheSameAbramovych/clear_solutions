package com.bohdan.abramovych.clear_solutions.infra.repository;

import com.bohdan.abramovych.clear_solutions.core.repository.UserRepository;
import com.bohdan.abramovych.clear_solutions.core.repository.mapper.UsersRecordMapper;
import com.bohdan.abramovych.clear_solutions.core.service.model.User;
import com.bohdan.abramovych.clear_solutions.persistence.tables.records.UsersRecord;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.bohdan.abramovych.clear_solutions.persistence.tables.Users.USERS;

@Slf4j
@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserInMemoryRepository implements UserRepository {

    UsersRecordMapper userRecordMapper;
    DSLContext context;

    public UsersRecord upsert(User user) {
        log.debug("Upsetting user {}...", user);

        UsersRecord usersRecord = userRecordMapper.toUsersRecord(user);
        usersRecord = context.insertInto(USERS)
                .set(usersRecord)
                .onDuplicateKeyUpdate()
                .set(usersRecord)
                .returning(USERS)
                .fetchOne();
        log.debug("User {} was inserted by id {}.", user, user.getId());
        return usersRecord;
    }

    public Optional<UsersRecord> getById(String id) {
        log.debug("Getting user by id {}...", id);

        Optional<UsersRecord> user = context.selectFrom(USERS)
                .where(USERS.ID.eq(id))
                .fetchOptional();

        log.debug("User {} was found by id {}.", user, id);
        return user;
    }

    public Optional<UsersRecord> remove(String id) {
        log.debug("Removing user by id {}...", id);

        Optional<UsersRecord> user = context.deleteFrom(USERS)
                .where(USERS.ID.eq(id))
                .returning()
                .fetchOptional();

        log.debug("User {} was removed by id {}.", user, id);
        return user;
    }
}
