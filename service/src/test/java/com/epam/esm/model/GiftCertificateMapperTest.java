package com.epam.esm.model;


import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.service.DataProvider;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GiftCertificateMapperTest {

    private final TagMapper tagMapper = new TagMapper();
    private final GiftCertificateMapper giftCertificateMapper = new GiftCertificateMapper(tagMapper);
    private final DataProvider dataProvider = new DataProvider();

    @Test
    void giftCertificateBusinessModelMappedFromGiftCertificateShouldHaveProperFieldsValues() {
        //GIVEN
        GiftCertificateBusinessModel expected = dataProvider.createGifCertificateBusinessModel();
        GiftCertificate giftCertificateBeMapped = dataProvider.createGifCertificate();
        //WHEN
        GiftCertificateBusinessModel actual = giftCertificateMapper
                .toGiftCertificateBusinessModelWithTags(giftCertificateBeMapped);
        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void giftCertificateMappedFromGiftCertificateBusinessModelShouldHaveProperFieldsValues() {
        //GIVEN
        GiftCertificate expected = dataProvider.createGifCertificate();
        GiftCertificateBusinessModel certificateToMap = dataProvider.createGifCertificateBusinessModel();
        //WHEN
        GiftCertificate actual = giftCertificateMapper.toGiftCertificateEntityModel(certificateToMap);
        //THEN
        assertEquals(expected, actual);
    }
}
