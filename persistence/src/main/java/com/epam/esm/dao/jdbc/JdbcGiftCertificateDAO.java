package com.epam.esm.dao.jdbc;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Tag;
import com.epam.esm.exception.InvalidSortTypeException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.rowmappers.GiftCertificatesRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Holds logic to access data for Gift Certificate Entity.
 *
 * @see GiftCertificateDAO
 * @see GiftCertificate
 */
@Repository
public class JdbcGiftCertificateDAO implements GiftCertificateDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private SimpleJdbcInsert simpleJdbcInsertCertificate;
    @Autowired
    private GiftCertificateQueryBuilder giftCertificateQueryBuilder;

    private static final String GET_ONE = "SELECT * FROM GiftCertificates WHERE id = ?";
    private static final String GET_ALL = "SELECT * FROM GiftCertificates";
    private static final String DELETE = "DELETE FROM GiftCertificates WHERE id = ?";

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<GiftCertificate> findById(final long id) {
        var potentialResults = jdbcTemplate.query(GET_ONE, new GiftCertificatesRowMapper(), id);
        return potentialResults.isEmpty() ? Optional.empty() : Optional.ofNullable(potentialResults.get(0));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GiftCertificate> findAll() {
        return jdbcTemplate.query(GET_ALL, new GiftCertificatesRowMapper());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GiftCertificate> findAllMatchingPrams(CertificateSearchCriteria certificateSearchCriteria)
            throws InvalidSortTypeException {
        String searchQuery = giftCertificateQueryBuilder.generateSearchQuery(certificateSearchCriteria);
        return jdbcTemplate.query(searchQuery, new GiftCertificatesRowMapper());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) throws ResourceNotFoundException {
        checkNotNull(giftCertificate, "Gift certificate entity is null!");
        var id = giftCertificate.getId();
        String updateQuery = giftCertificateQueryBuilder.generateUpdateQuery(id, giftCertificate);
        if (!updateQuery.isEmpty()) {
            jdbcTemplate.execute(updateQuery);
        }
        return findById(id).orElseThrow(() -> new ResourceNotFoundException("No resource with id " + id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GiftCertificate create(GiftCertificate dao) {
        Map<String, Object> parameters = collectParamsForInsert(dao);

        long newId = simpleJdbcInsertCertificate.executeAndReturnKey(parameters).longValue();
        dao.setId(newId);
        return dao;
    }

    private Map<String, Object> collectParamsForInsert(GiftCertificate dao) {
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("name", dao.getName());
        parameters.put("description", dao.getDescription());
        parameters.put("price", dao.getPrice());
        parameters.put("duration", dao.getDuration());
        parameters.put("create_date", LocalDateTime.now());
        parameters.put("last_update_date", LocalDateTime.now());
        return parameters;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(long id) {
        jdbcTemplate.update(DELETE, id);
    }
}
