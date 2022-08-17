package com.epam.esm.service;

import com.epam.esm.dao.jdbc.CertificateSearchCriteria;
import com.epam.esm.dao.jdbc.JPAGiftCertificateDAO;
import com.epam.esm.dao.jdbc.JPATagDAO;
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
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

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

    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;

    private final GiftCertificateBusinessModel giftCertificateBusinessModel = dataProvider
            .createGifCertificateBusinessModel();
    private final GiftCertificate giftCertificate = dataProvider.createGifCertificate();

    @Test
    void shouldReturnProperGiftCertificateBusinessModelWhenResourceIsFound() throws ResourceNotFoundException {
        //GIVEN
        when(giftCertificateDAO.findById(ID)).thenReturn(Optional.of(giftCertificate));
        when(giftCertificateMapper.toGiftCertificateBusinessModel(giftCertificate))
                .thenReturn(giftCertificateBusinessModel);
        GiftCertificateBusinessModel expected = dataProvider.createGifCertificateBusinessModel();
        //WHEN
        var actual = giftCertificateService.findCertificateById(ID);
        //THEN
        assertEquals(expected, actual);
    }

    @Test
    @DirtiesContext
    void shouldReturnProperGiftCertificateBusinessModelWhenSuccessfullyAdded() {
        //GIVEN
        GiftCertificateBusinessModel expected = giftCertificateBusinessModel;
        //WHEN
        when(giftCertificateMapper.toGiftCertificateEntityModel(giftCertificateBusinessModel))
                .thenReturn(giftCertificate);
        when(giftCertificateDAO.create(giftCertificate)).thenReturn(giftCertificate);
        when(giftCertificateMapper.toGiftCertificateBusinessModel(giftCertificate))
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
    void updateShouldThrowWhenCertificateWithNullIDIsPassed() {
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
