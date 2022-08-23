package com.epam.esm.dao;

import com.epam.esm.dao.jpa.CrdDao;
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

    Optional<Tag> findByName(String name);

}
