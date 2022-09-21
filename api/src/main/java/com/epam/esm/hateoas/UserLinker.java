package com.epam.esm.hateoas;

import com.epam.esm.controller.UserController;
import com.epam.esm.model.UserBusinessModel;
import com.epam.esm.pagination.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

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

    public CollectionModel<UserBusinessModel> addLinks(Page<UserBusinessModel> page) {
        List<UserBusinessModel> users = page.getContent();
        int pageNumber = page.getNumber();
        int previousPage = page.getPreviousPageNumber();
        int nextPage = page.getNextPageNumber();
        int size = page.getSize();
        Link previousPageLink = linkTo(methodOn(UserController.class).getAll(previousPage,size))
                .withRel("previous")
                .expand();
        Link selfPageLink = linkTo(methodOn(UserController.class).getAll(pageNumber,size))
                .withRel("current")
                .expand();
        Link nextPageLink = linkTo(methodOn(UserController.class).getAll(nextPage,size))
                .withRel("next")
                .expand();

        users.forEach(this::addLink);
        return CollectionModel.of(users, previousPageLink, selfPageLink, nextPageLink);
    }

    /**
     * Add links to single user.
     */
    public void addLink(UserBusinessModel user){
        user.add(linkTo(UserController.class).slash(user.getId()).withSelfRel());
    }
}
