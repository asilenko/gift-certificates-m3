package com.epam.esm.controller;

import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.hateoas.OrderLinker;
import com.epam.esm.model.OrderModel;
import com.epam.esm.service.InvalidFieldValueException;
import com.epam.esm.service.OrderService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Process requests for Order resources.
 */
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderLinker orderLinker;

    public OrderController(OrderService orderService, OrderLinker orderLinker) {
        this.orderService = orderService;
        this.orderLinker = orderLinker;
    }

    /**
     * Get order resource by specified id.
     *
     * @param id
     * @return OrderModel
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderModel> getByID(@PathVariable Long id) throws ResourceNotFoundException {
        var order = orderService.find(id);
        orderLinker.addLinkWithCertificates(order);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    /**
     * Gets all orders.
     *
     * @return List of OrderModel
     * <p>
     * Request example:
     * <pre>
     * GET /orders/?pageNumber=2&pageSize=10 HTTP/1.1
     * </pre>
     */
    @GetMapping
    public ResponseEntity<CollectionModel<OrderModel>> getAll(
            @RequestParam(defaultValue = "1") Integer pageNumber,
            @RequestParam(defaultValue = "20") Integer pageSize
    ) {
        var page = orderService.findAll(pageNumber, pageSize);
        CollectionModel<OrderModel> collectionModel = orderLinker.addLinks(page);
        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    /**
     * Creates new order resource.
     *
     * @param userId of user who is placing the order – must not be null
     * @param certificateId id of certificate bough by user – must not be null
     * @return OrderModel
     *
     * Request example:
     * <pre>
     * POST /orders/?userId=1&certificateId=4 HTTP/1.1
     * </pre>
     */
    @PostMapping
    public ResponseEntity<OrderModel> placeOrder(
            @RequestParam Long userId,
            @RequestParam Long certificateId)
            throws InvalidFieldValueException, ResourceNotFoundException {
        var createdOrder = orderService.placeOrder(userId, certificateId);
        orderLinker.addLinkWithCertificates(createdOrder);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    /**
     * Delete order resource by specified id.
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) throws ResourceNotFoundException {
        orderService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
