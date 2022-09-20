package com.epam.esm.service;

import com.epam.esm.model.OrderBusinessModel;
import com.epam.esm.pagination.Page;
import org.springframework.stereotype.Service;

/**
 * Provides business operations for order.
 *
 * @see com.epam.esm.domain.Order
 */
@Service
public interface OrderService extends CrdService<OrderBusinessModel>{

    /**
     * Finds all existing orders.
     *
     * @return List of orders.
     */
    Page<OrderBusinessModel> findAll(Integer pageNumber, Integer pageSize);
}
