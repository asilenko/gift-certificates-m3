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
import java.util.ArrayList;
import java.util.List;
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
    public OrderBusinessModel placeOrder(Long userId, List<Long> certificateIds)
            throws ResourceNotFoundException, InvalidFieldValueException {
        userService.find(userId);
        List<GiftCertificateBusinessModel> certificates = prepareCertificates(certificateIds);
        BigDecimal cost = calculateCost(certificates);
        OrderBusinessModel order = new OrderBusinessModel();
        order.setGiftCertificates(certificates);
        order.setUserID(userId);
        order.setCost(cost);
        order.setPurchaseDate(LocalDateTime.now());

        var orderToCreate = orderMapper.toOrder(order);
        orderToCreate.setId(null);
        var orderCreated = orderDAO.create(orderToCreate);
        return orderMapper.toOrderBusinessModel(orderCreated);
    }

    private BigDecimal calculateCost(List<GiftCertificateBusinessModel> certificates) throws InvalidFieldValueException {
        var cost = certificates.stream()
                .map(GiftCertificateBusinessModel::getPrice)
                .reduce(BigDecimal::add);
        return cost.orElseThrow(() -> new InvalidFieldValueException("Order should contain at least once certificate"));
    }

    private List<GiftCertificateBusinessModel> prepareCertificates(List<Long> certificateId)
            throws ResourceNotFoundException {
        List<GiftCertificateBusinessModel> certificates = new ArrayList<>();

        for (Long aLong : certificateId) {
            GiftCertificateBusinessModel giftCertificateBusinessModel = giftCertificateService.find(aLong);
            certificates.add(giftCertificateBusinessModel);
        }
        return certificates;
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
