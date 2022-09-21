package com.epam.esm.model;

import com.epam.esm.domain.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Maps User from persistence layer to UserBusinessModel and vice versa.
 *
 * @see com.epam.esm.domain.User
 * @see UserBusinessModel
 */
@Component
public class UserMapper {

    private final OrderMapper orderMapper;

    public UserMapper(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    /**
     * Maps BusinessModel to EntityModel.
     *
     * @param user
     * @return UserBusinessModel
     * @see User
     * @see UserBusinessModel
     */
    public UserBusinessModel toUserBusinessModel(User user) {
        UserBusinessModel userBusinessModel = new UserBusinessModel();
        userBusinessModel.setId(user.getId());
        userBusinessModel.setName(user.getName());
        userBusinessModel.setOrders(extractOrders(user));
        return userBusinessModel;
    }

    private List<OrderBusinessModel> extractOrders(User user) {
        return user.getOrders()
                .stream()
                .map(orderMapper::toOrderBusinessModel)
                .collect(Collectors.toList());
    }
}
