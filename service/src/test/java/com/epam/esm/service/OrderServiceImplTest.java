package com.epam.esm.service;

import com.epam.esm.dao.jpa.JPAOrderDAO;
import com.epam.esm.dataprovider.DataProvider;
import com.epam.esm.domain.Order;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.OrderBusinessModel;
import com.epam.esm.model.OrderMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    private static final Long ID = 1L;
    private static final Integer PAGE_NUMBER = 1;
    private static final Integer PAGE_SIZE = 10;
    private static final int NUMBER_OF_INVOCATIONS = 1;
    private static final int NUMBER_OF_ORDERS = 1;
    private final DataProvider dataProvider = new DataProvider();
    private final Order order = dataProvider.createOrderWithCertificate();
    private final OrderBusinessModel orderBusinessModel = dataProvider.createOrderBusinessModelWithCertificate();

    @Mock
    private JPAOrderDAO jpaOrderDAO;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderServiceImpl orderService;


    @Test
    void shouldReturnProperOrderBusinessModelWhenResourceIsFound() throws ResourceNotFoundException {
        //GIVEN
        when(jpaOrderDAO.findById(ID)).thenReturn(Optional.of(order));
        when(orderMapper.toOrderBusinessModel(order)).thenReturn(orderBusinessModel);
        OrderBusinessModel expected = orderBusinessModel;
        //WHEN
        var actual = orderService.find(ID);
        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnOrdersListWhenResourceIsFound() {
        //GIVEN
        List<OrderBusinessModel> expected = List.of(orderBusinessModel);
        List<Order> orders = List.of(order);
        //WHEN
        when(jpaOrderDAO.findAll(PAGE_NUMBER, PAGE_SIZE)).thenReturn(orders);
        when(orderMapper.toOrderBusinessModel(order)).thenReturn(orderBusinessModel);
        List<OrderBusinessModel> actual = orderService.findAll(PAGE_NUMBER, PAGE_SIZE).getContent();
        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnOrdersListForUserWhenResourceIsFound() {
        //GIVEN
        List<OrderBusinessModel> expected = List.of(orderBusinessModel);
        List<Order> orders = List.of(order);
        //WHEN
        when(jpaOrderDAO.getTotal(ID)).thenReturn(NUMBER_OF_ORDERS);
        when(jpaOrderDAO.findByUser(ID, PAGE_NUMBER, PAGE_SIZE)).thenReturn(orders);
        when(orderMapper.toOrderBusinessModel(order)).thenReturn(orderBusinessModel);
        List<OrderBusinessModel> actual = orderService.findByUser(ID, PAGE_NUMBER, PAGE_SIZE).getContent();
        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void deleteMethodShouldBeCalledWhileOrderIsBeingRemoved() throws ResourceNotFoundException {
        //GIVEN
        //WHEN
        orderService.delete(ID);
        //THEN
        verify(jpaOrderDAO, Mockito.times(NUMBER_OF_INVOCATIONS)).delete(ID);
    }
}
