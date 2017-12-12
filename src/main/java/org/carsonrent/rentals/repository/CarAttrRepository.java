package org.carsonrent.rentals.repository;

import org.carsonrent.rentals.domain.CarAttr;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CarAttr entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CarAttrRepository extends JpaRepository<CarAttr,Long> {
    
}
