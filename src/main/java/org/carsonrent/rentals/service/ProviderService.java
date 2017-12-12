package org.carsonrent.rentals.service;

import org.carsonrent.rentals.domain.Provider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Provider.
 */
public interface ProviderService {

    /**
     * Save a provider.
     *
     * @param provider the entity to save
     * @return the persisted entity
     */
    Provider save(Provider provider);

    /**
     *  Get all the providers.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Provider> findAll(Pageable pageable);

    /**
     *  Get the "id" provider.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Provider findOne(Long id);

    /**
     *  Delete the "id" provider.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the provider corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Provider> search(String query, Pageable pageable);
}
