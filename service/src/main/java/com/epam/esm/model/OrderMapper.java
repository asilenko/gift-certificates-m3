package com.epam.esm.model;

import com.epam.esm.domain.Order;
import org.springframework.stereotype.Component;

/**
 * Maps Order from persistence layer to OrderModel and vice versa.
 *
 * @see com.epam.esm.domain.Order
 * @see OrderModel
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
     * @param orderModel
     * @return Order
     * @see Order
     * @see OrderModel
     */
    public Order toOrder(OrderModel orderModel) {
        Order order = new Order();
        order.setId(orderModel.getId());
        order.setCost(orderModel.getCost());
        order.setPurchaseDate(orderModel.getPurchaseDate());
        order.setUserID(orderModel.getUserID());
        order.setGiftCertificate(
                giftCertificateMapper.toGiftCertificateEntityModel(orderModel.getGiftCertificate()));
        return order;
    }

    /**
     * Maps EntityModel to BusinessModel.
     *
     * @param order
     * @return OrderModel
     * @see Order
     * @see OrderModel
     */
    public OrderModel toOrderBusinessModel(Order order) {
        OrderModel orderModel = new OrderModel();
        orderModel.setId(order.getId());
        orderModel.setCost(order.getCost());
        orderModel.setPurchaseDate(order.getPurchaseDate());
        orderModel.setUserID(order.getUserID());
        orderModel.
                setGiftCertificate(giftCertificateMapper.toGiftCertificateBusinessModel(order.getGiftCertificates()));
        return orderModel;
    }
}
