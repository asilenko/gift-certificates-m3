package com.epam.esm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Model for order entity. A user can purchase one gift certificate by placing an order. The cost of the order
 * is calculated when the order is created and does not change if certificate price updates.
 *
 * @see GiftCertificate
 * @see User
 */
@Entity
@Table(name = "Orders")
public class Order extends AuditableEntity implements Serializable {

    @Column(nullable = false)
    private BigDecimal cost;

    @Column(name = "purchase_date", nullable = false)
    private LocalDateTime purchaseDate;

    @Column(name = "user_id", nullable = false, updatable = false)
    private Long userID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "certificate_id")
    private GiftCertificate giftCertificate;

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public GiftCertificate getGiftCertificates() {
        return giftCertificate;
    }

    public void setGiftCertificate(GiftCertificate giftCertificate) {
        this.giftCertificate = giftCertificate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(getId(), order.getId())
                && Objects.equals(cost, order.cost)
                && Objects.equals(purchaseDate, order.purchaseDate)
                && Objects.equals(userID, order.userID)
                && Objects.equals(giftCertificate, order.giftCertificate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
