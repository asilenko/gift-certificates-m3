package com.epam.esm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * Model for tag entity. Tag is a category which all gift certificates are grouped by, f.e. "beauty", "sports".
 * One certificate can have multiple but always globally unique tags.
 *
 * @see GiftCertificate
 */
@Entity
@Table(name = "Tags")
public class Tag extends AuditableEntity implements Serializable {

    @Column(nullable = false, unique = true, length = 40)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(getId(), tag.getId())
                && Objects.equals(name, tag.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
