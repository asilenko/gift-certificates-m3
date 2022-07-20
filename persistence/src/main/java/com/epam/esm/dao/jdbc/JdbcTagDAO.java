package com.epam.esm.dao.jdbc;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.domain.Tag;
import com.epam.esm.rowmappers.TagRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.Optional;

/**
 * Holds logic to access data for Tag Entity.
 *
 * @see TagDAO
 * @see Tag
 */
@Repository
public class JdbcTagDAO implements TagDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private SimpleJdbcInsert simpleJdbcInsertTag;
    private static final String GET_ONE = "SELECT * FROM TAGS WHERE ID = ?";
    private static final String GET_ALL = "SELECT * FROM TAGS";
    private static final String DELETE = "DELETE FROM tags WHERE id = ?";
    private static final String COUNT_BY_NAME = "SELECT COUNT(*) FROM tags WHERE name = ?";
    private static final String FIND_TAGS_RELATED_TO_CERTIFICATE = "SELECT tag_id as id, t.name " +
            "FROM GiftCertificates g " +
            "LEFT JOIN GiftCertificatesTags a ON g.ID = a.gift_certificate_id " +
            "LEFT JOIN tags t ON t.ID = a.tag_id " +
            "WHERE gift_certificate_id = ?";

    private static final String FIND_TAG_ID_BY_NAME = "SELECT id FROM tags WHERE NAME = ?";

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Tag> findById(final Long id) {
        var potentialResults = jdbcTemplate.query(GET_ONE, new TagRowMapper(), id);
        return potentialResults.isEmpty() ? Optional.empty() : Optional.ofNullable(potentialResults.get(0));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Tag> findAll() {
        List<Tag> tags = jdbcTemplate.query(GET_ALL, new TagRowMapper());
        return new HashSet<>(tags);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Tag create(Tag dao) {
        Integer numberOfExistingTagsWithName = jdbcTemplate
                .queryForObject(COUNT_BY_NAME, Integer.class, dao.getName().toLowerCase());
        if (numberOfExistingTagsWithName == 0) {
            Map<String, Object> parameters = new HashMap<>(1);
            parameters.put("name", dao.getName().toLowerCase());
            long newId = simpleJdbcInsertTag.executeAndReturnKey(parameters).longValue();
            dao.setId(newId);
        } else {
            Long tagIdCorrected
                    = jdbcTemplate.queryForObject(FIND_TAG_ID_BY_NAME, Long.class, dao.getName().toLowerCase());
            dao.setId(tagIdCorrected);
        }
        return dao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) {
        jdbcTemplate.update(DELETE, id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Tag> findAllTagsForByGiftCertificateId(long id) {
        List<Tag> tags = jdbcTemplate.query(FIND_TAGS_RELATED_TO_CERTIFICATE, new TagRowMapper(), id);
        return new HashSet<>(tags);
    }
}
