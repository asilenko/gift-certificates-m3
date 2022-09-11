package com.epam.esm.service;


import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.TagBusinessModel;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Provides business operations for tag.
 *
 * @see com.epam.esm.domain.Tag
 */
@Service
public interface TagService {

    /**
     * Finds tag with specified id.
     *
     * @param id
     * @return tag.
     * @throws ResourceNotFoundException
     */
    TagBusinessModel getTagById(Long id) throws ResourceNotFoundException;

    /**
     * Finds all existing tags.
     *
     * @return List of tags.
     */
    Set<TagBusinessModel> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Adds new tag to database.
     *
     * @param tagBusinessModel
     * @return tag with the assigned id.
     */
    TagBusinessModel addNewTag(TagBusinessModel tagBusinessModel);

    /**
     * Removes tag with specified id.
     *
     * @param id
     */
    void removeTag(Long id) throws ResourceNotFoundException;

}
