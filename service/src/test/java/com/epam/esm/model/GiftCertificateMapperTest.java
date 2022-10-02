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
    void giftCertificateModelMappedFromGiftCertificateShouldHaveProperFieldsValues() {
        //GIVEN
        GiftCertificateModel expected = dataProvider.createGifCertificateModelWithTags();
        GiftCertificate giftCertificateBeMapped = dataProvider.createGifCertificateWithTags();
        //WHEN
        GiftCertificateModel actual = giftCertificateMapper
                .toGiftCertificateBusinessModelWithTags(giftCertificateBeMapped);
        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void giftCertificateMappedFromGiftCertificateModelShouldHaveProperFieldsValues() {
        //GIVEN
        GiftCertificate expected = dataProvider.createGifCertificateWithTags();
        GiftCertificateModel certificateToMap = dataProvider.createGifCertificateModelWithTags();
        //WHEN
        GiftCertificate actual = giftCertificateMapper.toGiftCertificateEntityModel(certificateToMap);
        //THEN
        assertEquals(expected, actual);
    }
}
