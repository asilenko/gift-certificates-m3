package com.epam.esm.service;

import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.BusinessModel;

/**
 * CRD methods common for all entities.
 */
public interface CrdService <T extends BusinessModel> {

    /**
     * Finds a model by specified id.
     *
     * @param id
     * @return model
     * @throws ResourceNotFoundException
     */
    T find(Long id) throws ResourceNotFoundException;

    /**
     * Creates a new model.
     *
     * @param model
     * @return created model
     */
    T create(T model);

    /**
     * Deletes model by specified id.
     *
     * @param id
     */
    void delete(Long id) throws ResourceNotFoundException;
}
