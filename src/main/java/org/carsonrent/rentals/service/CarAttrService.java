package org.carsonrent.rentals.service;

import org.carsonrent.rentals.domain.CarAttr;
import java.util.List;

/**
 * Service Interface for managing CarAttr.
 */
public interface CarAttrService {

    /**
     * Save a carAttr.
     *
     * @param carAttr the entity to save
     * @return the persisted entity
     */
    CarAttr save(CarAttr carAttr);

    /**
     *  Get all the carAttrs.
     *
     *  @return the list of entities
     */
    List<CarAttr> findAll();

    /**
     *  Get the "id" carAttr.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CarAttr findOne(Long id);

    /**
     *  Delete the "id" carAttr.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the carAttr corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<CarAttr> search(String query);
}
