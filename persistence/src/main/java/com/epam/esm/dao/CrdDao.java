package com.epam.esm.dao;

import com.epam.esm.exception.ResourceNotFoundException;

import java.util.Collection;
import java.util.Optional;

/**
 * Provides CRD methods for entities.
 */
public interface CrdDao<T> {

    /**
     * Finds Entity by specified id. Should return empty Optional if no such Entity exist.
     *
     * @param id
     * @return Optional of EntityModel.
     */
    Optional<T> findById(final Long id);

    /**
     * Finds all existing entities.
     *
     * @return List of EntityModels.
     */
    Collection<T> findAll(Integer pageNumber, Integer pageSize);

    /**
     * Adds new Entity to the database.
     *
     * @return EntityModel
     */
    T create(T entity);

    /**
     * Delete Entity specified by id.
     *
     * @param id
     */
    void delete(Long id) throws ResourceNotFoundException;
}
