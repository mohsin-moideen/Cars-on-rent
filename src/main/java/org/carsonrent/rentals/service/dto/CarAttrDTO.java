package org.carsonrent.rentals.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the CarAttr entity.
 */
public class CarAttrDTO implements Serializable {

    private Long id;

    @NotNull
    private String attrname;

    @NotNull
    private String attrval;

    private Long carId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAttrname() {
        return attrname;
    }

    public void setAttrname(String attrname) {
        this.attrname = attrname;
    }

    public String getAttrval() {
        return attrval;
    }

    public void setAttrval(String attrval) {
        this.attrval = attrval;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CarAttrDTO carAttrDTO = (CarAttrDTO) o;
        if(carAttrDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), carAttrDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CarAttrDTO{" +
            "id=" + getId() +
            ", attrname='" + getAttrname() + "'" +
            ", attrval='" + getAttrval() + "'" +
            "}";
    }
}
