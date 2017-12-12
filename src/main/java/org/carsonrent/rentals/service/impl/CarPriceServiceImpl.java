package org.carsonrent.rentals.service.impl;

import org.carsonrent.rentals.service.CarPriceService;
import org.carsonrent.rentals.domain.CarPrice;
import org.carsonrent.rentals.repository.CarPriceRepository;
import org.carsonrent.rentals.repository.search.CarPriceSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing CarPrice.
 */
@Service
@Transactional
public class CarPriceServiceImpl implements CarPriceService{

    private final Logger log = LoggerFactory.getLogger(CarPriceServiceImpl.class);

    private final CarPriceRepository carPriceRepository;

    private final CarPriceSearchRepository carPriceSearchRepository;

    public CarPriceServiceImpl(CarPriceRepository carPriceRepository, CarPriceSearchRepository carPriceSearchRepository) {
        this.carPriceRepository = carPriceRepository;
        this.carPriceSearchRepository = carPriceSearchRepository;
    }

    /**
     * Save a carPrice.
     *
     * @param carPrice the entity to save
     * @return the persisted entity
     */
    @Override
    public CarPrice save(CarPrice carPrice) {
        log.debug("Request to save CarPrice : {}", carPrice);
        CarPrice result = carPriceRepository.save(carPrice);
        carPriceSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the carPrices.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CarPrice> findAll() {
        log.debug("Request to get all CarPrices");
        return carPriceRepository.findAll();
    }

    /**
     *  Get one carPrice by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CarPrice findOne(Long id) {
        log.debug("Request to get CarPrice : {}", id);
        return carPriceRepository.findOne(id);
    }

    /**
     *  Delete the  carPrice by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CarPrice : {}", id);
        carPriceRepository.delete(id);
        carPriceSearchRepository.delete(id);
    }

    /**
     * Search for the carPrice corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CarPrice> search(String query) {
        log.debug("Request to search CarPrices for query {}", query);
        return StreamSupport
            .stream(carPriceSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
