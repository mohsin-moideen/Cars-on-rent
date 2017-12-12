package org.carsonrent.rentals.service.impl;

import org.carsonrent.rentals.service.ProviderService;
import org.carsonrent.rentals.domain.Provider;
import org.carsonrent.rentals.repository.ProviderRepository;
import org.carsonrent.rentals.repository.search.ProviderSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Provider.
 */
@Service
@Transactional
public class ProviderServiceImpl implements ProviderService{

    private final Logger log = LoggerFactory.getLogger(ProviderServiceImpl.class);

    private final ProviderRepository providerRepository;

    private final ProviderSearchRepository providerSearchRepository;

    public ProviderServiceImpl(ProviderRepository providerRepository, ProviderSearchRepository providerSearchRepository) {
        this.providerRepository = providerRepository;
        this.providerSearchRepository = providerSearchRepository;
    }

    /**
     * Save a provider.
     *
     * @param provider the entity to save
     * @return the persisted entity
     */
    @Override
    public Provider save(Provider provider) {
        log.debug("Request to save Provider : {}", provider);
        Provider result = providerRepository.save(provider);
        providerSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the providers.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Provider> findAll(Pageable pageable) {
        log.debug("Request to get all Providers");
        return providerRepository.findAll(pageable);
    }

    /**
     *  Get one provider by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Provider findOne(Long id) {
        log.debug("Request to get Provider : {}", id);
        return providerRepository.findOne(id);
    }

    /**
     *  Delete the  provider by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Provider : {}", id);
        providerRepository.delete(id);
        providerSearchRepository.delete(id);
    }

    /**
     * Search for the provider corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Provider> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Providers for query {}", query);
        Page<Provider> result = providerSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
