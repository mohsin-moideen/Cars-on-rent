package org.carsonrent.rentals.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Coordinates.
 */
@Entity
@Table(name = "coordinates")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "coordinates")
public class Coordinates implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "lognitude", nullable = false)
    private Float lognitude;

    @NotNull
    @Column(name = "latitude", nullable = false)
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

    public Coordinates lognitude(Float lognitude) {
        this.lognitude = lognitude;
        return this;
    }

    public void setLognitude(Float lognitude) {
        this.lognitude = lognitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public Coordinates latitude(Float latitude) {
        this.latitude = latitude;
        return this;
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
        Coordinates coordinates = (Coordinates) o;
        if (coordinates.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), coordinates.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Coordinates{" +
            "id=" + getId() +
            ", lognitude='" + getLognitude() + "'" +
            ", latitude='" + getLatitude() + "'" +
            "}";
    }
}
