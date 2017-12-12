package org.carsonrent.rentals.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Coordinates entity.
 */
public class CoordinatesDTO implements Serializable {

    private Long id;

    @NotNull
    private Float lognitude;

    @NotNull
    private Float latitude;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getLognitude() {
        return lognitude;
    }

    public void setLognitude(Float lognitude) {
        this.lognitude = lognitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CoordinatesDTO coordinatesDTO = (CoordinatesDTO) o;
        if(coordinatesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), coordinatesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CoordinatesDTO{" +
            "id=" + getId() +
            ", lognitude='" + getLognitude() + "'" +
            ", latitude='" + getLatitude() + "'" +
            "}";
    }
}
