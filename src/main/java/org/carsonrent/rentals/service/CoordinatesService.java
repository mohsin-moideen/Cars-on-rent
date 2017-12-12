package org.carsonrent.rentals.service;

import org.carsonrent.rentals.domain.Coordinates;
import java.util.List;

/**
 * Service Interface for managing Coordinates.
 */
public interface CoordinatesService {

    /**
     * Save a coordinates.
     *
     * @param coordinates the entity to save
     * @return the persisted entity
     */
    Coordinates save(Coordinates coordinates);

    /**
     *  Get all the coordinates.
     *
     *  @return the list of entities
     */
    List<Coordinates> findAll();

    /**
     *  Get the "id" coordinates.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Coordinates findOne(Long id);

    /**
     *  Delete the "id" coordinates.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the coordinates corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<Coordinates> search(String query);
}
