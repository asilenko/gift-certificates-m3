package com.epam.esm.model;


import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.dataprovider.DataProvider;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GiftCertificateMapperTest {

    private final TagMapper tagMapper = new TagMapper();
    private final GiftCertificateMapper giftCertificateMapper = new GiftCertificateMapper(tagMapper);
    private final DataProvider dataProvider = new DataProvider();

    @Test
    void giftCertificateBusinessModelMappedFromGiftCertificateShouldHaveProperFieldsValues() {
        //GIVEN
        GiftCertificateBusinessModel expected = dataProvider.createGifCertificateBusinessModelWithTags();
        GiftCertificate giftCertificateBeMapped = dataProvider.createGifCertificateWithTags();
        //WHEN
        GiftCertificateBusinessModel actual = giftCertificateMapper
                .toGiftCertificateBusinessModelWithTags(giftCertificateBeMapped);
        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void giftCertificateMappedFromGiftCertificateBusinessModelShouldHaveProperFieldsValues() {
        //GIVEN
        GiftCertificate expected = dataProvider.createGifCertificateWithTags();
        GiftCertificateBusinessModel certificateToMap = dataProvider.createGifCertificateBusinessModelWithTags();
        //WHEN
        GiftCertificate actual = giftCertificateMapper.toGiftCertificateEntityModel(certificateToMap);
        //THEN
        assertEquals(expected, actual);
    }
}
