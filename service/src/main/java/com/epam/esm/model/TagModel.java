package com.epam.esm.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.Objects;

/**
 * Represents Tag in service layer.
 *
 * @see com.epam.esm.domain.Tag
 */
@Relation(itemRelation = "tag", collectionRelation = "tags")
public class TagModel extends RepresentationModel<TagModel> {
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
        TagModel that = (TagModel) o;
        return Objects.equals(id, that.id) && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
