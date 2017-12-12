package org.carsonrent.rentals.repository;

import org.carsonrent.rentals.domain.CarPrice;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CarPrice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CarPriceRepository extends JpaRepository<CarPrice,Long> {
    
}
