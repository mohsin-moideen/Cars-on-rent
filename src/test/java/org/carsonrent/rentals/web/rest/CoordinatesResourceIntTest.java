package org.carsonrent.rentals.web.rest;

import org.carsonrent.rentals.CarsOnRentApp;

import org.carsonrent.rentals.domain.Coordinates;
import org.carsonrent.rentals.repository.CoordinatesRepository;
import org.carsonrent.rentals.service.CoordinatesService;
import org.carsonrent.rentals.repository.search.CoordinatesSearchRepository;
import org.carsonrent.rentals.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CoordinatesResource REST controller.
 *
 * @see CoordinatesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CarsOnRentApp.class)
public class CoordinatesResourceIntTest {

    private static final Float DEFAULT_LOGNITUDE = 1F;
    private static final Float UPDATED_LOGNITUDE = 2F;

    private static final Float DEFAULT_LATITUDE = 1F;
    private static final Float UPDATED_LATITUDE = 2F;

    @Autowired
    private CoordinatesRepository coordinatesRepository;

    @Autowired
    private CoordinatesService coordinatesService;

    @Autowired
    private CoordinatesSearchRepository coordinatesSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCoordinatesMockMvc;

    private Coordinates coordinates;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CoordinatesResource coordinatesResource = new CoordinatesResource(coordinatesService);
        this.restCoordinatesMockMvc = MockMvcBuilders.standaloneSetup(coordinatesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Coordinates createEntity(EntityManager em) {
        Coordinates coordinates = new Coordinates()
            .lognitude(DEFAULT_LOGNITUDE)
            .latitude(DEFAULT_LATITUDE);
        return coordinates;
    }

    @Before
    public void initTest() {
        coordinatesSearchRepository.deleteAll();
        coordinates = createEntity(em);
    }

    @Test
    @Transactional
    public void createCoordinates() throws Exception {
        int databaseSizeBeforeCreate = coordinatesRepository.findAll().size();

        // Create the Coordinates
        restCoordinatesMockMvc.perform(post("/api/coordinates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coordinates)))
            .andExpect(status().isCreated());

        // Validate the Coordinates in the database
        List<Coordinates> coordinatesList = coordinatesRepository.findAll();
        assertThat(coordinatesList).hasSize(databaseSizeBeforeCreate + 1);
        Coordinates testCoordinates = coordinatesList.get(coordinatesList.size() - 1);
        assertThat(testCoordinates.getLognitude()).isEqualTo(DEFAULT_LOGNITUDE);
        assertThat(testCoordinates.getLatitude()).isEqualTo(DEFAULT_LATITUDE);

        // Validate the Coordinates in Elasticsearch
        Coordinates coordinatesEs = coordinatesSearchRepository.findOne(testCoordinates.getId());
        assertThat(coordinatesEs).isEqualToComparingFieldByField(testCoordinates);
    }

    @Test
    @Transactional
    public void createCoordinatesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = coordinatesRepository.findAll().size();

        // Create the Coordinates with an existing ID
        coordinates.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCoordinatesMockMvc.perform(post("/api/coordinates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coordinates)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Coordinates> coordinatesList = coordinatesRepository.findAll();
        assertThat(coordinatesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLognitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = coordinatesRepository.findAll().size();
        // set the field null
        coordinates.setLognitude(null);

        // Create the Coordinates, which fails.

        restCoordinatesMockMvc.perform(post("/api/coordinates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coordinates)))
            .andExpect(status().isBadRequest());

        List<Coordinates> coordinatesList = coordinatesRepository.findAll();
        assertThat(coordinatesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLatitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = coordinatesRepository.findAll().size();
        // set the field null
        coordinates.setLatitude(null);

        // Create the Coordinates, which fails.

        restCoordinatesMockMvc.perform(post("/api/coordinates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coordinates)))
            .andExpect(status().isBadRequest());

        List<Coordinates> coordinatesList = coordinatesRepository.findAll();
        assertThat(coordinatesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCoordinates() throws Exception {
        // Initialize the database
        coordinatesRepository.saveAndFlush(coordinates);

        // Get all the coordinatesList
        restCoordinatesMockMvc.perform(get("/api/coordinates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(coordinates.getId().intValue())))
            .andExpect(jsonPath("$.[*].lognitude").value(hasItem(DEFAULT_LOGNITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())));
    }

    @Test
    @Transactional
    public void getCoordinates() throws Exception {
        // Initialize the database
        coordinatesRepository.saveAndFlush(coordinates);

        // Get the coordinates
        restCoordinatesMockMvc.perform(get("/api/coordinates/{id}", coordinates.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(coordinates.getId().intValue()))
            .andExpect(jsonPath("$.lognitude").value(DEFAULT_LOGNITUDE.doubleValue()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCoordinates() throws Exception {
        // Get the coordinates
        restCoordinatesMockMvc.perform(get("/api/coordinates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCoordinates() throws Exception {
        // Initialize the database
        coordinatesService.save(coordinates);

        int databaseSizeBeforeUpdate = coordinatesRepository.findAll().size();

        // Update the coordinates
        Coordinates updatedCoordinates = coordinatesRepository.findOne(coordinates.getId());
        updatedCoordinates
            .lognitude(UPDATED_LOGNITUDE)
            .latitude(UPDATED_LATITUDE);

        restCoordinatesMockMvc.perform(put("/api/coordinates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCoordinates)))
            .andExpect(status().isOk());

        // Validate the Coordinates in the database
        List<Coordinates> coordinatesList = coordinatesRepository.findAll();
        assertThat(coordinatesList).hasSize(databaseSizeBeforeUpdate);
        Coordinates testCoordinates = coordinatesList.get(coordinatesList.size() - 1);
        assertThat(testCoordinates.getLognitude()).isEqualTo(UPDATED_LOGNITUDE);
        assertThat(testCoordinates.getLatitude()).isEqualTo(UPDATED_LATITUDE);

        // Validate the Coordinates in Elasticsearch
        Coordinates coordinatesEs = coordinatesSearchRepository.findOne(testCoordinates.getId());
        assertThat(coordinatesEs).isEqualToComparingFieldByField(testCoordinates);
    }

    @Test
    @Transactional
    public void updateNonExistingCoordinates() throws Exception {
        int databaseSizeBeforeUpdate = coordinatesRepository.findAll().size();

        // Create the Coordinates

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCoordinatesMockMvc.perform(put("/api/coordinates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coordinates)))
            .andExpect(status().isCreated());

        // Validate the Coordinates in the database
        List<Coordinates> coordinatesList = coordinatesRepository.findAll();
        assertThat(coordinatesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCoordinates() throws Exception {
        // Initialize the database
        coordinatesService.save(coordinates);

        int databaseSizeBeforeDelete = coordinatesRepository.findAll().size();

        // Get the coordinates
        restCoordinatesMockMvc.perform(delete("/api/coordinates/{id}", coordinates.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean coordinatesExistsInEs = coordinatesSearchRepository.exists(coordinates.getId());
        assertThat(coordinatesExistsInEs).isFalse();

        // Validate the database is empty
        List<Coordinates> coordinatesList = coordinatesRepository.findAll();
        assertThat(coordinatesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCoordinates() throws Exception {
        // Initialize the database
        coordinatesService.save(coordinates);

        // Search the coordinates
        restCoordinatesMockMvc.perform(get("/api/_search/coordinates?query=id:" + coordinates.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(coordinates.getId().intValue())))
            .andExpect(jsonPath("$.[*].lognitude").value(hasItem(DEFAULT_LOGNITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Coordinates.class);
        Coordinates coordinates1 = new Coordinates();
        coordinates1.setId(1L);
        Coordinates coordinates2 = new Coordinates();
        coordinates2.setId(coordinates1.getId());
        assertThat(coordinates1).isEqualTo(coordinates2);
        coordinates2.setId(2L);
        assertThat(coordinates1).isNotEqualTo(coordinates2);
        coordinates1.setId(null);
        assertThat(coordinates1).isNotEqualTo(coordinates2);
    }
}
