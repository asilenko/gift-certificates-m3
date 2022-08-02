package com.epam.esm.service;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.TagBusinessModel;
import com.epam.esm.model.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * {@inheritDoc}
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    TagMapper tagMapper;

    @Autowired
    private TagDAO tagDAO;

    /**
     * {@inheritDoc}
     */
    @Override
    public TagBusinessModel getTagById(Long id) throws ResourceNotFoundException {
        return tagMapper.toTagBusinessModel(tagDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No resource with id " + id)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<TagBusinessModel> getAll() {
        return tagDAO.findAll()
                .stream()
                .map(tagMapper::toTagBusinessModel)
                .collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TagBusinessModel addNewTag(TagBusinessModel tagBusinessModel) {
        var createdTag = tagDAO.create(tagMapper.toTag(tagBusinessModel));
        return tagMapper.toTagBusinessModel(createdTag);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeTag(Long id) throws ResourceNotFoundException {
        tagDAO.delete(id);
    }
}
