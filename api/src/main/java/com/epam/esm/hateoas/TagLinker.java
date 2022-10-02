package com.epam.esm.hateoas;

import com.epam.esm.controller.TagController;
import com.epam.esm.model.TagModel;
import com.epam.esm.pagination.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
    public CollectionModel<TagModel> addLinks(Page<TagModel> page) {
        List<TagModel> tags = page.getContent();
        List<Link> pages = generatePagesLinks(page);
        tags.forEach(this::addLink);
        return CollectionModel.of(tags, pages);
    }

    private List<Link> generatePagesLinks(Page<TagModel> page) {
        List<Link> pages = new ArrayList<>();
        int size = page.getSize();
        int previousPageNumber = page.getPreviousPageNumber();
        Link previousPageLink = generatePageLink(previousPageNumber, size, PageRel.PREVIOUS.name());
        pages.add(previousPageLink);
        int currentPageNumber = page.getCurrentNumber();
        Link selfPageLink = generatePageLink(currentPageNumber, size, PageRel.CURRENT.name());
        pages.add(selfPageLink);
        int nextPageNumber = page.getNextPageNumber();
        Link nextPageLink = generatePageLink(nextPageNumber, size, PageRel.NEXT.name());
        pages.add(nextPageLink);
        return pages;
    }

    private Link generatePageLink(int pageNumber, int size, String pageRel) {
        return linkTo(methodOn(TagController.class).getAll(pageNumber, size))
                .withRel(pageRel.toLowerCase())
                .expand();
    }

    /**
     * Add links to single tag.
     */
    public void addLink(TagModel tag){
        tag.add(linkTo(TagController.class).slash(tag.getId()).withSelfRel());
    }
}
