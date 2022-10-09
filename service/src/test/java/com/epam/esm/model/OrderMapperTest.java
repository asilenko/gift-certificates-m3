package com.epam.esm.model;

import com.epam.esm.dataprovider.DataProvider;
import com.epam.esm.domain.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderMapperTest {


    private final TagMapper tagMapper = new TagMapper();
    private final GiftCertificateMapper giftCertificateMapper = new GiftCertificateMapper(tagMapper);
    private final OrderMapper orderMapper = new OrderMapper(giftCertificateMapper);
    private final DataProvider dataProvider = new DataProvider();

    @Test
    void orderModelModelMappedFromOrderShouldHaveProperFieldsValues() {
        //GIVEN
        OrderModel expected = dataProvider.createOrderModelWithCertificate();
        Order orderToBeMapped = dataProvider.createOrderWithCertificate();
        //WHEN
        OrderModel actual = orderMapper.toOrderBusinessModel(orderToBeMapped);
        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void orderMappedFromOrderModelShouldHaveProperFieldsValues() {
        //GIVEN
        Order expected = dataProvider.createOrderWithCertificate();
        OrderModel orderToMap = dataProvider.createOrderModelWithCertificate();
        //WHEN
        Order actual = orderMapper.toOrder(orderToMap);
        //THEN
        assertEquals(expected, actual);
    }
}
