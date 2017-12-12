package org.carsonrent.rentals.service.impl;

import org.carsonrent.rentals.service.BookingsService;
import org.carsonrent.rentals.domain.Bookings;
import org.carsonrent.rentals.repository.BookingsRepository;
import org.carsonrent.rentals.repository.search.BookingsSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Bookings.
 */
@Service
@Transactional
public class BookingsServiceImpl implements BookingsService{

    private final Logger log = LoggerFactory.getLogger(BookingsServiceImpl.class);

    private final BookingsRepository bookingsRepository;

    private final BookingsSearchRepository bookingsSearchRepository;

    public BookingsServiceImpl(BookingsRepository bookingsRepository, BookingsSearchRepository bookingsSearchRepository) {
        this.bookingsRepository = bookingsRepository;
        this.bookingsSearchRepository = bookingsSearchRepository;
    }

    /**
     * Save a bookings.
     *
     * @param bookings the entity to save
     * @return the persisted entity
     */
    @Override
    public Bookings save(Bookings bookings) {
        log.debug("Request to save Bookings : {}", bookings);
        Bookings result = bookingsRepository.save(bookings);
        bookingsSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the bookings.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Bookings> findAll(Pageable pageable) {
        log.debug("Request to get all Bookings");
        return bookingsRepository.findAll(pageable);
    }

    /**
     *  Get one bookings by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Bookings findOne(Long id) {
        log.debug("Request to get Bookings : {}", id);
        return bookingsRepository.findOne(id);
    }

    /**
     *  Delete the  bookings by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Bookings : {}", id);
        bookingsRepository.delete(id);
        bookingsSearchRepository.delete(id);
    }

    /**
     * Search for the bookings corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Bookings> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Bookings for query {}", query);
        Page<Bookings> result = bookingsSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
