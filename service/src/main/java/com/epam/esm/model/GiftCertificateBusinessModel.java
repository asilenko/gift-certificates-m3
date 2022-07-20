package com.epam.esm.model;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

/**
 * Represents gift certificate in service layer.
 *
 * @see com.epam.esm.domain.GiftCertificate
 */
@Component
public class GiftCertificateBusinessModel {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int duration;
    private String createDate;
    private String lastUpdateDate;
    private Set<TagBusinessModel> tags;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Set<TagBusinessModel> getTags() {
        return tags;
    }

    public void setTags(Set<TagBusinessModel> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GiftCertificateBusinessModel that = (GiftCertificateBusinessModel) o;
        return duration == that.duration && Objects.equals(id, that.id)
                && Objects.equals(name, that.name) && Objects.equals(description, that.description)
                && Objects.equals(price, that.price) && Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
