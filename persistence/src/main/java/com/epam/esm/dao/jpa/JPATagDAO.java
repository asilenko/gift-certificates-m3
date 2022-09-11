package com.epam.esm.dao.jpa;

import com.epam.esm.dao.AbstractCrdDao;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.domain.Tag;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Holds logic to access data for Tag Entity.
 *
 * @see TagDAO
 * @see Tag
 */
@Repository
@Transactional
public class JPATagDAO extends AbstractCrdDao<Tag> implements TagDAO {

    private static final String FIND_BY_NAME = "FROM Tag t WHERE t.name = ?1";

    public JPATagDAO() {
        setClazz(Tag.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Tag> findAll(Integer page, Integer size) {
        return new HashSet<>(super.findAll(page, size));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Tag> findByName(String name) {
        var potentialTags = entityManager.createQuery(FIND_BY_NAME).setParameter(1, name).getResultList();
        return potentialTags.isEmpty() ? Optional.empty() : Optional.of((Tag) potentialTags.get(0));
    }
}
