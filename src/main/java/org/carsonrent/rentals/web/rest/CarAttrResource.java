package org.carsonrent.rentals.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.carsonrent.rentals.domain.CarAttr;
import org.carsonrent.rentals.service.CarAttrService;
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
 * REST controller for managing CarAttr.
 */
@RestController
@RequestMapping("/api")
public class CarAttrResource {

    private final Logger log = LoggerFactory.getLogger(CarAttrResource.class);

    private static final String ENTITY_NAME = "carAttr";

    private final CarAttrService carAttrService;

    public CarAttrResource(CarAttrService carAttrService) {
        this.carAttrService = carAttrService;
    }

    /**
     * POST  /car-attrs : Create a new carAttr.
     *
     * @param carAttr the carAttr to create
     * @return the ResponseEntity with status 201 (Created) and with body the new carAttr, or with status 400 (Bad Request) if the carAttr has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/car-attrs")
    @Timed
    public ResponseEntity<CarAttr> createCarAttr(@Valid @RequestBody CarAttr carAttr) throws URISyntaxException {
        log.debug("REST request to save CarAttr : {}", carAttr);
        if (carAttr.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new carAttr cannot already have an ID")).body(null);
        }
        CarAttr result = carAttrService.save(carAttr);
        return ResponseEntity.created(new URI("/api/car-attrs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /car-attrs : Updates an existing carAttr.
     *
     * @param carAttr the carAttr to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated carAttr,
     * or with status 400 (Bad Request) if the carAttr is not valid,
     * or with status 500 (Internal Server Error) if the carAttr couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/car-attrs")
    @Timed
    public ResponseEntity<CarAttr> updateCarAttr(@Valid @RequestBody CarAttr carAttr) throws URISyntaxException {
        log.debug("REST request to update CarAttr : {}", carAttr);
        if (carAttr.getId() == null) {
            return createCarAttr(carAttr);
        }
        CarAttr result = carAttrService.save(carAttr);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, carAttr.getId().toString()))
            .body(result);
    }

    /**
     * GET  /car-attrs : get all the carAttrs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of carAttrs in body
     */
    @GetMapping("/car-attrs")
    @Timed
    public List<CarAttr> getAllCarAttrs() {
        log.debug("REST request to get all CarAttrs");
        return carAttrService.findAll();
    }

    /**
     * GET  /car-attrs/:id : get the "id" carAttr.
     *
     * @param id the id of the carAttr to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the carAttr, or with status 404 (Not Found)
     */
    @GetMapping("/car-attrs/{id}")
    @Timed
    public ResponseEntity<CarAttr> getCarAttr(@PathVariable Long id) {
        log.debug("REST request to get CarAttr : {}", id);
        CarAttr carAttr = carAttrService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(carAttr));
    }

    /**
     * DELETE  /car-attrs/:id : delete the "id" carAttr.
     *
     * @param id the id of the carAttr to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/car-attrs/{id}")
    @Timed
    public ResponseEntity<Void> deleteCarAttr(@PathVariable Long id) {
        log.debug("REST request to delete CarAttr : {}", id);
        carAttrService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/car-attrs?query=:query : search for the carAttr corresponding
     * to the query.
     *
     * @param query the query of the carAttr search
     * @return the result of the search
     */
    @GetMapping("/_search/car-attrs")
    @Timed
    public List<CarAttr> searchCarAttrs(@RequestParam String query) {
        log.debug("REST request to search CarAttrs for query {}", query);
        return carAttrService.search(query);
    }

}
