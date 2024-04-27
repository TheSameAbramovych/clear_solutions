package com.bohdan.abramovych.clear_solutions.core.repository;

import java.util.Optional;

/**
 * The UserRepository interface defines methods for managing user data.
 */
public interface CrudRepository<R, M> {

    /**
     * Inserts or updates an entity in the repository.
     */
    R upsert(M user);

    /**
     * Retrieves an entity by their unique identifier.
     */
    Optional<R> getByUserId(String id);

    /**
     * Removes an entity from the repository by their unique identifier.
     */
    Optional<R> remove(String id);
}

