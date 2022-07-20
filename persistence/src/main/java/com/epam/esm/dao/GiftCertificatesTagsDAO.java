package com.epam.esm.dao;

import com.epam.esm.domain.Tag;
import org.springframework.stereotype.Repository;

/**
 * Provides create and delete methods for GiftCertificatesTags associative table.
 *
 * @see com.epam.esm.domain.GiftCertificate
 * @see Tag
 */
@Repository
public interface GiftCertificatesTagsDAO {

    /**
     * Creates new GiftCertificate - Tag association.
     *
     * @param giftCertificateId
     * @param tagId
     */
    void createAssociation(long giftCertificateId, long tagId);

    /**
     * Breaks GiftCertificate - Tag association.
     *
     * @param giftCertificateId
     * @param tagId
     */
    void breakAssociation(long giftCertificateId, long tagId);

}
