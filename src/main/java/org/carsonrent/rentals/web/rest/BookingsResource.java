package org.carsonrent.rentals.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.carsonrent.rentals.domain.Bookings;
import org.carsonrent.rentals.service.BookingsService;
import org.carsonrent.rentals.web.rest.util.HeaderUtil;
import org.carsonrent.rentals.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Bookings.
 */
@RestController
@RequestMapping("/api")
public class BookingsResource {

    private final Logger log = LoggerFactory.getLogger(BookingsResource.class);

    private static final String ENTITY_NAME = "bookings";

    private final BookingsService bookingsService;

    public BookingsResource(BookingsService bookingsService) {
        this.bookingsService = bookingsService;
    }

    /**
     * POST  /bookings : Create a new bookings.
     *
     * @param bookings the bookings to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bookings, or with status 400 (Bad Request) if the bookings has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bookings")
    @Timed
    public ResponseEntity<Bookings> createBookings(@Valid @RequestBody Bookings bookings) throws URISyntaxException {
        log.debug("REST request to save Bookings : {}", bookings);
        if (bookings.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new bookings cannot already have an ID")).body(null);
        }
        Bookings result = bookingsService.save(bookings);
        return ResponseEntity.created(new URI("/api/bookings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bookings : Updates an existing bookings.
     *
     * @param bookings the bookings to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bookings,
     * or with status 400 (Bad Request) if the bookings is not valid,
     * or with status 500 (Internal Server Error) if the bookings couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bookings")
    @Timed
    public ResponseEntity<Bookings> updateBookings(@Valid @RequestBody Bookings bookings) throws URISyntaxException {
        log.debug("REST request to update Bookings : {}", bookings);
        if (bookings.getId() == null) {
            return createBookings(bookings);
        }
        Bookings result = bookingsService.save(bookings);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bookings.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bookings : get all the bookings.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of bookings in body
     */
    @GetMapping("/bookings")
    @Timed
    public ResponseEntity<List<Bookings>> getAllBookings(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Bookings");
        Page<Bookings> page = bookingsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bookings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /bookings/:id : get the "id" bookings.
     *
     * @param id the id of the bookings to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bookings, or with status 404 (Not Found)
     */
    @GetMapping("/bookings/{id}")
    @Timed
    public ResponseEntity<Bookings> getBookings(@PathVariable Long id) {
        log.debug("REST request to get Bookings : {}", id);
        Bookings bookings = bookingsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bookings));
    }

    /**
     * DELETE  /bookings/:id : delete the "id" bookings.
     *
     * @param id the id of the bookings to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bookings/{id}")
    @Timed
    public ResponseEntity<Void> deleteBookings(@PathVariable Long id) {
        log.debug("REST request to delete Bookings : {}", id);
        bookingsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/bookings?query=:query : search for the bookings corresponding
     * to the query.
     *
     * @param query the query of the bookings search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/bookings")
    @Timed
    public ResponseEntity<List<Bookings>> searchBookings(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Bookings for query {}", query);
        Page<Bookings> page = bookingsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/bookings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
