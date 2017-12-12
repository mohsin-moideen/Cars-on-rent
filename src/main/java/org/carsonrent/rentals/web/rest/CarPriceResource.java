package org.carsonrent.rentals.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.carsonrent.rentals.domain.CarPrice;
import org.carsonrent.rentals.service.CarPriceService;
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
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing CarPrice.
 */
@RestController
@RequestMapping("/api")
public class CarPriceResource {

    private final Logger log = LoggerFactory.getLogger(CarPriceResource.class);

    private static final String ENTITY_NAME = "carPrice";

    private final CarPriceService carPriceService;

    public CarPriceResource(CarPriceService carPriceService) {
        this.carPriceService = carPriceService;
    }

    /**
     * POST  /car-prices : Create a new carPrice.
     *
     * @param carPrice the carPrice to create
     * @return the ResponseEntity with status 201 (Created) and with body the new carPrice, or with status 400 (Bad Request) if the carPrice has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/car-prices")
    @Timed
    public ResponseEntity<CarPrice> createCarPrice(@Valid @RequestBody CarPrice carPrice) throws URISyntaxException {
        log.debug("REST request to save CarPrice : {}", carPrice);
        if (carPrice.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new carPrice cannot already have an ID")).body(null);
        }
        CarPrice result = carPriceService.save(carPrice);
        return ResponseEntity.created(new URI("/api/car-prices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /car-prices : Updates an existing carPrice.
     *
     * @param carPrice the carPrice to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated carPrice,
     * or with status 400 (Bad Request) if the carPrice is not valid,
     * or with status 500 (Internal Server Error) if the carPrice couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/car-prices")
    @Timed
    public ResponseEntity<CarPrice> updateCarPrice(@Valid @RequestBody CarPrice carPrice) throws URISyntaxException {
        log.debug("REST request to update CarPrice : {}", carPrice);
        if (carPrice.getId() == null) {
            return createCarPrice(carPrice);
        }
        CarPrice result = carPriceService.save(carPrice);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, carPrice.getId().toString()))
            .body(result);
    }

    /**
     * GET  /car-prices : get all the carPrices.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of carPrices in body
     */
    @GetMapping("/car-prices")
    @Timed
    public List<CarPrice> getAllCarPrices() {
        log.debug("REST request to get all CarPrices");
        return carPriceService.findAll();
    }

    /**
     * GET  /car-prices/:id : get the "id" carPrice.
     *
     * @param id the id of the carPrice to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the carPrice, or with status 404 (Not Found)
     */
    @GetMapping("/car-prices/{id}")
    @Timed
    public ResponseEntity<CarPrice> getCarPrice(@PathVariable Long id) {
        log.debug("REST request to get CarPrice : {}", id);
        CarPrice carPrice = carPriceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(carPrice));
    }

    /**
     * DELETE  /car-prices/:id : delete the "id" carPrice.
     *
     * @param id the id of the carPrice to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/car-prices/{id}")
    @Timed
    public ResponseEntity<Void> deleteCarPrice(@PathVariable Long id) {
        log.debug("REST request to delete CarPrice : {}", id);
        carPriceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/car-prices?query=:query : search for the carPrice corresponding
     * to the query.
     *
     * @param query the query of the carPrice search
     * @return the result of the search
     */
    @GetMapping("/_search/car-prices")
    @Timed
    public List<CarPrice> searchCarPrices(@RequestParam String query) {
        log.debug("REST request to search CarPrices for query {}", query);
        return carPriceService.search(query);
    }

}
