package com.epam.esm.hateoas;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.exception.InvalidSortTypeException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.GiftCertificateBusinessModel;
import com.epam.esm.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

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
    public CollectionModel<GiftCertificateBusinessModel> addLinks(Page<GiftCertificateBusinessModel> page,
                                                                  List<String> tags,
                                                                  String name,
                                                                  String description,
                                                                  String sortByNameType,
                                                                  String sortByDateType)
            throws InvalidSortTypeException, ResourceNotFoundException {
        List<GiftCertificateBusinessModel> giftCertificates = page.getContent();
        int pageNumber = page.getNumber();
        int previousPage = page.getPreviousPageNumber();
        int nextPage = page.getNextPageNumber();
        int size = page.getSize();
        Link previousPageLink = linkTo(methodOn(GiftCertificateController.class).getAllMatching(tags, name, description,
                sortByNameType, sortByDateType, previousPage, size))
                .withRel("previous")
                .expand();
        Link selfPageLink = linkTo(methodOn(GiftCertificateController.class).getAllMatching(tags, name, description,
                sortByNameType, sortByDateType, pageNumber, size))
                .withRel("current")
                .expand();
        Link nextPageLink = linkTo(methodOn(GiftCertificateController.class).getAllMatching(tags, name, description,
                sortByNameType, sortByDateType, nextPage, size))
                .withRel("next")
                .expand();

        giftCertificates.forEach(this::addLinkWithTags);
        return CollectionModel.of(giftCertificates, previousPageLink, selfPageLink, nextPageLink);
    }

    /**
     * Add links to single certificate and related tags.
     */
    public void addLinkWithTags(GiftCertificateBusinessModel certificate) {
        addLink(certificate);
        addTagsLinks(certificate);
    }

    /**
     * Add links to single certificate.
     */
    public void addLink(GiftCertificateBusinessModel certificate) {
        certificate.add(linkTo(GiftCertificateController.class).slash(certificate.getId()).withSelfRel());
    }

    private void addTagsLinks(GiftCertificateBusinessModel certificate) {
        var tags = certificate.getTags();
        tags.forEach(tagLinker::addLink);
    }
}
