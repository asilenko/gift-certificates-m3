package com.epam.esm.dao.jpa;

import com.epam.esm.dao.AbstractCrdDao;
import com.epam.esm.dao.OrderDAO;
import com.epam.esm.domain.Order;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Holds logic to access data for Order Entity.
 *
 * @see OrderDAO
 * @see Order
 */
@Repository
@Transactional
public class JPAOrderDAO extends AbstractCrdDao<Order> implements OrderDAO {
    public JPAOrderDAO() {
        setClazz(Order.class);
    }
}
