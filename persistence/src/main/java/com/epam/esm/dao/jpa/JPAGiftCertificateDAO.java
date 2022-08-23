package com.epam.esm.dao.jpa;

import com.epam.esm.dao.AbstractCrdDao;
import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.exception.InvalidSortTypeException;
import com.epam.esm.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Holds logic to access and modify data for Gift Certificate Entity.
 *
 * @see GiftCertificateDAO
 * @see GiftCertificate
 */
@Repository
@Transactional
public class JPAGiftCertificateDAO extends AbstractCrdDao<GiftCertificate> implements GiftCertificateDAO {
    @Autowired
    private GiftCertificateQueryBuilder giftCertificateQueryBuilder;

    public JPAGiftCertificateDAO(){
        setClazz(GiftCertificate.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GiftCertificate> findAll() {
        return (List<GiftCertificate>) super.findAll();
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

}
