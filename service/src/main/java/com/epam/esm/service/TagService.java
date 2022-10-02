package com.epam.esm.service;


import com.epam.esm.model.TagModel;
import com.epam.esm.pagination.Page;
import org.springframework.stereotype.Service;

/**
 * Provides business operations for tag.
 *
 * @see com.epam.esm.domain.Tag
 */
@Service
public interface TagService extends CrdService <TagModel>{


    /**
     * Finds all existing tags.
     *
     * @return List of tags.
     */
    Page<TagModel> findAll(Integer pageNumber, Integer pageSize);

    /**
     * Finds the most widely used tag of a user with the highest cost of all orders.
     *
     * @return TagModel
     */
    TagModel mostWidelyUsed();

}
