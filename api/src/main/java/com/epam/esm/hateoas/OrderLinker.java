package com.epam.esm.hateoas;

import com.epam.esm.controller.OrderController;
import com.epam.esm.model.OrderBusinessModel;
import com.epam.esm.pagination.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Provides HATEOAS links for orders.
 */
@Component
public class OrderLinker {
    /**
     * Add links for:
     * <ul>
     *     <li>orders</li>
     *     <li>previous, current and next page</li>
     * </ul>
     */
    public CollectionModel<OrderBusinessModel> addLinks(Page<OrderBusinessModel> page) {
        List<OrderBusinessModel> orders = page.getContent();
        int pageNumber = page.getNumber();
        int previousPage = page.getPreviousPageNumber();
        int nextPage = page.getNextPageNumber();
        int size = page.getSize();
        Link previousPageLink = linkTo(methodOn(OrderController.class).getAll(previousPage,size))
                .withRel("previous")
                .expand();
        Link selfPageLink = linkTo(methodOn(OrderController.class).getAll(pageNumber,size))
                .withRel("current")
                .expand();
        Link nextPageLink = linkTo(methodOn(OrderController.class).getAll(nextPage,size))
                .withRel("next")
                .expand();

        orders.forEach(this::addLink);
        return CollectionModel.of(orders, previousPageLink, selfPageLink, nextPageLink);
    }

    /**
     * Add links to single order.
     */
    public void addLink(OrderBusinessModel order){
        order.add(linkTo(OrderController.class).slash(order.getId()).withSelfRel());
    }
}
