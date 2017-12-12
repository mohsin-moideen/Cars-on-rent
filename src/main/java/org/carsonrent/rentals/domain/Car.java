package org.carsonrent.rentals.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Car.
 */
@Entity
@Table(name = "car")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "car")
public class Car implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "capacity")
    private Integer capacity;

    @ManyToOne
    private Provider provider;

    @OneToOne
    @JoinColumn(unique = true)
    private CarPrice carPrice;

    @OneToMany(mappedBy = "car")
    
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CarAttr> carAttrs = new HashSet<>();

    @OneToMany(mappedBy = "car")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Availability> availabilities = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Car capacity(Integer capacity) {
        this.capacity = capacity;
        return this;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Provider getProvider() {
        return provider;
    }

    public Car provider(Provider provider) {
        this.provider = provider;
        return this;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public CarPrice getCarPrice() {
        return carPrice;
    }

    public Car carPrice(CarPrice carPrice) {
        this.carPrice = carPrice;
        return this;
    }

    public void setCarPrice(CarPrice carPrice) {
        this.carPrice = carPrice;
    }

    public Set<CarAttr> getCarAttrs() {
        return carAttrs;
    }

    public Car carAttrs(Set<CarAttr> carAttrs) {
        this.carAttrs = carAttrs;
        return this;
    }

    public Car addCarAttr(CarAttr carAttr) {
        this.carAttrs.add(carAttr);
        carAttr.setCar(this);
        return this;
    }

    public Car removeCarAttr(CarAttr carAttr) {
        this.carAttrs.remove(carAttr);
        carAttr.setCar(null);
        return this;
    }

    public void setCarAttrs(Set<CarAttr> carAttrs) {
        this.carAttrs = carAttrs;
    }

    public Set<Availability> getAvailabilities() {
        return availabilities;
    }

    public Car availabilities(Set<Availability> availabilities) {
        this.availabilities = availabilities;
        return this;
    }

    public Car addAvailability(Availability availability) {
        this.availabilities.add(availability);
        availability.setCar(this);
        return this;
    }

    public Car removeAvailability(Availability availability) {
        this.availabilities.remove(availability);
        availability.setCar(null);
        return this;
    }

    public void setAvailabilities(Set<Availability> availabilities) {
        this.availabilities = availabilities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Car car = (Car) o;
        if (car.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), car.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Car{" +
            "id=" + getId() +
            ", capacity='" + getCapacity() + "'" +
            "}";
    }
}
