package com.epam.esm.service;


import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.jdbc.CertificateSearchCriteria;
import com.epam.esm.exception.InvalidSortTypeException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.GiftCertificateBusinessModel;
import com.epam.esm.model.GiftCertificateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * {@inheritDoc}
 */
@Service
@Transactional
public class GiftCertificateServiceImpl implements GiftCertificateService {

    @Autowired
    GiftCertificateDAO giftCertificateDAO;

    @Autowired
    TagDAO tagDAO;

    @Autowired
    GiftCertificateMapper giftCertificateMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public GiftCertificateBusinessModel findCertificateById(Long id) throws ResourceNotFoundException {
        var giftCertificate = giftCertificateDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No resource with id " + id));
        return giftCertificateMapper.toGiftCertificateBusinessModel(giftCertificate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GiftCertificateBusinessModel addNewCertificate(GiftCertificateBusinessModel certificate) {
        var giftCertificateToCreate = giftCertificateMapper.toGiftCertificateEntityModel(certificate);
        var tags = giftCertificateToCreate.getTags()
                .stream()
                .map(t -> tagDAO.create(t))
                .collect(Collectors.toSet());
        giftCertificateToCreate.setTags(tags);
        var giftCertificateCreated = giftCertificateDAO.create(giftCertificateToCreate);
        return giftCertificateMapper.toGiftCertificateBusinessModel(giftCertificateCreated);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteById(Long id) throws ResourceNotFoundException {
        giftCertificateDAO.delete(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GiftCertificateBusinessModel updateCertificate(GiftCertificateBusinessModel certificate)
            throws ResourceNotFoundException, InvalidFieldValueException {
        Long certificateID = certificate.getId();
        if (certificateID == null) {
            throw new InvalidFieldValueException("Certificate ID must be specified for update.");
        }
        var certificateToUpdate = giftCertificateMapper.toGiftCertificateEntityModel(certificate);
        var tags = certificateToUpdate.getTags()
                .stream()
                .map(t -> tagDAO.create(t))
                .collect(Collectors.toSet());
        certificateToUpdate.setTags(tags);
        var updatedGCEM = giftCertificateDAO.update(certificateToUpdate);
        return giftCertificateMapper.toGiftCertificateBusinessModel(updatedGCEM);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GiftCertificateBusinessModel> findAllMatching(Optional<CertificateSearchCriteria> searchCriteria)
            throws InvalidSortTypeException {
        if (searchCriteria.isEmpty()) {
            return giftCertificateDAO.findAll()
                    .stream()
                    .map(g -> giftCertificateMapper.toGiftCertificateBusinessModel(g))
                    .collect(Collectors.toList());
        }
        return giftCertificateDAO.findAllMatchingPrams(searchCriteria.get())
                .stream()
                .map(g -> giftCertificateMapper.toGiftCertificateBusinessModel(g))
                .collect(Collectors.toList());
    }
}
