package com.epam.esm.service;

import com.epam.esm.dao.jpa.CertificateSearchCriteria;
import com.epam.esm.exception.InvalidSortTypeException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.GiftCertificateBusinessModel;
import com.epam.esm.pagination.Page;

import java.util.Optional;

/**
 * Provides business operations for a gift certificate.
 *
 * @see com.epam.esm.domain.GiftCertificate
 */
public interface GiftCertificateService extends CrdService <GiftCertificateBusinessModel>{

    /**
     * Updates gift certificate with tags.
     *
     * @param giftCertificateBusinessModel
     * @return
     * @throws ResourceNotFoundException
     */
    GiftCertificateBusinessModel update(GiftCertificateBusinessModel giftCertificateBusinessModel)
            throws ResourceNotFoundException, InvalidFieldValueException;

    /**
     * Return list of gift certificates with tags. Results might be sorted by certificate name or creation date.
     *
     * @param searchCriteria
     * @return Page with list of matching certificates with tags according to search criteria. All existing certificates
     * will be returned in case no search criteria or no request body provided.
     * @throws InvalidSortTypeException
     */
    Page<GiftCertificateBusinessModel> findAllMatching(Optional<CertificateSearchCriteria> searchCriteria,
                                                       Integer pageNumber, Integer pageSize)
            throws InvalidSortTypeException;
}
