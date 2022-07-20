package com.epam.esm.dao;

import com.epam.esm.dao.jdbc.CertificateSearchCriteria;
import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.exception.InvalidSortTypeException;
import com.epam.esm.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

/**
 * Provides CRUD methods for GiftCertificate.
 *
 * @see GiftCertificate
 */
public interface GiftCertificateDAO {

    /**
     * Finds GiftCertificate by specified id. Should return empty Optional if no such GiftCertificate exist.
     *
     * @param id
     * @return Optional of GiftCertificate.
     */
    Optional<GiftCertificate> findById(final long id);

    /**
     * Finds all existing GiftCertificates.
     *
     * @return List of GiftCertificates.
     */
    List<GiftCertificate> findAll();

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

    /**
     * Add new GiftCertificate to the database.
     *
     * @param dao
     * @return GiftCertificate
     */
    GiftCertificate create(GiftCertificate dao);

    /**
     * Delete GiftCertificate specified by id.
     *
     * @param id
     */
    void delete(long id);
}
