package com.epam.esm.dataprovider;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Order;
import com.epam.esm.domain.Tag;
import com.epam.esm.domain.User;
import com.epam.esm.model.GiftCertificateModel;
import com.epam.esm.model.OrderModel;
import com.epam.esm.model.TagModel;
import com.epam.esm.model.UserModel;

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

    public TagModel createTagModel() {
        TagModel tagModel = new TagModel();
        tagModel.setName(NAME);
        tagModel.setId(ID);
        return tagModel;
    }

    public GiftCertificate createGifCertificateWithTags() {
        GiftCertificate giftCertificate = createBaseGiftCertificate();
        giftCertificate.setTags(createTagsSet());
        return giftCertificate;
    }

    public GiftCertificate createBaseGiftCertificate() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(ID);
        giftCertificate.setName(NAME);
        giftCertificate.setDescription(DESCRIPTION);
        giftCertificate.setPrice(PRICE);
        giftCertificate.setDuration(DURATION);
        giftCertificate.setCreateDate(CREATE_DATE_LDT);
        giftCertificate.setLastUpdateDate(LAST_UPDATE_DATE_LDT);
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

    public Set<TagModel> createTagsModelSet(){
        Set<TagModel> tags = new HashSet<>();
        for (long i = 0L; i < SET_QUANTITY; i++) {
            TagModel tag = new TagModel();
            tag.setId(i);
            tag.setName(NAME + i);
            tags.add(tag);
        }
        return tags;
    }

    public GiftCertificateModel createGifCertificateModelWithTags() {
        GiftCertificateModel giftCertificateModel = createBaseGiftCertificateModel();
        Set<TagModel> tags = createTagsModelSet();
        giftCertificateModel.setTags(tags);
        return giftCertificateModel;
    }

    public GiftCertificateModel createBaseGiftCertificateModel() {
        GiftCertificateModel giftCertificateModel = new GiftCertificateModel();
        giftCertificateModel.setId(ID);
        giftCertificateModel.setName(NAME);
        giftCertificateModel.setDescription(DESCRIPTION);
        giftCertificateModel.setPrice(PRICE);
        giftCertificateModel.setDuration(DURATION);
        giftCertificateModel.setCreateDate(CREATE_DATE);
        giftCertificateModel.setLastUpdateDate(LAST_UPDATE_DATE);
        return giftCertificateModel;
    }

    public OrderModel createOrderModelWithCertificate() {
        OrderModel orderModel = new OrderModel();
        orderModel.setId(ID);
        orderModel.setCost(PRICE);
        orderModel.setPurchaseDate(CREATE_DATE_LDT);
        orderModel.setUserID(ID);
        orderModel.setGiftCertificate(createBaseGiftCertificateModel());
        return orderModel;
    }

    public Order createOrderWithCertificate() {
        Order order = new Order();
        order.setId(ID);
        order.setCost(PRICE);
        order.setPurchaseDate(CREATE_DATE_LDT);
        order.setUserID(ID);
        order.setGiftCertificate(createBaseGiftCertificate());
        return order;
    }

    public UserModel createUserModel() {
        UserModel userModel = new UserModel();
        userModel.setId(ID);
        userModel.setName(NAME);
        return userModel;
    }

    public User createUser() {
        User user = new User();
        user.setId(ID);
        user.setName(NAME);
        return user;
    }
}
