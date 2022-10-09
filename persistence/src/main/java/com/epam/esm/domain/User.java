package com.epam.esm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Model for user entity. User can buy a gift certificate by placing an order.
 *
 * @see Order
 */
@Entity
@Table(name = "Users")
public class User extends AuditableEntity implements Serializable {

    @Column(nullable = false, length = 40)
    private String name;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Order> orders = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId())
                && Objects.equals(name, user.name)
                && Objects.equals(orders, user.orders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
