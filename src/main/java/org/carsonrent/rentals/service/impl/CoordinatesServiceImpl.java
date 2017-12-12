package org.carsonrent.rentals.service.impl;

import org.carsonrent.rentals.service.CoordinatesService;
import org.carsonrent.rentals.domain.Coordinates;
import org.carsonrent.rentals.repository.CoordinatesRepository;
import org.carsonrent.rentals.repository.search.CoordinatesSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Coordinates.
 */
@Service
@Transactional
public class CoordinatesServiceImpl implements CoordinatesService{

    private final Logger log = LoggerFactory.getLogger(CoordinatesServiceImpl.class);

    private final CoordinatesRepository coordinatesRepository;

    private final CoordinatesSearchRepository coordinatesSearchRepository;

    public CoordinatesServiceImpl(CoordinatesRepository coordinatesRepository, CoordinatesSearchRepository coordinatesSearchRepository) {
        this.coordinatesRepository = coordinatesRepository;
        this.coordinatesSearchRepository = coordinatesSearchRepository;
    }

    /**
     * Save a coordinates.
     *
     * @param coordinates the entity to save
     * @return the persisted entity
     */
    @Override
    public Coordinates save(Coordinates coordinates) {
        log.debug("Request to save Coordinates : {}", coordinates);
        Coordinates result = coordinatesRepository.save(coordinates);
        coordinatesSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the coordinates.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Coordinates> findAll() {
        log.debug("Request to get all Coordinates");
        return coordinatesRepository.findAll();
    }

    /**
     *  Get one coordinates by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Coordinates findOne(Long id) {
        log.debug("Request to get Coordinates : {}", id);
        return coordinatesRepository.findOne(id);
    }

    /**
     *  Delete the  coordinates by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Coordinates : {}", id);
        coordinatesRepository.delete(id);
        coordinatesSearchRepository.delete(id);
    }

    /**
     * Search for the coordinates corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Coordinates> search(String query) {
        log.debug("Request to search Coordinates for query {}", query);
        return StreamSupport
            .stream(coordinatesSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
