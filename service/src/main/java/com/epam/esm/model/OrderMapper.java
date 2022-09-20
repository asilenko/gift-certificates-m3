package com.epam.esm.model;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Maps Order from persistence layer to OrderBusinessModel and vice versa.
 *
 * @see com.epam.esm.domain.Order
 * @see OrderBusinessModel
 */
@Component
public class OrderMapper {

    private final GiftCertificateMapper giftCertificateMapper;


    public OrderMapper(GiftCertificateMapper giftCertificateMapper) {
        this.giftCertificateMapper = giftCertificateMapper;
    }

    /**
     * Maps BusinessModel to EntityModel.
     *
     * @param orderBusinessModel
     * @return Order
     * @see Order
     * @see OrderBusinessModel
     */
    public Order toOrder(OrderBusinessModel orderBusinessModel) {
        Order order = new Order();
        order.setId(orderBusinessModel.getId());
        order.setCost(orderBusinessModel.getCost());
        order.setPurchaseDate(orderBusinessModel.getPurchaseDate());
        order.setUserID(orderBusinessModel.getUserID());
        order.setGiftCertificates(extractCertificates(orderBusinessModel));
        return order;
    }

    private List<GiftCertificate> extractCertificates(OrderBusinessModel orderBusinessModel) {
        return orderBusinessModel.getGiftCertificates()
                .stream()
                .map(giftCertificateMapper::toGiftCertificateEntityModel)
                .collect(Collectors.toList());
    }

    /**
     * Maps EntityModel to BusinessModel.
     *
     * @param order
     * @return OrderBusinessModel
     * @see Order
     * @see OrderBusinessModel
     */
    public OrderBusinessModel toOrderBusinessModel(Order order) {
        OrderBusinessModel orderBusinessModel = new OrderBusinessModel();
        orderBusinessModel.setId(order.getId());
        orderBusinessModel.setCost(order.getCost());
        orderBusinessModel.setPurchaseDate(order.getPurchaseDate());
        orderBusinessModel.setUserID(order.getUserID());
        orderBusinessModel.setGiftCertificates(extractCertificates(order));
        return orderBusinessModel;
    }

    private List<GiftCertificateBusinessModel> extractCertificates(Order order) {
        return order.getGiftCertificates()
                .stream()
                .map(giftCertificateMapper::toGiftCertificateBusinessModel)
                .collect(Collectors.toList());
    }
}
