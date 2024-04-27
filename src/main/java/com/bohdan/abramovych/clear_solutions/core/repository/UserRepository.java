package com.bohdan.abramovych.clear_solutions.core.repository;

import com.bohdan.abramovych.clear_solutions.core.service.model.User;
import com.bohdan.abramovych.clear_solutions.persistence.tables.records.UsersRecord;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

/**
 * The UserRepository interface defines methods for managing user data.
 */
public interface UserRepository extends CrudRepository<UsersRecord, User> {
    Stream<UsersRecord> findByBirthdayRange(LocalDate start, LocalDate end);
}

