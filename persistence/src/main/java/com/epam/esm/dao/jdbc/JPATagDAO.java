package com.epam.esm.dao.jdbc;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.domain.Tag;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.rowmappers.TagRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
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
public class JPATagDAO implements TagDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    EntityManager entityManager;
    private static final String GET_ALL = "FROM Tag";
    private static final String FIND_BY_NAME = "FROM Tag t WHERE t.name = ?1";
    private static final String FIND_TAGS_RELATED_TO_CERTIFICATE = "SELECT tag_id as id, t.name " +
            "FROM GiftCertificates g " +
            "LEFT JOIN GiftCertificatesTags a ON g.ID = a.gift_certificate_id " +
            "LEFT JOIN tags t ON t.ID = a.tag_id " +
            "WHERE gift_certificate_id = ?";

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
        List<Tag> tags = entityManager.createQuery(GET_ALL).getResultList();
        return new HashSet<>(tags);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Tag create(Tag tag) {
        Optional<Tag> tagFromDB = findByName(tag.getName());
        if (tagFromDB.isEmpty()) {
            tag.setId(null);
            entityManager.getTransaction().begin();
            entityManager.persist(tag);
            entityManager.getTransaction().commit();
            return findByName(tag.getName())
                    .orElseThrow(() -> new EntityNotFoundException("Unexpected exception"));
        } else {
            return tagFromDB.get();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void delete(Long id) throws ResourceNotFoundException {
        Optional<Tag> tag = findById(id);
        if (tag.isEmpty()) {
            throw new ResourceNotFoundException("No tag related to id: " + id);
        } else {
            entityManager.getTransaction().begin();
            entityManager.remove(tag.get());
            entityManager.getTransaction().commit();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Set<Tag> findAllTagsForByGiftCertificateId(long id) {
        List<Tag> tags = jdbcTemplate.query(FIND_TAGS_RELATED_TO_CERTIFICATE, new TagRowMapper(), id);
        return new HashSet<>(tags);
    }

    @Transactional
    public Optional<Tag> findByName(String name) {
        var potentialTags = entityManager.createQuery(FIND_BY_NAME).setParameter(1, name).getResultList();
        return potentialTags.isEmpty() ? Optional.empty() : Optional.of((Tag) potentialTags.get(0));
    }
}
