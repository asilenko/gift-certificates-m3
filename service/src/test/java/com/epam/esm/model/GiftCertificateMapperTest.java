package com.epam.esm.model;


import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Tag;
import com.epam.esm.service.DataProvider;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GiftCertificateMapperTest {

    private final TagMapper tagMapper = new TagMapper();
    private final GiftCertificateMapper giftCertificateMapper = new GiftCertificateMapper(tagMapper);
    private final DataProvider dataProvider = new DataProvider();

    @Test
    void giftCertificateBusinessModelMappedFromGiftCertificateShouldHaveProperFieldsValues() {
        //GIVEN
        GiftCertificateBusinessModel expected = dataProvider.createGifCertificateBusinessModel();
        Set<Tag> tagsToAdd = dataProvider.createTagsSet();
        GiftCertificate giftCertificateBeMapped = dataProvider.createGifCertificate();
        //WHEN
        GiftCertificateBusinessModel actual = giftCertificateMapper
                .toGiftCertificateBusinessModel(giftCertificateBeMapped, tagsToAdd);
        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void extractCertificateFromBusinessModelShouldReturnProperCertificate() {
        //GIVEN
        GiftCertificate expected = dataProvider.createGifCertificate();
        GiftCertificateBusinessModel certificateBMToExtract = dataProvider.createGifCertificateBusinessModel();
        //WHEN
        GiftCertificate actual = giftCertificateMapper.extractCertificateFromBusinessModel(certificateBMToExtract);
        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void extractTagsFromCertificateBusinessModelShouldReturnProperTagsList() {
        //GIVEN
        Set<Tag> expected = dataProvider.createTagsSet();
        GiftCertificateBusinessModel certificateBMToExtract = dataProvider.createGifCertificateBusinessModel();
        //WHEN
        Set<Tag> actual = giftCertificateMapper.extractTagsFromCertificateBusinessModel(certificateBMToExtract);
        //THEN
        assertEquals(expected, actual);
    }
}
