package com.epam.esm.controller;

import com.epam.esm.dao.jpa.CertificateSearchCriteria;
import com.epam.esm.exception.InvalidSortTypeException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.GiftCertificateBusinessModel;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.InvalidFieldValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * Process requests for Gift certificate resources.
 */
@RestController
@RequestMapping("/certificates")
class GiftCertificateController {
    @Autowired
    GiftCertificateService giftCertificateService;

    /**
     * Get Gift certificate resource by specified id.
     *
     * @param id of gift certificate
     * @return GiftCertificateBusinessModel
     */
    @GetMapping("/{id}")
    public GiftCertificateBusinessModel getCertificateByID(@PathVariable Long id) throws ResourceNotFoundException {
        return giftCertificateService.findCertificateById(id);
    }

    /**
     * Return list of gift certificates with tags. Results might be sorted by certificate name or creation date.
     *
     * @param searchCriteria
     * @return List of matching certificates with tags according to search criteria. All existing certificates will be
     * returned in case no search criteria or no request body provided.
     *
     * Request example:
     * <pre>
     * GET /api/certificates/ HTTP/1.1
     * Host: localhost:8080
     * Content-Type: application/json
     * Content-Length: 168
     *
     * {
     *     "tagName": "drinks",
     *     "certificateName": "Queen",
     *     "certificateDescription": "restaurant",
     *     "sortByNameType": "ASC",
     *     "sortByDateType": "DESC"
     * }
     * </pre>
     * @throws InvalidSortTypeException
     */
    @GetMapping
    public List<GiftCertificateBusinessModel> getAllMatching
    (@RequestBody(required = false) CertificateSearchCriteria searchCriteria) throws InvalidSortTypeException {
        return giftCertificateService.findAllMatching(Optional.ofNullable(searchCriteria));
    }

    /**
     * Delete gift certificate by specified id.
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public void removeCertificateByID(@PathVariable Long id) throws ResourceNotFoundException {
        giftCertificateService.deleteById(id);
    }

    /**
     * Add new gift certificate with related tags. If new tags are passed, they are being added to database.
     * Fields with null values may be omitted.
     *
     * @param certificate
     *
     * Request example:
     * <pre>
     * POST /api/certificates HTTP/1.1
     * Host: localhost:8080
     * Content-Type: application/json
     * Content-Length: 695
     *
     * {
     *     "id": null,
     *     "name": "TK Maxx",
     *     "description": "Valid online and in local stories.",
     *     "price": 250.00,
     *     "duration": 360,
     *     "createDate": null,
     *     "lastUpdateDate": null,
     *     "tags": [
     *         {
     *             "id": null,
     *             "name": "drinks"
     *         },
     *         {
     *             "id": null,
     *             "name": "food"
     *         },
     *         {
     *             "id": null,
     *             "name": "home&decor"
     *         },
     *         {
     *             "id": null,
     *             "name": "shoes"
     *         }
     *     ]
     * }
     * </pre>
     */
    @PostMapping
    public GiftCertificateBusinessModel addNewCertificate(@RequestBody GiftCertificateBusinessModel certificate) {
        return giftCertificateService.addNewCertificate(certificate);
    }

    /**
     * Updates gift certificate in two ways:
     * <ul>
     *     <li> if gift certificate is passed with tags, all other fields should be specified as well. If new tags
     *     are passed, they are being added to database.</li>
     *     <li>if certificate is passed without tags, only specified fields will be updated</li>
     *</ul>
     * Fields with null values may be omitted.
     *
     * @param certificate
     * @return GiftCertificateBusinessModel
     * @throws ResourceNotFoundException
     * @throws InvalidFieldValueException
     *
     * Request example:
     * <pre>
     *     PATCH /api/certificates/ HTTP/1.1
     * Host: localhost:8080
     * Content-Type: application/json
     * Content-Length: 607
     *
     * {
     *     "id": 4,
     *     "name": null,
     *     "description": null,
     *     "price": 90.00,
     *     "duration": 360,
     *     "createDate": "2020-10-05T14:24:11.056",
     *     "lastUpdateDate": "2021-08-29T06:12:15.156",
     *     "tags": [
     *         {
     *             "id": 5,
     *             "name": "birthday"
     *         },
     *         {
     *             "id": 9,
     *             "name": "mother's day"
     *         },
     *         {
     *             "id": 15,
     *             "name": "cinema"
     *         }
     *     ]
     * }
     * </pre>
     */
    @PatchMapping
    public GiftCertificateBusinessModel updateCertificate(@RequestBody GiftCertificateBusinessModel certificate)
            throws ResourceNotFoundException, InvalidFieldValueException {
        return giftCertificateService.updateCertificate(certificate);
    }
}
