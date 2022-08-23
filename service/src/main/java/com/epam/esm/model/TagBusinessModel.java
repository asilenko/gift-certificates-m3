package com.epam.esm.model;

import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Represents Tag in service layer.
 *
 * @see com.epam.esm.domain.Tag
 */
@Component
public class TagBusinessModel {
    private Long id;
    private String name;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagBusinessModel that = (TagBusinessModel) o;
        return Objects.equals(id, that.id) && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
