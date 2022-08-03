package com.epam.esm.service;

import com.epam.esm.dao.GiftCertificatesTagsDAO;
import com.epam.esm.dao.jdbc.CertificateSearchCriteria;
import com.epam.esm.dao.jdbc.JPATagDAO;
import com.epam.esm.dao.jdbc.JPAGiftCertificateDAO;
import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Tag;
import com.epam.esm.exception.InvalidSortTypeException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.GiftCertificateBusinessModel;
import com.epam.esm.model.GiftCertificateMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {
    private static final long ID = 1L;
    private final DataProvider dataProvider = new DataProvider();

    @Mock
    private JPAGiftCertificateDAO giftCertificateDAO;

    @Mock
    private JPATagDAO tagDAO;

    @Mock
    private GiftCertificateMapper giftCertificateMapper;

    @Mock
    private GiftCertificatesTagsDAO giftCertificatesTagsDAO;

    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;

    private final GiftCertificateBusinessModel giftCertificateBusinessModel = dataProvider
            .createGifCertificateBusinessModel();
    private final GiftCertificate giftCertificate = dataProvider.createGifCertificate();
    private final Set<Tag> tags = dataProvider.createTagsSet();

    private final Tag tag = dataProvider.createTestTag();

    @Test
    void shouldReturnProperGiftCertificateBusinessModelWhenResourceIsFound() throws ResourceNotFoundException {
        //GIVEN
        when(giftCertificateDAO.findById(ID)).thenReturn(Optional.of(giftCertificate));
        when(tagDAO.findAllTagsForByGiftCertificateId(ID)).thenReturn(tags);
        when(giftCertificateMapper.toGiftCertificateBusinessModel(giftCertificate, tags))
                .thenReturn(giftCertificateBusinessModel);
        GiftCertificateBusinessModel expected = dataProvider.createGifCertificateBusinessModel();
        //WHEN
        var actual = giftCertificateService.findCertificateById(ID);
        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnProperGiftCertificateBusinessModelWhenSuccessfullyAdded() {
        //GIVEN
        GiftCertificateBusinessModel expected = giftCertificateBusinessModel;
        expected.setTags(Set.of(dataProvider.createTestTagBusinessModel()));
        Set<Tag> tagsToAdd = Set.of(tag);
        //WHEN
        when(giftCertificateMapper.extractCertificateFromBusinessModel(giftCertificateBusinessModel))
                .thenReturn(giftCertificate);
        when(giftCertificateMapper.extractTagsFromCertificateBusinessModel(giftCertificateBusinessModel))
                .thenReturn(tagsToAdd);
        when(giftCertificateDAO.create(giftCertificate)).thenReturn(giftCertificate);
        when(tagDAO.create(tag)).thenReturn(tag);
        when(giftCertificateMapper.toGiftCertificateBusinessModel(giftCertificate, tagsToAdd))
                .thenReturn(giftCertificateBusinessModel);
        GiftCertificateBusinessModel actual = giftCertificateService.addNewCertificate(giftCertificateBusinessModel);
        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void deleteMethodShouldBeCalledWhileCertificateIsBeingRemoved() throws ResourceNotFoundException {
        //GIVEN
        //WHEN
        giftCertificateService.deleteById(ID);
        //THEN
        verify(giftCertificateDAO, Mockito.times(1)).delete(ID);
    }

    @Test
    void updateShouldThrowWhenCertificateWithNullIDIsPassed()
            throws InvalidFieldValueException, ResourceNotFoundException {
        //GIVEN
        GiftCertificateBusinessModel certificate = new GiftCertificateBusinessModel();
        //WHEN
        Exception exception = assertThrows(InvalidFieldValueException.class,
                () -> giftCertificateService.updateCertificate(certificate));
        String expectedMessage = "Certificate ID must be specified for update.";
        String actualMessage = exception.getMessage();
        //THEN
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void findAllMethodShouldBeCalledWhenNoSearchParamPassed() throws InvalidSortTypeException {
        //GIVEN
        Optional<CertificateSearchCriteria> searchCriteria = Optional.empty();
        //WHEN
        giftCertificateService.findAllMatching(searchCriteria);
        //THEN
        verify(giftCertificateDAO, Mockito.times(1)).findAll();
    }

    @Test
    void findAllMatchingParamsMethodShouldBeCalledWhenSearchParamPassed() throws InvalidSortTypeException {
        //GIVEN
        Optional<CertificateSearchCriteria> searchCriteria =
                Optional.of(new CertificateSearchCriteria("food","","","ASC",""));
        //WHEN
        giftCertificateService.findAllMatching(searchCriteria);
        //THEN
        verify(giftCertificateDAO, Mockito.times(1)).findAllMatchingPrams(searchCriteria.get());
    }

}
