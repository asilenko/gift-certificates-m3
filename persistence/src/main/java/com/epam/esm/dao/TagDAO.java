package com.epam.esm.dao;

import com.epam.esm.domain.Tag;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Provides CRD methods for Tag.
 *
 * @see Tag
 */
@Repository
public interface TagDAO extends CrdDao<Tag> {

    /**
     * Finds tag by provided name.
     *
     * @param name of tag.
     * @return Tag.
     */
    Optional<Tag> findByName(String name);

    /**
     * Finds the most widely used tag of a user with the highest cost of all orders.
     *
     * @return Tag
     */
    Tag mostWidelyUsed();

}
