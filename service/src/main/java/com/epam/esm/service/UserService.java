package com.epam.esm.service;

import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.OrderBusinessModel;
import com.epam.esm.model.UserBusinessModel;
import com.epam.esm.pagination.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Provides business operations for user.
 *
 * @see com.epam.esm.domain.User
 */
@Service
public interface UserService {

    UserBusinessModel find(Long id) throws ResourceNotFoundException;

    List<OrderBusinessModel> findOrders(Long userId);

    Page<UserBusinessModel> findAll(Integer pageNumber, Integer pageSize);
}
