package org.carsonrent.rentals.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.carsonrent.rentals.domain.Availability;

import org.carsonrent.rentals.repository.AvailabilityRepository;
import org.carsonrent.rentals.repository.search.AvailabilitySearchRepository;
import org.carsonrent.rentals.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Availability.
 */
@RestController
@RequestMapping("/api")
public class AvailabilityResource {

    private final Logger log = LoggerFactory.getLogger(AvailabilityResource.class);

    private static final String ENTITY_NAME = "availability";

    private final AvailabilityRepository availabilityRepository;

    private final AvailabilitySearchRepository availabilitySearchRepository;

    public AvailabilityResource(AvailabilityRepository availabilityRepository, AvailabilitySearchRepository availabilitySearchRepository) {
        this.availabilityRepository = availabilityRepository;
        this.availabilitySearchRepository = availabilitySearchRepository;
    }

    /**
     * POST  /availabilities : Create a new availability.
     *
     * @param availability the availability to create
     * @return the ResponseEntity with status 201 (Created) and with body the new availability, or with status 400 (Bad Request) if the availability has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/availabilities")
    @Timed
    public ResponseEntity<Availability> createAvailability(@Valid @RequestBody Availability availability) throws URISyntaxException {
        log.debug("REST request to save Availability : {}", availability);
        if (availability.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new availability cannot already have an ID")).body(null);
        }
        Availability result = availabilityRepository.save(availability);
        availabilitySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/availabilities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /availabilities : Updates an existing availability.
     *
     * @param availability the availability to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated availability,
     * or with status 400 (Bad Request) if the availability is not valid,
     * or with status 500 (Internal Server Error) if the availability couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/availabilities")
    @Timed
    public ResponseEntity<Availability> updateAvailability(@Valid @RequestBody Availability availability) throws URISyntaxException {
        log.debug("REST request to update Availability : {}", availability);
        if (availability.getId() == null) {
            return createAvailability(availability);
        }
        Availability result = availabilityRepository.save(availability);
        availabilitySearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, availability.getId().toString()))
            .body(result);
    }

    /**
     * GET  /availabilities : get all the availabilities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of availabilities in body
     */
    @GetMapping("/availabilities")
    @Timed
    public List<Availability> getAllAvailabilities() {
        log.debug("REST request to get all Availabilities");
        return availabilityRepository.findAll();
    }

    /**
     * GET  /availabilities/:id : get the "id" availability.
     *
     * @param id the id of the availability to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the availability, or with status 404 (Not Found)
     */
    @GetMapping("/availabilities/{id}")
    @Timed
    public ResponseEntity<Availability> getAvailability(@PathVariable Long id) {
        log.debug("REST request to get Availability : {}", id);
        Availability availability = availabilityRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(availability));
    }

    /**
     * DELETE  /availabilities/:id : delete the "id" availability.
     *
     * @param id the id of the availability to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/availabilities/{id}")
    @Timed
    public ResponseEntity<Void> deleteAvailability(@PathVariable Long id) {
        log.debug("REST request to delete Availability : {}", id);
        availabilityRepository.delete(id);
        availabilitySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/availabilities?query=:query : search for the availability corresponding
     * to the query.
     *
     * @param query the query of the availability search
     * @return the result of the search
     */
    @GetMapping("/_search/availabilities")
    @Timed
    public List<Availability> searchAvailabilities(@RequestParam String query) {
        log.debug("REST request to search Availabilities for query {}", query);
        return StreamSupport
            .stream(availabilitySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
