package com.epam.esm.dao;

import com.epam.esm.domain.Tag;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

/**
 * Provides CRD methods for Tag.
 *
 * @see Tag
 */
@Repository
public interface TagDAO {

    /**
     * Finds Tag by specified id. Should return empty Optional if no such Tag exist.
     *
     * @param id
     * @return Optional of Tag.
     * @see Tag
     */
    Optional<Tag> findById(final Long id);

    /**
     * Finds all existing tags.
     *
     * @return List of Tags.
     */
    Set<Tag> findAll();

    /**
     * Adds new Tag to the database.
     *
     * @param dao
     * @return Tag
     */
    Tag create(Tag dao);

    /**
     * Delete Tag specified by id.
     *
     * @param id
     */
    void delete(Long id);

    /**
     * Retrieve all tags related to gift certificate by specified gift certificate id.
     *
     * @param id of gift certificate.
     * @return List of tags related to specified gift certificate.
     */
    Set <Tag> findAllTagsForByGiftCertificateId(long id);
}
