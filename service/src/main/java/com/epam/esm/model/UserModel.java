package com.epam.esm.model;

import org.springframework.hateoas.RepresentationModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents user in service layer.
 *
 * @see com.epam.esm.domain.User
 */
public class UserModel extends RepresentationModel<UserModel> {
    private Long id;
    private String name;
    private List<OrderModel> orders = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<OrderModel> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderModel> orders) {
        this.orders = orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserModel that = (UserModel) o;
        return Objects.equals(id, that.id)
                && Objects.equals(name, that.name)
                && Objects.equals(orders, that.orders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
