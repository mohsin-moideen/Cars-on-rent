package org.carsonrent.rentals.service;

import org.carsonrent.rentals.domain.CarPrice;
import java.util.List;

/**
 * Service Interface for managing CarPrice.
 */
public interface CarPriceService {

    /**
     * Save a carPrice.
     *
     * @param carPrice the entity to save
     * @return the persisted entity
     */
    CarPrice save(CarPrice carPrice);

    /**
     *  Get all the carPrices.
     *
     *  @return the list of entities
     */
    List<CarPrice> findAll();

    /**
     *  Get the "id" carPrice.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CarPrice findOne(Long id);

    /**
     *  Delete the "id" carPrice.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the carPrice corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<CarPrice> search(String query);
}
