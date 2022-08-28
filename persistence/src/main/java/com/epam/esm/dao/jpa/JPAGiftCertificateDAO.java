package com.epam.esm.dao.jpa;

import com.epam.esm.dao.AbstractCrdDao;
import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.exception.InvalidSortTypeException;
import com.epam.esm.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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
    private GiftCertificateQueryBuilder queryBuilder;

    public JPAGiftCertificateDAO() {
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
    public List<GiftCertificate> findAllMatchingPrams(CertificateSearchCriteria searchCriteria)
            throws InvalidSortTypeException {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> criteriaQuery = queryBuilder.buildCriteriaQuery(criteriaBuilder, searchCriteria);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) throws ResourceNotFoundException {
        checkNotNull(giftCertificate, "Gift certificate entity is null!");
        var id = giftCertificate.getId();
        entityManager.getTransaction().begin();
        GiftCertificate certificateToUpdate = findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No resource with id " + id));
        if (giftCertificate.getName() != null) {
            certificateToUpdate.setName(giftCertificate.getName());
        }
        if ((giftCertificate.getDescription() != null)) {
            certificateToUpdate.setDescription(giftCertificate.getDescription());
        }
        if ((giftCertificate.getPrice() != null)) {
            certificateToUpdate.setPrice(giftCertificate.getPrice());
        }
        if (giftCertificate.getDuration() != null) {
            certificateToUpdate.setDuration(giftCertificate.getDuration());
        }
        if (giftCertificate.getTags() != null & !giftCertificate.getTags().isEmpty()) {
            certificateToUpdate.setTags(giftCertificate.getTags());
        }
        entityManager.getTransaction().commit();
        entityManager.clear();
        return certificateToUpdate;
    }
}
