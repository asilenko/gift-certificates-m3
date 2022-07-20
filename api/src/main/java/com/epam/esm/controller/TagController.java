package com.epam.esm.controller;

import com.epam.esm.service.TagService;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.TagBusinessModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * Process requests for Tag resources.
 */
@RestController
@RequestMapping("/tags")
public class TagController {

    @Autowired
    private TagService tagservice;

    /**
     * Get tag resource by specified id.
     *
     * @param id
     * @return TagBusinessModel
     */
    @GetMapping("/{id}")
    public TagBusinessModel getTagByID(@PathVariable Long id) throws ResourceNotFoundException {
        return tagservice.getTagById(id);
    }

    /**
     * Gets all tags.
     *
     * @return List of TagBusinessModel
     */
    @GetMapping
    public Set<TagBusinessModel> getAllTags() {
        return tagservice.getAll();
    }

    /**
     * Creates new Tag resource.
     *
     * @param tag
     * @return TagBusinessModel
     *
     * Request example:
     * <pre>
     * POST /api/tags/ HTTP/1.1
     * Host: localhost:8080
     * Content-Type: application/json
     * Content-Length: 41
     * {
     *     "id": null,
     *     "name": "pets"
     * }
     * </pre>
     */
    @PostMapping
    public TagBusinessModel addNewTag(@RequestBody TagBusinessModel tag) {
        return tagservice.addNewTag(tag);
    }

    /**
     * Delete Tag resource by specified id.
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public void deleteTagById(@PathVariable Long id) {
        tagservice.removeTag(id);
    }
}
