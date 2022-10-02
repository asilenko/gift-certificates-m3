package com.epam.esm.hateoas;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.exception.InvalidSortTypeException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.GiftCertificateModel;
import com.epam.esm.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Provides HATEOAS links for gift certificates.
 */
@Component
public class GiftCertificateLinker {

    TagLinker tagLinker;

    @Autowired
    public GiftCertificateLinker(TagLinker tagLinker) {
        this.tagLinker = tagLinker;
    }

    /**
     * Add links for:
     * <ul>
     *     <li>gift certificate</li>
     *     <li>tags related to gift certificate</li>
     *     <li>previous, current and next page</li>
     * </ul>
     */
    public CollectionModel<GiftCertificateModel> addLinks(Page<GiftCertificateModel> page,
                                                          List<String> tags,
                                                          String name,
                                                          String description,
                                                          String sortByNameType,
                                                          String sortByDateType)

            throws InvalidSortTypeException, ResourceNotFoundException {
        List<GiftCertificateModel> giftCertificates = page.getContent();
        List<Link> pages = generatePagesLinks(page, tags, name, description, sortByNameType, sortByDateType);
        giftCertificates.forEach(this::addLinkWithTags);
        return CollectionModel.of(giftCertificates, pages);
    }

    private List<Link> generatePagesLinks(Page<GiftCertificateModel> page,
                                          List<String> tags,
                                          String name,
                                          String description,
                                          String sortByNameType,
                                          String sortByDateType)
            throws InvalidSortTypeException, ResourceNotFoundException {
        List<Link> pages = new ArrayList<>();
        int size = page.getSize();
        int previousPageNumber = page.getPreviousPageNumber();
        Link previous = generatePageLink(tags, name, description, sortByNameType, sortByDateType, size,
                previousPageNumber, PageRel.PREVIOUS.name());
        pages.add(previous);
        int currentPageNumber = page.getCurrentNumber();
        Link self = generatePageLink(tags, name, description, sortByNameType, sortByDateType, size,
                currentPageNumber, PageRel.CURRENT.name());
        pages.add(self);
        int nextPageNumber = page.getNextPageNumber();
        Link next = generatePageLink(tags, name, description, sortByNameType, sortByDateType, size,
                nextPageNumber, PageRel.NEXT.name());
        pages.add(next);
        return pages;
    }

    private Link generatePageLink(List<String> tags,
                                  String name,
                                  String description,
                                  String sortByNameType,
                                  String sortByDateType,
                                  int size,
                                  int pageNumber,
                                  String pageRel) throws InvalidSortTypeException, ResourceNotFoundException {
        return linkTo(methodOn(GiftCertificateController.class).getAllMatching(tags, name, description,
                sortByNameType, sortByDateType, pageNumber, size))
                .withRel(pageRel.toLowerCase())
                .expand();
    }

    /**
     * Add links to single certificate and related tags.
     */
    public void addLinkWithTags(GiftCertificateModel certificate) {
        addLink(certificate);
        addTagsLinks(certificate);
    }

    /**
     * Add links to single certificate.
     */
    public void addLink(GiftCertificateModel certificate) {
        certificate.add(linkTo(GiftCertificateController.class).slash(certificate.getId()).withSelfRel());
    }

    private void addTagsLinks(GiftCertificateModel certificate) {
        var tags = certificate.getTags();
        tags.forEach(tagLinker::addLink);
    }
}
