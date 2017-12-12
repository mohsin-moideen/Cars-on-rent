package org.carsonrent.rentals.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CarPrice entity.
 */
public class CarPriceDTO implements Serializable {

    private Long id;

    @NotNull
    private Double pricePerHour;

    @NotNull
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

    public void setPricePerHour(Double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public Double getDepositAmount() {
        return depositAmount;
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

        CarPriceDTO carPriceDTO = (CarPriceDTO) o;
        if(carPriceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), carPriceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CarPriceDTO{" +
            "id=" + getId() +
            ", pricePerHour='" + getPricePerHour() + "'" +
            ", depositAmount='" + getDepositAmount() + "'" +
            "}";
    }
}
