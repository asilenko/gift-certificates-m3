package com.epam.esm.service;

import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.OrderBusinessModel;
import com.epam.esm.pagination.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Provides business operations for order.
 *
 * @see com.epam.esm.domain.Order
 */
@Service
public interface OrderService {

    /**
     * Finds an order by specified id.
     *
     * @return an order
     * @throws ResourceNotFoundException
     */
    OrderBusinessModel find(Long id) throws ResourceNotFoundException;

    /**
     * Place a new order for specified user.
     *
     * @param userId
     * @param certificateId
     * @return created model
     */
    OrderBusinessModel placeOrder(Long userId, Long certificateId) throws ResourceNotFoundException, InvalidFieldValueException;

    /**
     * Deletes order by specified id.
     *
     * @param id
     */
    void delete(Long id) throws ResourceNotFoundException;

    /**
     * Finds all existing orders.
     *
     * @return List of orders.
     */
    Page<OrderBusinessModel> findAll(Integer pageNumber, Integer pageSize);

    /**
     * Finds all orders placed by specified user.
     *
     * @param userId
     * @return List of orders.
     */
    Page<OrderBusinessModel> findByUser(Long userId, Integer pageNumber, Integer pageSize);
}
