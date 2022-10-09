package com.epam.esm.model;

import com.epam.esm.domain.Tag;
import org.springframework.stereotype.Component;

/**
 * Maps Tag from persistence layer to TagModel and vice versa.
 *
 * @see com.epam.esm.domain.Tag
 * @see TagModel
 */
@Component
public class TagMapper {

    /**
     * Maps TagModel to Tag.
     *
     * @param tagModel
     * @return Tag
     * @see Tag
     * @see TagModel
     */
    public Tag toTag(TagModel tagModel) {
        Tag tag = new Tag();
        tag.setName(tagModel.getName().toLowerCase());
        tag.setId(tagModel.getId());
        return tag;
    }

    /**
     * Maps Tag to TagModel.
     *
     * @param tag
     * @return TagModel
     * @see Tag
     * @see TagModel
     */
    public TagModel toTagBusinessModel(Tag tag) {
        TagModel tagModel = new TagModel();
        tagModel.setName(tag.getName());
        tagModel.setId(tag.getId());
        return tagModel;
    }
}
