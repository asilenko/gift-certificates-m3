package com.epam.esm.dao.jpa;

import com.epam.esm.dao.AbstractCrdDao;
import com.epam.esm.dao.OrderDAO;
import com.epam.esm.domain.Order;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Holds logic to access data for Order Entity.
 *
 * @see OrderDAO
 * @see Order
 */
@Repository
@Transactional
public class JPAOrderDAO extends AbstractCrdDao<Order> implements OrderDAO {

    private static final String SELECT_COUNT_BY_USER_ID = "SELECT count(*) FROM Order order WHERE order.userID=:userid";

    public JPAOrderDAO() {
        setClazz(Order.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTotal(Long userId) {
        TypedQuery<Long> query = entityManager.createQuery(SELECT_COUNT_BY_USER_ID, Long.class);
        query.setParameter("userid", userId);
        return  query.getSingleResult().intValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Order> findByUser(Long userId, Integer pageNumber, Integer pageSize) {
        CriteriaQuery<Order> criteriaQuery = getOrderCriteriaQuery(userId);
        return entityManager.createQuery(criteriaQuery)
                .setFirstResult(pageSize * (pageNumber - 1))
                .setMaxResults(pageSize)
                .getResultList();
    }

    private CriteriaQuery<Order> getOrderCriteriaQuery(Long userId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> order = criteriaQuery.from(Order.class);
        criteriaQuery.select(order).where(criteriaBuilder.equal(order.get("userID"), userId));
        return criteriaQuery;
    }
}
