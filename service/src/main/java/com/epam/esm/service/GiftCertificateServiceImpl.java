package com.epam.esm.service;


import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.jpa.CertificateSearchCriteria;
import com.epam.esm.exception.InvalidSortTypeException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.GiftCertificateModel;
import com.epam.esm.model.GiftCertificateMapper;
import com.epam.esm.pagination.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * {@inheritDoc}
 */
@Service
@Transactional
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateDAO giftCertificateDAO;
    private final TagService tagService;
    private final GiftCertificateMapper giftCertificateMapper;

    public GiftCertificateServiceImpl(GiftCertificateDAO giftCertificateDAO, TagService tagService,
                                      GiftCertificateMapper giftCertificateMapper) {
        this.giftCertificateDAO = giftCertificateDAO;
        this.tagService = tagService;
        this.giftCertificateMapper = giftCertificateMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GiftCertificateModel find(Long id) throws ResourceNotFoundException {
        var giftCertificate = giftCertificateDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No resource with id " + id));
        return giftCertificateMapper.toGiftCertificateBusinessModelWithTags(giftCertificate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GiftCertificateModel create(GiftCertificateModel certificate) {
        prepareTags(certificate);
        var giftCertificateToCreate = giftCertificateMapper.toGiftCertificateEntityModel(certificate);
        giftCertificateToCreate.setId(null);
        var giftCertificateCreated = giftCertificateDAO.create(giftCertificateToCreate);
        return giftCertificateMapper.toGiftCertificateBusinessModelWithTags(giftCertificateCreated);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        giftCertificateDAO.delete(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GiftCertificateModel update(GiftCertificateModel certificate)
            throws ResourceNotFoundException, InvalidFieldValueException {
        Long certificateID = certificate.getId();
        if (certificateID == null) {
            throw new InvalidFieldValueException("Certificate ID must be specified for update.");
        }
        prepareTags(certificate);
        var certificateToUpdate = giftCertificateMapper.toGiftCertificateEntityModel(certificate);
        var updatedGCEM = giftCertificateDAO.update(certificateToUpdate);
        return giftCertificateMapper.toGiftCertificateBusinessModelWithTags(updatedGCEM);
    }

    private void prepareTags(GiftCertificateModel certificate) {
        var preparedTags = certificate.getTags()
                .stream()
                .map(tagService::create)
                .collect(Collectors.toSet());
        certificate.setTags(preparedTags);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<GiftCertificateModel> findAllMatching(Optional<CertificateSearchCriteria> searchCriteria,
                                                      Integer pageNumber, Integer pageSize)
            throws InvalidSortTypeException {
        if (searchCriteria.isEmpty() || !searchCriteria.get().hasValues()) {
            var total = giftCertificateDAO.getTotal();
            var certificates =  giftCertificateDAO.findAll(pageNumber, pageSize)
                    .stream()
                    .map(giftCertificateMapper::toGiftCertificateBusinessModelWithTags)
                    .collect(Collectors.toList());
            return new Page<>(pageNumber, pageSize, total, certificates);
        }
        var total = giftCertificateDAO.getTotal(searchCriteria.get());
        var certificates =  giftCertificateDAO.findAllMatchingPrams(searchCriteria.get(), pageNumber, pageSize)
                .stream()
                .map(giftCertificateMapper::toGiftCertificateBusinessModelWithTags)
                .collect(Collectors.toList());
        return new Page<>(pageNumber, pageSize, total, certificates);
    }
}
