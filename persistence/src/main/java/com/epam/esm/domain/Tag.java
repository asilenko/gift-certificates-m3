package com.epam.esm.domain;

import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Model for tag entity.
 */
@Component
public class Tag {
    private Long id;
    private String name;

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return name.equalsIgnoreCase(tag.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
