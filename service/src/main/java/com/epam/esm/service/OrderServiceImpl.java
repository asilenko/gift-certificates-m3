package com.epam.esm.service;

import com.epam.esm.dao.OrderDAO;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.GiftCertificateBusinessModel;
import com.epam.esm.model.OrderBusinessModel;
import com.epam.esm.model.OrderMapper;
import com.epam.esm.pagination.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * {@inheritDoc}
 */
@Service
@Transactional
class OrderServiceImpl implements OrderService {
    private final OrderMapper orderMapper;
    private final OrderDAO orderDAO;
    private final GiftCertificateService giftCertificateService;
    private final UserService userService;

    public OrderServiceImpl(OrderMapper orderMapper, OrderDAO orderDAO, GiftCertificateService giftCertificateService,
                            UserService userService) {
        this.orderMapper = orderMapper;
        this.orderDAO = orderDAO;
        this.giftCertificateService = giftCertificateService;
        this.userService = userService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderBusinessModel find(Long id) throws ResourceNotFoundException {
        return orderMapper.toOrderBusinessModel(orderDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No resource with id " + id)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderBusinessModel placeOrder(Long userId, Long certificateId)
            throws ResourceNotFoundException {
        userService.find(userId);
        GiftCertificateBusinessModel certificate = giftCertificateService.find(certificateId);
        BigDecimal cost = certificate.getPrice();
        OrderBusinessModel order = new OrderBusinessModel();
        order.setGiftCertificate(certificate);
        order.setUserID(userId);
        order.setCost(cost);
        order.setPurchaseDate(LocalDateTime.now());

        var orderToCreate = orderMapper.toOrder(order);
        orderToCreate.setId(null);
        var orderCreated = orderDAO.create(orderToCreate);
        return orderMapper.toOrderBusinessModel(orderCreated);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        orderDAO.delete(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<OrderBusinessModel> findAll(Integer pageNumber, Integer pageSize) {
        var total = orderDAO.getTotal();
        var orders = orderDAO.findAll(pageNumber, pageSize)
                .stream()
                .map(orderMapper::toOrderBusinessModel)
                .collect(Collectors.toList());
        return new Page<>(pageNumber, pageSize, total, orders);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<OrderBusinessModel> findByUser(Long userId, Integer pageNumber, Integer pageSize) {
        int total = orderDAO.getTotal(userId);
        var orders = orderDAO.findByUser(userId, pageNumber, pageSize)
                .stream()
                .map(orderMapper::toOrderBusinessModel)
                .collect(Collectors.toList());
        return new Page<>(pageNumber, pageSize, total, orders);
    }
}
