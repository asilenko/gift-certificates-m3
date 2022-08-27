package com.epam.esm.dao.jpa;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Transfer data with search criteria for certificate.
 */
@Component
public class CertificateSearchCriteria {
    private List<String> tags = new ArrayList<>();
    private String certificateName;
    private String certificateDescription;
    private String sortByNameType;
    private String sortByDateType;

    public CertificateSearchCriteria() {
    }

    public CertificateSearchCriteria(List<String> tags, String certificateName, String certificateDescription,
                                     String sortByName, String sortByDateType) {
        this.tags = tags;
        this.certificateName = certificateName;
        this.certificateDescription = certificateDescription;
        this.sortByNameType = sortByName;
        this.sortByDateType = sortByDateType;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
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
