package com.epam.esm.dao.jdbc;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.exception.InvalidSortTypeException;
import com.epam.esm.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Holds logic to access and modify data for Gift Certificate Entity.
 *
 * @see GiftCertificateDAO
 * @see GiftCertificate
 */
@Repository
@Transactional
public class JPAGiftCertificateDAO implements GiftCertificateDAO {
    @Autowired
    private GiftCertificateQueryBuilder giftCertificateQueryBuilder;
    @Autowired
    EntityManager entityManager;
    private static final String GET_ALL = "FROM GiftCertificate";

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<GiftCertificate> findById(final long id) {
        return Optional.ofNullable(entityManager.find(GiftCertificate.class, id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GiftCertificate> findAll() {
        return entityManager.createQuery(GET_ALL, GiftCertificate.class).getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GiftCertificate> findAllMatchingPrams(CertificateSearchCriteria certificateSearchCriteria)
            throws InvalidSortTypeException {
        String searchQuery = giftCertificateQueryBuilder.generateSearchQuery(certificateSearchCriteria);
        return entityManager
                .createNativeQuery(searchQuery, GiftCertificate.class)
                .getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) throws ResourceNotFoundException {
        checkNotNull(giftCertificate, "Gift certificate entity is null!");
        var id = giftCertificate.getId();
        String updateQuery = giftCertificateQueryBuilder.generateUpdateQuery(id, giftCertificate);
        if (!updateQuery.isEmpty()) {
            entityManager.getTransaction().begin();
            if (giftCertificate.getTags().isEmpty()) {
                entityManager.createNativeQuery(updateQuery).executeUpdate();
            } else {
                entityManager.merge(giftCertificate);
            }
            entityManager.getTransaction().commit();
            entityManager.clear();
        }
        return findById(id).orElseThrow(() -> new ResourceNotFoundException("No resource with id " + id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GiftCertificate create(GiftCertificate giftCertificate) {
        giftCertificate.setId(null);
        entityManager.getTransaction().begin();
        entityManager.persist(giftCertificate);
        entityManager.getTransaction().commit();
        entityManager.clear();
        return giftCertificate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(long id) throws ResourceNotFoundException {
        Optional<GiftCertificate> giftCertificate = findById(id);
        if (giftCertificate.isEmpty()) {
            throw new ResourceNotFoundException("No tag related to id: " + id);
        } else {
            entityManager.getTransaction().begin();
            entityManager.remove(giftCertificate.get());
            entityManager.getTransaction().commit();
            entityManager.clear();
        }
    }
}
