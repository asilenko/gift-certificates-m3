package com.epam.esm.dao.jpa;

import com.epam.esm.dao.AbstractCrdDao;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.domain.Tag;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Holds logic to access data for Tag Entity.
 *
 * @see TagDAO
 * @see Tag
 */
@Repository
@Transactional
public class JPATagDAO extends AbstractCrdDao<Tag> implements TagDAO {

    private static final String FIND_BY_NAME = "FROM Tag t WHERE t.name = ?1";
    private static final String MOST_WIDELY_USED =
            "SELECT Tags.id, Tags.name " +
                    "FROM Tags " +
                    "INNER JOIN GiftCertificatesTags " +
                    "   ON Tags.id = GiftCertificatesTags.tag_id " +
                    "INNER JOIN Orders " +
                    "   ON GiftCertificatesTags.gift_certificate_id = Orders.certificate_id " +
                    "INNER JOIN Users " +
                    "   ON Users.id = Orders.user_id " +
                    "WHERE Users.id= " +
                    "   (SELECT user_id " +
                    "    FROM Orders " +
                    "    GROUP BY user_id " +
                    "    ORDER BY SUM(cost) DESC " +
                    "    LIMIT 1) " +
                    "GROUP BY Tags.id " +
                    "ORDER BY count(Tags.id) DESC " +
                    "LIMIT 1";

    public JPATagDAO() {
        setClazz(Tag.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Tag> findByName(String name) {
        var potentialTags = entityManager.createQuery(FIND_BY_NAME).setParameter(1, name).getResultList();
        return potentialTags.isEmpty() ? Optional.empty() : Optional.of((Tag) potentialTags.get(0));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Tag mostWidelyUsed() {
        return (Tag) entityManager.createNativeQuery(MOST_WIDELY_USED, Tag.class).getSingleResult();
    }
}
