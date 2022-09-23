package com.epam.esm.service;

import com.epam.esm.dao.jpa.CertificateSearchCriteria;
import com.epam.esm.dao.jpa.JPAGiftCertificateDAO;
import com.epam.esm.domain.GiftCertificate;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {
    private static final long ID = 1L;
    private static final Integer PAGE_NUMBER = 1;
    private static final Integer PAGE_SIZE = 10;
    private static final int NUMBER_OF_INVOCATIONS = 1;
    private static final String EMPTY_STRING = "";
    private final DataProvider dataProvider = new DataProvider();
    @Mock
    private JPAGiftCertificateDAO giftCertificateDAO;
    @Mock
    private TagService tagService;
    @Mock
    private GiftCertificateMapper giftCertificateMapper;

    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;

    private final GiftCertificateBusinessModel giftCertificateBusinessModel = dataProvider
            .createGifCertificateBusinessModel();
    private final GiftCertificate giftCertificate = dataProvider.createGifCertificate();

    @Test
    void shouldReturnProperGiftCertificateBusinessModelWhenResourceIsFound() throws ResourceNotFoundException {
        //GIVEN
        when(giftCertificateDAO.findById(ID)).thenReturn(Optional.of(giftCertificate));
        when(giftCertificateMapper.toGiftCertificateBusinessModelWithTags(giftCertificate))
                .thenReturn(giftCertificateBusinessModel);
        GiftCertificateBusinessModel expected = dataProvider.createGifCertificateBusinessModel();
        //WHEN
        var actual = giftCertificateService.find(ID);
        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnProperGiftCertificateBusinessModelWhenSuccessfullyAdded() {
        //GIVEN
        GiftCertificateBusinessModel expected = giftCertificateBusinessModel;
        //WHEN
        when(giftCertificateMapper.toGiftCertificateEntityModel(giftCertificateBusinessModel))
                .thenReturn(giftCertificate);
        when(giftCertificateDAO.create(giftCertificate)).thenReturn(giftCertificate);
        when(giftCertificateMapper.toGiftCertificateBusinessModelWithTags(giftCertificate))
                .thenReturn(giftCertificateBusinessModel);
        GiftCertificateBusinessModel actual = giftCertificateService.create(giftCertificateBusinessModel);
        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void deleteMethodShouldBeCalledWhileCertificateIsBeingRemoved() throws ResourceNotFoundException {
        //GIVEN
        //WHEN
        giftCertificateService.delete(ID);
        //THEN
        verify(giftCertificateDAO, Mockito.times(NUMBER_OF_INVOCATIONS)).delete(ID);
    }

    @Test
    void updateShouldThrowWhenCertificateWithNullIDIsPassed() {
        //GIVEN
        GiftCertificateBusinessModel certificate = new GiftCertificateBusinessModel();
        //WHEN
        Exception exception = assertThrows(InvalidFieldValueException.class,
                () -> giftCertificateService.update(certificate));
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
        giftCertificateService.findAllMatching(searchCriteria, PAGE_NUMBER, PAGE_SIZE);
        //THEN
        verify(giftCertificateDAO, Mockito.times(NUMBER_OF_INVOCATIONS)).findAll(PAGE_NUMBER, PAGE_SIZE);
    }

    @Test
    void findAllMatchingParamsMethodShouldBeCalledWhenSearchParamPassed() throws InvalidSortTypeException {
        //GIVEN
        Optional<CertificateSearchCriteria> searchCriteria =
                Optional.of(new CertificateSearchCriteria(List.of("food"),EMPTY_STRING,EMPTY_STRING,"ASC",EMPTY_STRING));
        //WHEN
        giftCertificateService.findAllMatching(searchCriteria, PAGE_NUMBER,PAGE_SIZE);
        //THEN
        verify(giftCertificateDAO, Mockito.times(NUMBER_OF_INVOCATIONS)).findAllMatchingPrams(
                searchCriteria.get(), PAGE_NUMBER, PAGE_SIZE);
    }
}
