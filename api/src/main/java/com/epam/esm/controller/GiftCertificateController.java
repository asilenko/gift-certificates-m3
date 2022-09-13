package com.epam.esm.controller;

import com.epam.esm.dao.jpa.CertificateSearchCriteria;
import com.epam.esm.exception.InvalidSortTypeException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.hateoas.GiftCertificateLinker;
import com.epam.esm.model.GiftCertificateBusinessModel;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.InvalidFieldValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * Process requests for Gift certificate resources.
 */
@RestController
@RequestMapping("/certificates")
public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;
    private final GiftCertificateLinker giftCertificateLinker;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService, GiftCertificateLinker giftCertificateLinker) {
        this.giftCertificateService = giftCertificateService;
        this.giftCertificateLinker = giftCertificateLinker;
    }

    /**
     * Get Gift certificate resource by specified id.
     *
     * @param id of gift certificate
     * @return GiftCertificateBusinessModel
     */
    @GetMapping("/{id}")
    public ResponseEntity<GiftCertificateBusinessModel> getById(@PathVariable Long id) throws ResourceNotFoundException {
        var giftCertificate = giftCertificateService.findCertificateById(id);
        giftCertificateLinker.addLink(giftCertificate);
        return new ResponseEntity<>(giftCertificate, HttpStatus.OK);
    }

    /**
     * Return list of gift certificates with tags. Results might be sorted by certificate name or creation date.
     *
     * Output can be narrowed by search criteria:
     * <ul>
     *     <li> list of tags related to gift certificate,
     *     <li> gift certificate name or part of it,
     *     <li> gift certificate description or part of it.
     * </ul>
     * @return List of matching certificates with tags according to search criteria. All existing certificates will be
     * returned in case no search criteria were provided.
     * <p>
     * Request example for search with few params specified:
     * GET /certificates/?name=food&sortByDateType=DESC HTTP/1.1
     * Host: localhost:8080
     * <p>
     * Request example for search with all params specified:
     * GET /certificates/?tags=food,handmade&name=Queen&description=restaurant&sortByNameType=ASC&sortByDateType=DESC&pageNumber=1&pageSize=2 HTTP/1.1
     * Host: localhost:8080
     * <p>
     * Request example to find all gift certificates:
     * GET /certificates/ HTTP/1.1
     * Host: localhost:8080
     * <p>
     * @throws InvalidSortTypeException
     */
    @GetMapping
    public ResponseEntity<CollectionModel<GiftCertificateBusinessModel>> getAllMatching
    (@RequestParam(required = false) List<String> tags,
     @RequestParam(required = false) String name,
     @RequestParam(required = false) String description,
     @RequestParam(required = false) String sortByNameType,
     @RequestParam(required = false) String sortByDateType,
     @RequestParam(defaultValue = "1") Integer pageNumber,
     @RequestParam(defaultValue = "20") Integer pageSize)
            throws InvalidSortTypeException, ResourceNotFoundException {
        CertificateSearchCriteria searchCriteria = new CertificateSearchCriteria(tags, name, description,
                sortByNameType, sortByDateType);
        var page = giftCertificateService.findAllMatching(
                Optional.of(searchCriteria), pageNumber, pageSize);
        CollectionModel<GiftCertificateBusinessModel> collectionModel = giftCertificateLinker.addLinks(page, tags, name,
                description, sortByNameType, sortByDateType);
        return ResponseEntity.ok(collectionModel);
    }

    /**
     * Delete gift certificate by specified id.
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteByID(@PathVariable Long id) throws ResourceNotFoundException {
        giftCertificateService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
    public ResponseEntity<GiftCertificateBusinessModel> create(@RequestBody GiftCertificateBusinessModel certificate) {
        var giftCertificate = giftCertificateService.addNewCertificate(certificate);
        giftCertificateLinker.addLink(giftCertificate);
        return new ResponseEntity<>(giftCertificate, HttpStatus.CREATED);
    }

    /**
     * Updates gift certificate in two ways:
     * <ul>
     *     <li> if gift certificate is passed with tags, all other fields should be specified as well. If new tags
     *     are passed, they are being added to database.</li>
     *     <li>if certificate is passed without tags, only specified fields will be updated</li>
     * </ul>
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
    public ResponseEntity<GiftCertificateBusinessModel> updateCertificate(@RequestBody GiftCertificateBusinessModel certificate)
            throws ResourceNotFoundException, InvalidFieldValueException {
        var giftCertificate = giftCertificateService.updateCertificate(certificate);
        giftCertificateLinker.addLink(giftCertificate);
        return new ResponseEntity<>(giftCertificate, HttpStatus.OK);
    }
}
