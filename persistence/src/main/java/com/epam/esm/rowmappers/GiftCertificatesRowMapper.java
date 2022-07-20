package com.epam.esm.rowmappers;

import com.epam.esm.domain.GiftCertificate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Provides method to map result set from database to GiftCertificate.
 *
 * @see GiftCertificate
 */
public class GiftCertificatesRowMapper implements RowMapper<GiftCertificate> {

    /**
     * Maps result set from database to GiftCertificate.
     *
     * @param rs the ResultSet to map (pre-initialized for the current row)
     * @param rowNum the number of the current row
     * @return GiftCertificate
     * @throws SQLException
     */
    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        final GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(rs.getLong("id"));
        giftCertificate.setName(rs.getString("name"));
        giftCertificate.setDescription(rs.getString("description"));
        giftCertificate.setPrice(rs.getBigDecimal("price"));
        giftCertificate.setDuration(rs.getInt("duration"));
        giftCertificate.setCreateDate(rs.getTimestamp("create_date").toLocalDateTime());
        giftCertificate.setLastUpdateDate(rs.getTimestamp("last_update_date").toLocalDateTime());
        return giftCertificate;
    }
}
