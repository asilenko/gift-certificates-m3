package com.epam.esm.hateoas;

import com.epam.esm.controller.TagController;
import com.epam.esm.model.TagBusinessModel;
import com.epam.esm.pagination.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Provides HATEOAS links for tags.
 */
@Component
public class TagLinker {

    /**
     * Add links for:
     * <ul>
     *     <li>tag</li>
     *     <li>previous, current and next page</li>
     * </ul>
     */
    public CollectionModel<TagBusinessModel> addLinks(Page<TagBusinessModel> page) {
        List<TagBusinessModel> tags = page.getContent();
        int pageNumber = page.getNumber();
        int previousPage = page.getPreviousPageNumber();
        int nextPage = page.getNextPageNumber();
        int size = page.getSize();
        Link previousPageLink = linkTo(methodOn(TagController.class).getAll(previousPage,size))
                .withRel("previous")
                .expand();
        Link selfPageLink = linkTo(methodOn(TagController.class).getAll(pageNumber,size))
                .withRel("current")
                .expand();
        Link nextPageLink = linkTo(methodOn(TagController.class).getAll(nextPage,size))
                .withRel("next")
                .expand();

        tags.forEach(this::addLink);
        return CollectionModel.of(tags, previousPageLink, selfPageLink, nextPageLink);
    }

    /**
     * Add links to single tag.
     */
    public void addLink(TagBusinessModel tag){
        tag.add(linkTo(TagController.class).slash(tag.getId()).withSelfRel());
    }
}
