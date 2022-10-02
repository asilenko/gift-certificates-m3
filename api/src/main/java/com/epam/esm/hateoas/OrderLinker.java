package com.epam.esm.hateoas;

import com.epam.esm.controller.OrderController;
import com.epam.esm.model.OrderModel;
import com.epam.esm.pagination.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Provides HATEOAS links for orders.
 */
@Component
public class OrderLinker {

    private final GiftCertificateLinker giftCertificateLinker;

    public OrderLinker(GiftCertificateLinker giftCertificateLinker) {
        this.giftCertificateLinker = giftCertificateLinker;
    }

    /**
     * Add links for:
     * <ul>
     *     <li>orders</li>
     *     <li>previous, current and next page</li>
     * </ul>
     */
    public CollectionModel<OrderModel> addLinks(Page<OrderModel> page) {
        List<OrderModel> orders = page.getContent();
        List<Link> pages = generatePagesLinks(page);
        orders.forEach(this::addLinkWithCertificates);
        return CollectionModel.of(orders, pages);
    }

    private List<Link> generatePagesLinks(Page<OrderModel> page) {
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
        return linkTo(methodOn(OrderController.class).getAll(pageNumber, size))
                .withRel(pageRel.toLowerCase())
                .expand();
    }

    /**
     * Add links to single order and related certificates.
     */
    public void addLinkWithCertificates(OrderModel order) {
        order.add(linkTo(OrderController.class).slash(order.getId()).withSelfRel());
        addCertificatesLinks(order);
    }

    private void addCertificatesLinks(OrderModel order) {
        var certificate = order.getGiftCertificate();
        giftCertificateLinker.addLink(certificate);
    }
}
