package com.epam.esm.service;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Tag;
import com.epam.esm.model.GiftCertificateBusinessModel;
import com.epam.esm.model.TagBusinessModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Provides data for tests.
 */
public class DataProvider {

    private static final String NAME = "name";
    private static final long ID = 1L;
    private static final int SET_QUANTITY = 3;
    private static final String DESCRIPTION = "Certificate description";
    private static final BigDecimal PRICE = new BigDecimal("10.10");
    private static final int DURATION = 10;
    private static final String CREATE_DATE = "2020-10-05T14:24:11.056";
    private static final LocalDateTime CREATE_DATE_LDT = LocalDateTime.parse(CREATE_DATE);
    private static final String LAST_UPDATE_DATE = "2021-11-01T14:24:11.011";
    private static final LocalDateTime LAST_UPDATE_DATE_LDT = LocalDateTime.parse(LAST_UPDATE_DATE);


    public Tag createTestTag() {
        Tag tag = new Tag();
        tag.setName(NAME);
        tag.setId(ID);
        return tag;
    }

    public TagBusinessModel createTestTagBusinessModel() {
        TagBusinessModel tagBusinessModel = new TagBusinessModel();
        tagBusinessModel.setName(NAME);
        tagBusinessModel.setId(ID);
        return tagBusinessModel;
    }

    public GiftCertificate createGifCertificate() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(ID);
        giftCertificate.setName(NAME);
        giftCertificate.setDescription(DESCRIPTION);
        giftCertificate.setPrice(PRICE);
        giftCertificate.setDuration(DURATION);
        giftCertificate.setCreateDate(CREATE_DATE_LDT);
        giftCertificate.setLastUpdateDate(LAST_UPDATE_DATE_LDT);
        giftCertificate.setTags(createTagsSet());
        return giftCertificate;
    }

    public Set<Tag> createTagsSet() {
        Set<Tag> tags = new HashSet<>();
        for (int i = 0; i < SET_QUANTITY; i++) {
            Tag tag = new Tag();
            tag.setId((long) i);
            tag.setName(NAME + i);
            tags.add(tag);
        }
        return tags;
    }

    public Set<TagBusinessModel> createTagsBMSet(){
        Set<TagBusinessModel> tags = new HashSet<>();
        for (int i = 0; i < SET_QUANTITY; i++) {
            TagBusinessModel tag = new TagBusinessModel();
            tag.setId(i);
            tag.setName(NAME + i);
            tags.add(tag);
        }
        return tags;
    }

    public GiftCertificateBusinessModel createGifCertificateBusinessModel() {
        GiftCertificateBusinessModel giftCertificateBusinessModel = new GiftCertificateBusinessModel();
        giftCertificateBusinessModel.setId(ID);
        giftCertificateBusinessModel.setName(NAME);
        giftCertificateBusinessModel.setDescription(DESCRIPTION);
        giftCertificateBusinessModel.setPrice(PRICE);
        giftCertificateBusinessModel.setDuration(DURATION);
        giftCertificateBusinessModel.setCreateDate(CREATE_DATE);
        giftCertificateBusinessModel.setLastUpdateDate(LAST_UPDATE_DATE);

        Set<TagBusinessModel> tags = createTagsBMSet();

        giftCertificateBusinessModel.setTags(tags);
        return giftCertificateBusinessModel;
    }
}
