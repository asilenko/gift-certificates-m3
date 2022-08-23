package com.epam.esm.dao;

import com.epam.esm.dao.jpa.CertificateSearchCriteria;
import com.epam.esm.dao.jpa.CrdDao;
import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.exception.InvalidSortTypeException;
import com.epam.esm.exception.ResourceNotFoundException;

import java.util.List;

/**
 * Provides CRUD methods for GiftCertificate.
 *
 * @see GiftCertificate
 */
public interface GiftCertificateDAO extends CrdDao<GiftCertificate> {


    /**
     * Finds all GiftCertificates matching search criteria.
     *
     * @return List of GiftCertificates.
     */
    List<GiftCertificate> findAllMatchingPrams(CertificateSearchCriteria certificateSearchCriteria)
            throws InvalidSortTypeException;

    /**
     * Updates GiftCertificate according to non-null fields of the provided GiftCertificate instance.
     *
     * @param dao
     * @return Instance of updated GiftCertificate
     * @throws ResourceNotFoundException
     */
    GiftCertificate update(GiftCertificate dao) throws ResourceNotFoundException;
}
