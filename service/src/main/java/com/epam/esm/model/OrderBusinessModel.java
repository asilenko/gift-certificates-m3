package com.epam.esm.model;

import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents order in service layer.
 *
 * @see com.epam.esm.domain.Order
 */
public class OrderBusinessModel extends RepresentationModel<OrderBusinessModel> implements BusinessModel{

    private Long id;
    private BigDecimal cost;
    private LocalDateTime purchaseDate;
    private Long userID;
    private GiftCertificateBusinessModel giftCertificate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public GiftCertificateBusinessModel getGiftCertificate() {
        return giftCertificate;
    }

    public void setGiftCertificate(GiftCertificateBusinessModel giftCertificate) {
        this.giftCertificate = giftCertificate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderBusinessModel that = (OrderBusinessModel) o;
        return Objects.equals(id, that.id)
                && Objects.equals(cost, that.cost)
                && Objects.equals(purchaseDate, that.purchaseDate)
                && Objects.equals(userID, that.userID)
                && Objects.equals(giftCertificate, that.giftCertificate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
