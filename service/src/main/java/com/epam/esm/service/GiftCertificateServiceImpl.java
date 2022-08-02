package com.epam.esm.service;


import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.GiftCertificatesTagsDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.jdbc.CertificateSearchCriteria;
import com.epam.esm.domain.Tag;
import com.epam.esm.exception.InvalidSortTypeException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.GiftCertificateBusinessModel;
import com.epam.esm.model.GiftCertificateMapper;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * {@inheritDoc}
 */
@Service
@EnableTransactionManagement
public class GiftCertificateServiceImpl implements GiftCertificateService {

    @Autowired
    GiftCertificateDAO giftCertificateDAO;

    @Autowired
    TagDAO tagDAO;

    @Autowired
    GiftCertificatesTagsDAO giftCertificatesTagsDAO;

    @Autowired
    GiftCertificateMapper giftCertificateMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public GiftCertificateBusinessModel findCertificateById(Long id) throws ResourceNotFoundException {
        var giftCertificate = giftCertificateDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No resource with id " + id));
        Set<Tag> tags = tagDAO.findAllTagsForByGiftCertificateId(id);
        return giftCertificateMapper.toGiftCertificateBusinessModel(giftCertificate, tags);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public GiftCertificateBusinessModel addNewCertificate(GiftCertificateBusinessModel certificate) {
        var giftCertificateToCreate = giftCertificateMapper.extractCertificateFromBusinessModel(certificate);
        Set<Tag> tagsToCreate = giftCertificateMapper.extractTagsFromCertificateBusinessModel(certificate);
        var giftCertificateCreated = giftCertificateDAO.create(giftCertificateToCreate);
        long certificateId = giftCertificateCreated.getId();
        Set<Tag> tagsCreated = tagsToCreate.stream()
                .map(tag -> tagDAO.create(tag))
                .collect(Collectors.toSet());
        tagsCreated.forEach(tag -> giftCertificatesTagsDAO.createAssociation(certificateId, tag.getId()));
        return giftCertificateMapper.toGiftCertificateBusinessModel(giftCertificateCreated, tagsCreated);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteById(Long id) {
        giftCertificateDAO.delete(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public GiftCertificateBusinessModel updateCertificate(GiftCertificateBusinessModel certificate)
            throws ResourceNotFoundException, InvalidFieldValueException {
        Long certificateID = certificate.getId();
        if (certificateID == null) {
            throw new InvalidFieldValueException("Certificate ID must be specified for update.");
        }
        var certificateToUpdate = giftCertificateMapper.extractCertificateFromBusinessModel(certificate);
        Set<Tag> newTags = giftCertificateMapper.extractTagsFromCertificateBusinessModel(certificate);
        Set<Tag> oldTags = tagDAO.findAllTagsForByGiftCertificateId(certificateID);
        var updatedCertificateWithNoTags = giftCertificateDAO.update(certificateToUpdate);
        associateNewTags(newTags, certificateID, oldTags);
        breakAssociationsWithRedundantTags(newTags, certificateID, oldTags);
        Set<Tag> tagsAfterUpdate = tagDAO.findAllTagsForByGiftCertificateId(certificateID);
        return giftCertificateMapper.toGiftCertificateBusinessModel(updatedCertificateWithNoTags, tagsAfterUpdate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public List<GiftCertificateBusinessModel> findAllMatching(Optional<CertificateSearchCriteria> searchCriteria)
            throws InvalidSortTypeException {
        if (searchCriteria.isEmpty()) {
            return giftCertificateDAO.findAll()
                    .stream()
                    .map(g -> giftCertificateMapper
                            .toGiftCertificateBusinessModel(g, tagDAO
                                    .findAllTagsForByGiftCertificateId(g.getId())))
                    .collect(Collectors.toList());
        }
        return giftCertificateDAO.findAllMatchingPrams(searchCriteria.get())
                .stream()
                .map(g -> giftCertificateMapper
                        .toGiftCertificateBusinessModel(g, tagDAO
                                .findAllTagsForByGiftCertificateId(g.getId())))
                .collect(Collectors.toList());
    }

    private void breakAssociationsWithRedundantTags(Set<Tag> newTags, long certificateID, Set<Tag> oldTags) {
        Set<Tag> tagsToBreakAssociation = Sets.difference(oldTags, newTags);
        if (!tagsToBreakAssociation.isEmpty()) {
            tagsToBreakAssociation.forEach(tag -> giftCertificatesTagsDAO.breakAssociation(certificateID, tag.getId()));
        }
    }

    private Set<Tag> associateNewTags(Set<Tag> newTags, long certificateID, Set<Tag> oldTags) {
        Set<Tag> tagsToAssociate = Sets.difference(newTags, oldTags);
        if (!tagsToAssociate.isEmpty()) {
            tagsToAssociate.forEach(tag -> tagDAO.create(tag));
            tagsToAssociate.forEach(tag -> giftCertificatesTagsDAO.createAssociation(certificateID, tag.getId()));
        }
        return tagsToAssociate;
    }
}
