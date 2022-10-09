package com.epam.esm.dao;

import com.epam.esm.domain.Order;
import com.epam.esm.domain.Tag;

import java.util.List;

/**
 * Provides CRD methods for Order.
 *
 * @see Tag
 */
public interface OrderDAO extends CrdDao <Order>{

    /**
     * Calculate total number of orders for specified user.
     */
    int getTotal(Long userId);

    /**
     * Finds all orders for specified user.
     *
     * @return List of GiftCertificates.
     */
    List<Order> findByUser(Long userId, Integer pageNumber, Integer pageSize);
}
