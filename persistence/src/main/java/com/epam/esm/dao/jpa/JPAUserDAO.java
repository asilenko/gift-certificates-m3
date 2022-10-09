package com.epam.esm.dao.jpa;

import com.epam.esm.dao.AbstractCrdDao;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.domain.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Holds logic to access data for User Entity.
 *
 * @see UserDAO
 * @see User
 */
@Repository
@Transactional
public class JPAUserDAO extends AbstractCrdDao<User> implements UserDAO {
    public JPAUserDAO() {
        setClazz(User.class);
    }
}
