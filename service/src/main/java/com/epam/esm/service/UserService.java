package com.epam.esm.service;

import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.UserBusinessModel;
import com.epam.esm.pagination.Page;
import org.springframework.stereotype.Service;

/**
 * Provides business operations for user.
 *
 * @see com.epam.esm.domain.User
 */
@Service
public interface UserService {

    /**
     * Finds user by specified id.
     *
     * @param id
     * @return user
     * @throws ResourceNotFoundException
     */
    UserBusinessModel find(Long id) throws ResourceNotFoundException;

    /**
     * Finds all existing users.
     *
     * @return List of users.
     */
    Page<UserBusinessModel> findAll(Integer pageNumber, Integer pageSize);
}
