package com.epam.esm.dao.jdbc;

import org.springframework.stereotype.Component;

/**
 * Transfer data with search criteria for certificate.
 */
@Component
public class CertificateSearchCriteria {
    private String tagName;
    private String certificateName;
    private String certificateDescription;
    private String sortByNameType;
    private String sortByDateType;

    public CertificateSearchCriteria() {
    }

    public CertificateSearchCriteria(String tagName, String certificateName, String certificateDescription,
                                     String sortByName, String sortByDateType) {
        this.tagName = tagName;
        this.certificateName = certificateName;
        this.certificateDescription = certificateDescription;
        this.sortByNameType = sortByName;
        this.sortByDateType = sortByDateType;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getCertificateName() {
        return certificateName;
    }

    public void setCertificateName(String certificateName) {
        this.certificateName = certificateName;
    }

    public String getCertificateDescription() {
        return certificateDescription;
    }

    public void setCertificateDescription(String certificateDescription) {
        this.certificateDescription = certificateDescription;
    }

    public String getSortByNameType() {
        return sortByNameType;
    }

    public void setSortByNameType(String sortByNameType) {
        this.sortByNameType = sortByNameType;
    }

    public String getSortByDateType() {
        return sortByDateType;
    }

    public void setSortByDateType(String sortByDateType) {
        this.sortByDateType = sortByDateType;
    }
}
