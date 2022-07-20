package com.epam.esm.dao.jdbc;

import com.epam.esm.dao.GiftCertificatesTagsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * {@inheritDoc}
 */
@Repository
public class JdbcGiftCertificatesTagsDAO implements GiftCertificatesTagsDAO {

    private static final String CREATE_ASSOCIATION = "INSERT INTO GiftCertificatesTags (gift_certificate_id, tag_id)" +
            "VALUES (?, ?)";
    private static final String BREAK_ASSOCIATION = "DELETE FROM GiftCertificatesTags " +
            "WHERE gift_certificate_id=? AND tag_id = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * {@inheritDoc}
     */
    @Override
    public void createAssociation(long giftCertificateId, long tagId) {
        jdbcTemplate.update(CREATE_ASSOCIATION, giftCertificateId, tagId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void breakAssociation(long giftCertificateId, long tagId) {
        jdbcTemplate.update(BREAK_ASSOCIATION, giftCertificateId, tagId);
    }
}
