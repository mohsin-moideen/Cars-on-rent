package org.carsonrent.rentals.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CarPrice.
 */
@Entity
@Table(name = "car_price")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "carprice")
public class CarPrice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "price_per_hour", nullable = false)
    private Double pricePerHour;

    @NotNull
    @Column(name = "deposit_amount", nullable = false)
    private Double depositAmount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPricePerHour() {
        return pricePerHour;
    }

    public CarPrice pricePerHour(Double pricePerHour) {
        this.pricePerHour = pricePerHour;
        return this;
    }

    public void setPricePerHour(Double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public Double getDepositAmount() {
        return depositAmount;
    }

    public CarPrice depositAmount(Double depositAmount) {
        this.depositAmount = depositAmount;
        return this;
    }

    public void setDepositAmount(Double depositAmount) {
        this.depositAmount = depositAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CarPrice carPrice = (CarPrice) o;
        if (carPrice.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), carPrice.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CarPrice{" +
            "id=" + getId() +
            ", pricePerHour='" + getPricePerHour() + "'" +
            ", depositAmount='" + getDepositAmount() + "'" +
            "}";
    }
}
