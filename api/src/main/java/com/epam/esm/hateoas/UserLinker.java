package com.epam.esm.hateoas;

import com.epam.esm.controller.UserController;
import com.epam.esm.model.UserModel;
import com.epam.esm.pagination.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Provides HATEOAS links for users.
 */
@Component
public class UserLinker {
    /**
     * Add links for:
     * <ul>
     *     <li>user</li>
     *     <li>previous, current and next page</li>
     * </ul>
     */

    public CollectionModel<UserModel> addLinks(Page<UserModel> page) {
        List<UserModel> users = page.getContent();
        List<Link> pages = generatePagesLinks(page);
        users.forEach(this::addLink);
        return CollectionModel.of(users, pages);
    }

    private List<Link> generatePagesLinks(Page<UserModel> page) {
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
        return linkTo(methodOn(UserController.class).getAll(pageNumber, size))
                .withRel(pageRel.toLowerCase())
                .expand();
    }

    /**
     * Add links to single user.
     */
    public void addLink(UserModel user){
        user.add(linkTo(UserController.class).slash(user.getId()).withSelfRel());
    }
}
