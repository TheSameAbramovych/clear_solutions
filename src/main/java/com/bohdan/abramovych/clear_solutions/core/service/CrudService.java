package com.clear.solutions.task.core.service;

/**
 * The CrudService interface defines basic CRUD (Create, Read, Update, Delete) operations for entities of type T.
 */
public interface CrudService<T> {

    /**
     * Stores a new entity in the system.
     */
    T store(T entity);

    /**
     * Updates an existing entity in the system.
     */
    T update(T entity);

    /**
     * Retrieves an entity by its unique identifier.
     */
    T get(String id);

    /**
     * Removes an entity from the system by its unique identifier.
     */
    T remove(String id);
}

