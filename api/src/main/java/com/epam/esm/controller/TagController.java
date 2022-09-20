package com.epam.esm.controller;

import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.hateoas.TagLinker;
import com.epam.esm.model.TagBusinessModel;
import com.epam.esm.service.TagService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Process requests for Tag resources.
 */
@RestController
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;
    private final TagLinker tagLinker;

    public TagController(TagService tagservice, TagLinker tagLinker) {
        this.tagService = tagservice;
        this.tagLinker = tagLinker;
    }

    /**
     * Get tag resource by specified id.
     *
     * @param id
     * @return TagBusinessModel
     */
    @GetMapping("/{id}")
    public ResponseEntity<TagBusinessModel> getByID(@PathVariable Long id) throws ResourceNotFoundException {
        var tag = tagService.find(id);
        tagLinker.addLink(tag);
        return new ResponseEntity<>(tag, HttpStatus.OK);
    }

    /**
     * Gets all tags.
     *
     * @return List of TagBusinessModel
     *
     * Request example:
     * <pre>
     * GET /tags/?pageNumber=2&pageSize=10 HTTP/1.1
     * </pre>
     */
    @GetMapping
    public ResponseEntity<CollectionModel<TagBusinessModel>> getAll(
            @RequestParam(defaultValue = "1") Integer pageNumber,
            @RequestParam(defaultValue = "20") Integer pageSize
    ) {
        var page = tagService.findAll(pageNumber, pageSize);
        CollectionModel<TagBusinessModel> collectionModel = tagLinker.addLinks(page);
        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    /**
     * Creates new Tag resource.
     *
     * @param tag
     * @return TagBusinessModel
     * <p>
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
    public ResponseEntity<TagBusinessModel> create(@RequestBody TagBusinessModel tag) {
        var createdTag = tagService.create(tag);
        tagLinker.addLink(createdTag);
        return new ResponseEntity<>(createdTag, HttpStatus.CREATED);
    }

    /**
     * Delete Tag resource by specified id.
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) throws ResourceNotFoundException {
        tagService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
