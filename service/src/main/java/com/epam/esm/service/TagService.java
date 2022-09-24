package com.epam.esm.service;


import com.epam.esm.domain.Tag;
import com.epam.esm.model.TagBusinessModel;
import com.epam.esm.pagination.Page;
import org.springframework.stereotype.Service;

/**
 * Provides business operations for tag.
 *
 * @see com.epam.esm.domain.Tag
 */
@Service
public interface TagService extends CrdService <TagBusinessModel>{


    /**
     * Finds all existing tags.
     *
     * @return List of tags.
     */
    Page<TagBusinessModel> findAll(Integer pageNumber, Integer pageSize);

    /**
     * Finds the most widely used tag of a user with the highest cost of all orders.
     *
     * @return TagBusinessModel
     */
    TagBusinessModel mostWidelyUsed();

}
