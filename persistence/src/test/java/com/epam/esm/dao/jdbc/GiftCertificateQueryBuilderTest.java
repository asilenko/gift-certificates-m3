package com.epam.esm.dao.jdbc;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.exception.InvalidSortTypeException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GiftCertificateQueryBuilderTest {

    private static final String UPDATE_QUERY_PREFIX = "UPDATE GiftCertificates SET ";
    private static final String UPDATE_PARAMS_DELIMITER = "', ";
    private static final String UPDATE_COLUMN_NAME_AND_VALUE_DELIMITER = " = '";
    private static final String UPDATE_QUERY_POSTFIX = " WHERE ID = ";
    private static final int ID = 1;

    @Test
    void updateQueryShouldContainNameAndLastUpdateDateOnlyIfOnlyNameIsPassed() {
        //GIVEN
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName("Test");

        //WHEN
        String actual = new GiftCertificateQueryBuilder().generateUpdateQuery(ID, giftCertificate);
        String expected = UPDATE_QUERY_PREFIX
                + "name"
                + UPDATE_COLUMN_NAME_AND_VALUE_DELIMITER
                + giftCertificate.getName()
                + "', last_update_date = CURRENT_TIMESTAMP"
                + UPDATE_QUERY_POSTFIX
                + ID;

        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void updateQueryShouldReturnEmptyStringIfNoValuesPassed() {
        //GIVEN
        GiftCertificate giftCertificate = new GiftCertificate();

        //WHEN
        String actual = new GiftCertificateQueryBuilder().generateUpdateQuery(ID, giftCertificate);

        //THEN
        assertTrue(actual.isEmpty());
    }

    @Test
    void updateQueryShouldContainAllValuesIfAllValuesArePassed() {
        //GIVEN
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName("Test");
        giftCertificate.setDescription("Test description");
        giftCertificate.setPrice(new BigDecimal("42.00"));
        giftCertificate.setDuration(48);

        //WHEN
        String actual = new GiftCertificateQueryBuilder().generateUpdateQuery(ID, giftCertificate);
        String expected = UPDATE_QUERY_PREFIX
                + "name"
                + UPDATE_COLUMN_NAME_AND_VALUE_DELIMITER
                + giftCertificate.getName()
                + UPDATE_PARAMS_DELIMITER
                + "description"
                + UPDATE_COLUMN_NAME_AND_VALUE_DELIMITER
                + giftCertificate.getDescription()
                + UPDATE_PARAMS_DELIMITER
                + "price"
                + UPDATE_COLUMN_NAME_AND_VALUE_DELIMITER
                + giftCertificate.getPrice().toString()
                + UPDATE_PARAMS_DELIMITER
                + "duration"
                + UPDATE_COLUMN_NAME_AND_VALUE_DELIMITER
                + giftCertificate.getDuration()
                + "', last_update_date = CURRENT_TIMESTAMP"
                + UPDATE_QUERY_POSTFIX
                + ID;

        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void searchQueryShouldContainAllValuesIfAllValuesArePassed() throws InvalidSortTypeException {
        //GIVEN
        CertificateSearchCriteria certificateSearchCriteria =
                new CertificateSearchCriteria("jewelry", "hin", "Poland", "ASC", "DESC");
        String expected = "SELECT g.* " +
                "FROM GiftCertificates g " +
                "LEFT JOIN GiftCertificatesTags a ON g.ID = a.gift_certificate_id " +
                "LEFT JOIN tags t ON t.ID = a.tag_id " +
                "WHERE  t.name = 'jewelry' " +
                "AND g.name LIKE '%hin%' " +
                "AND g.description LIKE '%Poland%'  " +
                "ORDER BY  g.name ASC ,  g.create_date DESC ";
        //WHEN
        String actual = new GiftCertificateQueryBuilder().generateSearchQuery(certificateSearchCriteria);
        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void searchQueryWithOneSearchParamShouldBeGeneratedProperly() throws InvalidSortTypeException {
        //GIVEN
        CertificateSearchCriteria certificateSearchCriteria =
                new CertificateSearchCriteria(null, null, "Poland", null, null);
        String expected = "SELECT g.* FROM GiftCertificates g  WHERE  g.description LIKE '%Poland%' ";
        //WHEN
        String actual = new GiftCertificateQueryBuilder().generateSearchQuery(certificateSearchCriteria);
        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void searchQueryWithOneSortParamShouldBeGeneratedProperly() throws InvalidSortTypeException {
        //GIVEN
        CertificateSearchCriteria certificateSearchCriteria =
                new CertificateSearchCriteria(null, null, null, "ASC", null);
        String expected = "SELECT g.* FROM GiftCertificates g  ORDER BY  g.name ASC ";
        //WHEN
        String actual = new GiftCertificateQueryBuilder().generateSearchQuery(certificateSearchCriteria);
        //THEN
        assertEquals(expected, actual);
    }
}
