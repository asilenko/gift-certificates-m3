package com.epam.esm.service;

import com.epam.esm.dao.OrderDAO;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.OrderBusinessModel;
import com.epam.esm.model.OrderMapper;
import com.epam.esm.pagination.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public OrderServiceImpl(OrderMapper orderMapper, OrderDAO orderDAO, GiftCertificateService giftCertificateService) {
        this.orderMapper = orderMapper;
        this.orderDAO = orderDAO;
        this.giftCertificateService = giftCertificateService;
    }

    /**
     * Finds a model by specified id.
     *
     * @param id
     * @return model
     * @throws ResourceNotFoundException
     */
    @Override
    public OrderBusinessModel find(Long id) throws ResourceNotFoundException {
        return orderMapper.toOrderBusinessModel(orderDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No resource with id " + id)));
    }

    /**
     * Creates a new model.
     *
     * @param order
     * @return created model
     */
    @Override
    public OrderBusinessModel create(OrderBusinessModel order) {
        prepareCertificates(order);
        var orderToCreate = orderMapper.toOrder(order);
        orderToCreate.setId(null);
        var orderCreated = orderDAO.create(orderToCreate);
        return orderMapper.toOrderBusinessModel(orderCreated);
    }

    private void prepareCertificates(OrderBusinessModel order) {
        var preparedCertificates = order.getGiftCertificates()
                .stream()
                .map(giftCertificateService::create)
                .collect(Collectors.toList());
        order.setGiftCertificates(preparedCertificates);
    }

    /**
     * Deletes model by specified id.
     *
     * @param id
     */
    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        orderDAO.delete(id);
    }

    /**
     * Finds all existing orders.
     *
     * @param pageNumber
     * @param pageSize
     * @return List of orders.
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
}
