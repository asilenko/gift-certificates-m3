package com.epam.esm.service;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.domain.Tag;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.TagMapper;
import com.epam.esm.model.TagModel;
import com.epam.esm.pagination.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * {@inheritDoc}
 */
@Service
@Transactional
public class TagServiceImpl implements TagService {

    private final TagMapper tagMapper;
    private final TagDAO tagDAO;

    public TagServiceImpl(TagMapper tagMapper, TagDAO tagDAO) {
        this.tagMapper = tagMapper;
        this.tagDAO = tagDAO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TagModel find(Long id) throws ResourceNotFoundException {
        return tagMapper.toTagBusinessModel(tagDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No resource with id " + id)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<TagModel> findAll(Integer pageNumber, Integer pageSize) {
        var tags = tagDAO.findAll(pageNumber, pageSize)
                .stream()
                .map(tagMapper::toTagBusinessModel)
                .collect(Collectors.toList());
        var total = tagDAO.getTotal();
        return new Page<>(pageNumber, pageSize, total, tags);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TagModel create(TagModel tagModel) {
        Optional<Tag> tagFromDB = tagDAO.findByName(tagModel.getName().toLowerCase().trim());
        if (tagFromDB.isEmpty()) {
            tagModel.setId(null);
            var createdTag = tagDAO.create(tagMapper.toTag(tagModel));
            return tagMapper.toTagBusinessModel(createdTag);
        } else {
            return tagMapper.toTagBusinessModel(tagFromDB.get());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        tagDAO.delete(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TagModel mostWidelyUsed() {
        return tagMapper.toTagBusinessModel(tagDAO.mostWidelyUsed());
    }
}
