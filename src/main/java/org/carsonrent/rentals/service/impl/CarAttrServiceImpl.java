package org.carsonrent.rentals.service.impl;

import org.carsonrent.rentals.service.CarAttrService;
import org.carsonrent.rentals.domain.CarAttr;
import org.carsonrent.rentals.repository.CarAttrRepository;
import org.carsonrent.rentals.repository.search.CarAttrSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing CarAttr.
 */
@Service
@Transactional
public class CarAttrServiceImpl implements CarAttrService{

    private final Logger log = LoggerFactory.getLogger(CarAttrServiceImpl.class);

    private final CarAttrRepository carAttrRepository;

    private final CarAttrSearchRepository carAttrSearchRepository;

    public CarAttrServiceImpl(CarAttrRepository carAttrRepository, CarAttrSearchRepository carAttrSearchRepository) {
        this.carAttrRepository = carAttrRepository;
        this.carAttrSearchRepository = carAttrSearchRepository;
    }

    /**
     * Save a carAttr.
     *
     * @param carAttr the entity to save
     * @return the persisted entity
     */
    @Override
    public CarAttr save(CarAttr carAttr) {
        log.debug("Request to save CarAttr : {}", carAttr);
        CarAttr result = carAttrRepository.save(carAttr);
        carAttrSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the carAttrs.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CarAttr> findAll() {
        log.debug("Request to get all CarAttrs");
        return carAttrRepository.findAll();
    }

    /**
     *  Get one carAttr by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CarAttr findOne(Long id) {
        log.debug("Request to get CarAttr : {}", id);
        return carAttrRepository.findOne(id);
    }

    /**
     *  Delete the  carAttr by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CarAttr : {}", id);
        carAttrRepository.delete(id);
        carAttrSearchRepository.delete(id);
    }

    /**
     * Search for the carAttr corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CarAttr> search(String query) {
        log.debug("Request to search CarAttrs for query {}", query);
        return StreamSupport
            .stream(carAttrSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
