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
public class UserBusinessModel extends RepresentationModel<UserBusinessModel> implements BusinessModel {
    private Long id;
    private String name;
    private List<OrderBusinessModel> orders = new ArrayList<>();

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

    public List<OrderBusinessModel> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderBusinessModel> orders) {
        this.orders = orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserBusinessModel that = (UserBusinessModel) o;
        return Objects.equals(id, that.id)
                && Objects.equals(name, that.name)
                && Objects.equals(orders, that.orders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
