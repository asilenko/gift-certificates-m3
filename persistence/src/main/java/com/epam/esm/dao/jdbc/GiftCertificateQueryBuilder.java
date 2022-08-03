package com.epam.esm.dao.jdbc;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.exception.InvalidSortTypeException;
import com.google.common.base.Enums;
import com.google.common.base.Joiner;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * Builder for queries for GiftCertificate.
 *
 * @see JPAGiftCertificateDAO
 */
@Component
public class GiftCertificateQueryBuilder {

    private static final String UPDATE_QUERY_PREFIX = "UPDATE GiftCertificates SET ";
    private static final String UPDATE_PARAMS_DELIMITER = ", ";
    private static final String UPDATE_COLUMN_NAME_AND_VALUE_DELIMITER = " = ";
    private static final String UPDATE_QUERY_POSTFIX = " WHERE ID = ";
    private static final String SEARCH_QUERY_WITH_TAG_PREFIX = "SELECT g.* " +
            "FROM GiftCertificates g " +
            "LEFT JOIN GiftCertificatesTags a ON g.ID = a.gift_certificate_id " +
            "LEFT JOIN tags t ON t.ID = a.tag_id";
    private static final String SEARCH_QUERY_NO_TAG_PREFIX = "SELECT g.* " +
            "FROM GiftCertificates g ";


    private static final String WHERE = " WHERE ";
    private static final String WHERE_TAG_NAME = " t.name = '%s' ";
    private static final String AND = "AND";
    private static final String WHERE_CERTIFICATE_NAME = " g.name LIKE '%%%s%%' ";
    private static final String WHERE_CERTIFICATE_DESCRIPTION = " g.description LIKE '%%%s%%' ";
    private static final String ORDER_BY = " ORDER BY ";
    private static final String SORT_CERTIFICATE_CREATE_DATE = " g.create_date %s ";
    private static final String SORT_CERTIFICATE_NAME = " g.name %s ";

    enum Sort {ASC, DESC}

    String generateUpdateQuery(long id, GiftCertificate giftCertificate) {
        String query = "";
        Map<String, String> paramsToUpdate = collectParamsToUpdate(giftCertificate);

        if (!paramsToUpdate.isEmpty()) {
            query += UPDATE_QUERY_PREFIX;

            StringJoiner stringJoiner = new StringJoiner(UPDATE_PARAMS_DELIMITER);
            paramsToUpdate.forEach((name, value)
                    -> stringJoiner.add(name + UPDATE_COLUMN_NAME_AND_VALUE_DELIMITER + "'" + value + "'"));
            stringJoiner.add("last_update_date = CURRENT_TIMESTAMP");
            query += stringJoiner.toString();
            query += (UPDATE_QUERY_POSTFIX + id);
        }
        return query;
    }

    private Map<String, String> collectParamsToUpdate(GiftCertificate giftCertificate) {
        Map<String, String> params = new LinkedHashMap<>();
        if (giftCertificate.getName() != null) {
            params.put("name", giftCertificate.getName());
        }
        if (giftCertificate.getDescription() != null) {
            params.put("description", giftCertificate.getDescription());
        }
        if (giftCertificate.getPrice() != null) {
            params.put("price", giftCertificate.getPrice().toString());
        }
        if (giftCertificate.getDuration() != 0) {
            params.put("duration", String.valueOf(giftCertificate.getDuration()));
        }
        return params;
    }

    String generateSearchQuery(CertificateSearchCriteria certificateSearchCriteria) throws InvalidSortTypeException {
        String searchQuery = (certificateSearchCriteria.getTagName() == null) ?
                SEARCH_QUERY_NO_TAG_PREFIX : SEARCH_QUERY_WITH_TAG_PREFIX;
        searchQuery = collectSearchCriteria(certificateSearchCriteria, searchQuery);
        searchQuery = collectSortParams(certificateSearchCriteria, searchQuery);
        return searchQuery;
    }

    private String collectSortParams(CertificateSearchCriteria certificateSearchCriteria, String searchQuery)
            throws InvalidSortTypeException {
        List<String> sortParams = new ArrayList<>();
        if (certificateSearchCriteria.getSortByNameType() != null) {
            if (!Enums.getIfPresent(Sort.class, certificateSearchCriteria.getSortByNameType()).isPresent()) {
                throw new InvalidSortTypeException("Invalid sort type value for name. " +
                        "Proper value should match ACS or DESC");
            } else {
                sortParams.add(String.format(SORT_CERTIFICATE_NAME, certificateSearchCriteria.getSortByNameType()));
            }
        }
        if (certificateSearchCriteria.getSortByDateType() != null) {
            if (!Enums.getIfPresent(Sort.class, certificateSearchCriteria.getSortByDateType()).isPresent()) {
                throw new InvalidSortTypeException("Invalid sort type value for date. " +
                        "Proper value should match ACS or DESC");
            } else {
                sortParams.add(String.format(SORT_CERTIFICATE_CREATE_DATE,
                        certificateSearchCriteria.getSortByDateType()));
            }
        }
        if (!sortParams.isEmpty()) {
            searchQuery += ORDER_BY;
            searchQuery += Joiner.on(", ").join(sortParams);
        }
        return searchQuery;
    }

    private String collectSearchCriteria(CertificateSearchCriteria certificateSearchCriteria, String searchQuery) {
        List<String> whereParams = new ArrayList<>();
        if (certificateSearchCriteria.getTagName() != null) {
            whereParams.add(String.format(WHERE_TAG_NAME, certificateSearchCriteria.getTagName()));
        }
        if (certificateSearchCriteria.getCertificateName() != null) {
            whereParams.add(String.format(WHERE_CERTIFICATE_NAME, certificateSearchCriteria.getCertificateName()));
        }
        if (certificateSearchCriteria.getCertificateDescription() != null) {
            whereParams.add(String.format(WHERE_CERTIFICATE_DESCRIPTION,
                    certificateSearchCriteria.getCertificateDescription()));
        }
        if (!whereParams.isEmpty()) {
            searchQuery += WHERE;
            searchQuery += Joiner.on(AND).join(whereParams);
        }
        return searchQuery;
    }
}
