package com.clear.solutions.task.core.repository;

import com.clear.solutions.task.core.repository.model.UserRecord;
import com.clear.solutions.task.core.service.model.User;

import java.util.Optional;

/**
 * The UserRepository interface defines methods for managing user data.
 */
public interface UserRepository extends CrudRepository<UserRecord, User> {

}

