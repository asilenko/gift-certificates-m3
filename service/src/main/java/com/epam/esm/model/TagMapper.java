package com.epam.esm.model;

import com.epam.esm.domain.Tag;
import org.springframework.stereotype.Component;

/**
 * Maps Tag from persistence layer to TagBusinessModel and vice versa.
 *
 * @see com.epam.esm.domain.Tag
 * @see TagBusinessModel
 */
@Component
public class TagMapper {

    /**
     * Maps TagBusinessModel to Tag.
     *
     * @param tagBusinessModel
     * @return Tag
     * @see Tag
     * @see TagBusinessModel
     */
    public Tag toTag(TagBusinessModel tagBusinessModel) {
        Tag tag = new Tag();
        tag.setName(tagBusinessModel.getName().toLowerCase());
        tag.setId(tagBusinessModel.getId());
        return tag;
    }

    /**
     * Maps Tag to TagBusinessModel.
     *
     * @param tag
     * @return TagBusinessModel
     * @see Tag
     * @see TagBusinessModel
     */
    public TagBusinessModel toTagBusinessModel(Tag tag) {
        TagBusinessModel tagBusinessModel = new TagBusinessModel();
        tagBusinessModel.setName(tag.getName());
        tagBusinessModel.setId(tag.getId());
        return tagBusinessModel;
    }
}
