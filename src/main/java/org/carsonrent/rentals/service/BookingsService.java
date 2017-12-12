package org.carsonrent.rentals.service;

import org.carsonrent.rentals.domain.Bookings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Bookings.
 */
public interface BookingsService {

    /**
     * Save a bookings.
     *
     * @param bookings the entity to save
     * @return the persisted entity
     */
    Bookings save(Bookings bookings);

    /**
     *  Get all the bookings.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Bookings> findAll(Pageable pageable);

    /**
     *  Get the "id" bookings.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Bookings findOne(Long id);

    /**
     *  Delete the "id" bookings.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the bookings corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Bookings> search(String query, Pageable pageable);
}
