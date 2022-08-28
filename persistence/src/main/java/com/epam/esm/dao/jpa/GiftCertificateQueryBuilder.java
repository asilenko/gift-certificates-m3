package com.epam.esm.dao.jpa;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Tag;
import com.epam.esm.exception.InvalidSortTypeException;
import com.google.common.base.Enums;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.LinkedList;
import java.util.List;

/**
 * Builder for queries for GiftCertificate.
 *
 * @see JPAGiftCertificateDAO
 */
@Component
public class GiftCertificateQueryBuilder {

    private static final String NAME = "name";
    private static final String DATE = "createDate";
    private static final String DESCRIPTION = "description";


    enum Sort {ASC, DESC}


    CriteriaQuery<GiftCertificate> buildCriteriaQuery(CriteriaBuilder criteriaBuilder,
                                                      CertificateSearchCriteria searchCriteria)
            throws InvalidSortTypeException {

        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> certificate = criteriaQuery.from(GiftCertificate.class);
        criteriaQuery.select(certificate);

        Predicate[] predicates = collectPredicates(criteriaBuilder, searchCriteria, certificate);
        Order[] order = prepareSorting(criteriaBuilder, searchCriteria, certificate);

        Predicate predicate = criteriaBuilder.and(predicates);
        criteriaQuery.where(predicate);
        criteriaQuery.orderBy(order);
        return criteriaQuery;
    }

    private Predicate[] collectPredicates(CriteriaBuilder criteriaBuilder, CertificateSearchCriteria searchCriteria,
                                          Root<GiftCertificate> certificate) {
        List<Predicate> tagRestrictions = collectTagRestrictions(criteriaBuilder, searchCriteria, certificate);
        List<Predicate> likeRestrictions = collectLikeRestrictions(criteriaBuilder, searchCriteria, certificate);

        List<Predicate> finalPredicate = new LinkedList<>();
        finalPredicate.addAll(tagRestrictions);
        finalPredicate.addAll(likeRestrictions);
        return finalPredicate.toArray(new Predicate[0]);
    }

    private List<Predicate> collectTagRestrictions(CriteriaBuilder criteriaBuilder,
                                                   CertificateSearchCriteria certificateSearchCriteria,
                                                   Root<GiftCertificate> certificate) {
        var tagNames = certificateSearchCriteria.getTags();
        List<Predicate> tagRestrictions = new LinkedList<>();
        if (tagNames != null && !tagNames.isEmpty()) {
            for (String tag : tagNames) {
                Join<GiftCertificate, Tag> tags = certificate.join("tags");
                var predicate = criteriaBuilder.equal(tags.get("name"), tag);
                tagRestrictions.add(predicate);
            }
        }
        return tagRestrictions;
    }

    private List<Predicate> collectLikeRestrictions(CriteriaBuilder criteriaBuilder,
                                                    CertificateSearchCriteria certificateSearchCriteria,
                                                    Root<GiftCertificate> certificate) {
        List<Predicate> likeRestrictions = new LinkedList<>();
        String name = certificateSearchCriteria.getCertificateName();
        if ((name != null)) {
            likeRestrictions.add(criteriaBuilder.like(certificate.get(NAME), "%" + name + "%"));
        }
        String description = certificateSearchCriteria.getCertificateDescription();
        if (description != null) {
            likeRestrictions.add(criteriaBuilder.like(certificate.get(DESCRIPTION), "%" + description + "%"));
        }
        return likeRestrictions;
    }

    private Order[] prepareSorting(CriteriaBuilder criteriaBuilder, CertificateSearchCriteria searchCriteria,
                                   Root<GiftCertificate> certificate) throws InvalidSortTypeException {
        List<Order> sortingRestrictions = new LinkedList<>();
        String sortByName = searchCriteria.getSortByNameType();
        addSortingRestriction(criteriaBuilder, certificate, sortingRestrictions, sortByName, NAME);
        String sortByDate = searchCriteria.getSortByDateType();
        addSortingRestriction(criteriaBuilder, certificate, sortingRestrictions, sortByDate, DATE);
        return sortingRestrictions.toArray(new Order[0]);
    }

    private void addSortingRestriction(CriteriaBuilder criteriaBuilder, Root<GiftCertificate> certificate,
                                       List<Order> sortingRestrictions, String sortBy, String column)
            throws InvalidSortTypeException {
        if (sortBy != null) {
            if (!Enums.getIfPresent(Sort.class, sortBy).isPresent()) {
                throw new InvalidSortTypeException("Invalid sort type value for name. " +
                        "Proper value should match ACS or DESC");
            } else if (sortBy.equals(Sort.ASC.name())) {
                sortingRestrictions.add(criteriaBuilder.asc(certificate.get(column)));
            } else {
                sortingRestrictions.add(criteriaBuilder.desc(certificate.get(column)));
            }
        }
    }
}
