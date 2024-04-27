package com.bohdan.abramovych.clear_solutions.core.service;


import com.bohdan.abramovych.clear_solutions.core.service.model.User;

import java.time.LocalDate;
import java.util.List;

/**
 * The CrudService interface defines basic CRUD (Create, Read, Update, Delete) operations for entities of type T.
 */
public interface UserService extends CrudService<User> {

    /**
     * Find all users with birthday in the given range.
     */
    List<User> findByBirthdayRange(LocalDate start, LocalDate end);
}

