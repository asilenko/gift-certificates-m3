package com.epam.esm.dao.jdbc;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.domain.Tag;
import com.epam.esm.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.List;
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
public class JPATagDAO implements TagDAO {
    @Autowired
    EntityManager entityManager;
    private static final String GET_ALL = "FROM Tag";
    private static final String FIND_BY_NAME = "FROM Tag t WHERE t.name = ?1";

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Tag> findById(final Long id) {
        return Optional.ofNullable(entityManager.find(Tag.class, id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Tag> findAll() {
        List<Tag> tags = entityManager.createQuery(GET_ALL, Tag.class).getResultList();
        return new HashSet<>(tags);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Tag create(Tag tag) {
        Optional<Tag> tagFromDB = findByName(tag.getName());
        if (tagFromDB.isEmpty()) {
            tag.setId(null);
            entityManager.getTransaction().begin();
            entityManager.persist(tag);
            entityManager.getTransaction().commit();
            entityManager.clear();
            return tag;
        } else {
            return tagFromDB.get();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        Optional<Tag> tag = findById(id);
        if (tag.isEmpty()) {
            throw new ResourceNotFoundException("No tag related to id: " + id);
        } else {
            entityManager.getTransaction().begin();
            entityManager.remove(tag.get());
            entityManager.getTransaction().commit();
            entityManager.clear();
        }
    }

    public Optional<Tag> findByName(String name) {
        var potentialTags = entityManager.createQuery(FIND_BY_NAME).setParameter(1, name).getResultList();
        return potentialTags.isEmpty() ? Optional.empty() : Optional.of((Tag) potentialTags.get(0));
    }
}
