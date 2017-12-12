package org.carsonrent.rentals.repository;

import org.carsonrent.rentals.domain.Bookings;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Bookings entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookingsRepository extends JpaRepository<Bookings,Long> {

    @Query("select bookings from Bookings bookings where bookings.user.login = ?#{principal.username}")
    List<Bookings> findByUserIsCurrentUser();
    
}
