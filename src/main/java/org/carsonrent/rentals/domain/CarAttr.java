package org.carsonrent.rentals.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CarAttr.
 */
@Entity
@Table(name = "car_attr")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "carattr")
public class CarAttr implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "attrname", nullable = false)
    private String attrname;

    @NotNull
    @Column(name = "attrval", nullable = false)
    private String attrval;

    @ManyToOne
    private Car car;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAttrname() {
        return attrname;
    }

    public CarAttr attrname(String attrname) {
        this.attrname = attrname;
        return this;
    }

    public void setAttrname(String attrname) {
        this.attrname = attrname;
    }

    public String getAttrval() {
        return attrval;
    }

    public CarAttr attrval(String attrval) {
        this.attrval = attrval;
        return this;
    }

    public void setAttrval(String attrval) {
        this.attrval = attrval;
    }

    public Car getCar() {
        return car;
    }

    public CarAttr car(Car car) {
        this.car = car;
        return this;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CarAttr carAttr = (CarAttr) o;
        if (carAttr.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), carAttr.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CarAttr{" +
            "id=" + getId() +
            ", attrname='" + getAttrname() + "'" +
            ", attrval='" + getAttrval() + "'" +
            "}";
    }
}
