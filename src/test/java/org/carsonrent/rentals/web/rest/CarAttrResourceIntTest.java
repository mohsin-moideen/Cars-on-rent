package org.carsonrent.rentals.web.rest;

import org.carsonrent.rentals.CarsOnRentApp;

import org.carsonrent.rentals.domain.CarAttr;
import org.carsonrent.rentals.repository.CarAttrRepository;
import org.carsonrent.rentals.service.CarAttrService;
import org.carsonrent.rentals.repository.search.CarAttrSearchRepository;
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
 * Test class for the CarAttrResource REST controller.
 *
 * @see CarAttrResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CarsOnRentApp.class)
public class CarAttrResourceIntTest {

    private static final String DEFAULT_ATTRNAME = "AAAAAAAAAA";
    private static final String UPDATED_ATTRNAME = "BBBBBBBBBB";

    private static final String DEFAULT_ATTRVAL = "AAAAAAAAAA";
    private static final String UPDATED_ATTRVAL = "BBBBBBBBBB";

    @Autowired
    private CarAttrRepository carAttrRepository;

    @Autowired
    private CarAttrService carAttrService;

    @Autowired
    private CarAttrSearchRepository carAttrSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCarAttrMockMvc;

    private CarAttr carAttr;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CarAttrResource carAttrResource = new CarAttrResource(carAttrService);
        this.restCarAttrMockMvc = MockMvcBuilders.standaloneSetup(carAttrResource)
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
    public static CarAttr createEntity(EntityManager em) {
        CarAttr carAttr = new CarAttr()
            .attrname(DEFAULT_ATTRNAME)
            .attrval(DEFAULT_ATTRVAL);
        return carAttr;
    }

    @Before
    public void initTest() {
        carAttrSearchRepository.deleteAll();
        carAttr = createEntity(em);
    }

    @Test
    @Transactional
    public void createCarAttr() throws Exception {
        int databaseSizeBeforeCreate = carAttrRepository.findAll().size();

        // Create the CarAttr
        restCarAttrMockMvc.perform(post("/api/car-attrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carAttr)))
            .andExpect(status().isCreated());

        // Validate the CarAttr in the database
        List<CarAttr> carAttrList = carAttrRepository.findAll();
        assertThat(carAttrList).hasSize(databaseSizeBeforeCreate + 1);
        CarAttr testCarAttr = carAttrList.get(carAttrList.size() - 1);
        assertThat(testCarAttr.getAttrname()).isEqualTo(DEFAULT_ATTRNAME);
        assertThat(testCarAttr.getAttrval()).isEqualTo(DEFAULT_ATTRVAL);

        // Validate the CarAttr in Elasticsearch
        CarAttr carAttrEs = carAttrSearchRepository.findOne(testCarAttr.getId());
        assertThat(carAttrEs).isEqualToComparingFieldByField(testCarAttr);
    }

    @Test
    @Transactional
    public void createCarAttrWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = carAttrRepository.findAll().size();

        // Create the CarAttr with an existing ID
        carAttr.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCarAttrMockMvc.perform(post("/api/car-attrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carAttr)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CarAttr> carAttrList = carAttrRepository.findAll();
        assertThat(carAttrList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAttrnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = carAttrRepository.findAll().size();
        // set the field null
        carAttr.setAttrname(null);

        // Create the CarAttr, which fails.

        restCarAttrMockMvc.perform(post("/api/car-attrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carAttr)))
            .andExpect(status().isBadRequest());

        List<CarAttr> carAttrList = carAttrRepository.findAll();
        assertThat(carAttrList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAttrvalIsRequired() throws Exception {
        int databaseSizeBeforeTest = carAttrRepository.findAll().size();
        // set the field null
        carAttr.setAttrval(null);

        // Create the CarAttr, which fails.

        restCarAttrMockMvc.perform(post("/api/car-attrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carAttr)))
            .andExpect(status().isBadRequest());

        List<CarAttr> carAttrList = carAttrRepository.findAll();
        assertThat(carAttrList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCarAttrs() throws Exception {
        // Initialize the database
        carAttrRepository.saveAndFlush(carAttr);

        // Get all the carAttrList
        restCarAttrMockMvc.perform(get("/api/car-attrs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carAttr.getId().intValue())))
            .andExpect(jsonPath("$.[*].attrname").value(hasItem(DEFAULT_ATTRNAME.toString())))
            .andExpect(jsonPath("$.[*].attrval").value(hasItem(DEFAULT_ATTRVAL.toString())));
    }

    @Test
    @Transactional
    public void getCarAttr() throws Exception {
        // Initialize the database
        carAttrRepository.saveAndFlush(carAttr);

        // Get the carAttr
        restCarAttrMockMvc.perform(get("/api/car-attrs/{id}", carAttr.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(carAttr.getId().intValue()))
            .andExpect(jsonPath("$.attrname").value(DEFAULT_ATTRNAME.toString()))
            .andExpect(jsonPath("$.attrval").value(DEFAULT_ATTRVAL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCarAttr() throws Exception {
        // Get the carAttr
        restCarAttrMockMvc.perform(get("/api/car-attrs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCarAttr() throws Exception {
        // Initialize the database
        carAttrService.save(carAttr);

        int databaseSizeBeforeUpdate = carAttrRepository.findAll().size();

        // Update the carAttr
        CarAttr updatedCarAttr = carAttrRepository.findOne(carAttr.getId());
        updatedCarAttr
            .attrname(UPDATED_ATTRNAME)
            .attrval(UPDATED_ATTRVAL);

        restCarAttrMockMvc.perform(put("/api/car-attrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCarAttr)))
            .andExpect(status().isOk());

        // Validate the CarAttr in the database
        List<CarAttr> carAttrList = carAttrRepository.findAll();
        assertThat(carAttrList).hasSize(databaseSizeBeforeUpdate);
        CarAttr testCarAttr = carAttrList.get(carAttrList.size() - 1);
        assertThat(testCarAttr.getAttrname()).isEqualTo(UPDATED_ATTRNAME);
        assertThat(testCarAttr.getAttrval()).isEqualTo(UPDATED_ATTRVAL);

        // Validate the CarAttr in Elasticsearch
        CarAttr carAttrEs = carAttrSearchRepository.findOne(testCarAttr.getId());
        assertThat(carAttrEs).isEqualToComparingFieldByField(testCarAttr);
    }

    @Test
    @Transactional
    public void updateNonExistingCarAttr() throws Exception {
        int databaseSizeBeforeUpdate = carAttrRepository.findAll().size();

        // Create the CarAttr

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCarAttrMockMvc.perform(put("/api/car-attrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carAttr)))
            .andExpect(status().isCreated());

        // Validate the CarAttr in the database
        List<CarAttr> carAttrList = carAttrRepository.findAll();
        assertThat(carAttrList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCarAttr() throws Exception {
        // Initialize the database
        carAttrService.save(carAttr);

        int databaseSizeBeforeDelete = carAttrRepository.findAll().size();

        // Get the carAttr
        restCarAttrMockMvc.perform(delete("/api/car-attrs/{id}", carAttr.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean carAttrExistsInEs = carAttrSearchRepository.exists(carAttr.getId());
        assertThat(carAttrExistsInEs).isFalse();

        // Validate the database is empty
        List<CarAttr> carAttrList = carAttrRepository.findAll();
        assertThat(carAttrList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCarAttr() throws Exception {
        // Initialize the database
        carAttrService.save(carAttr);

        // Search the carAttr
        restCarAttrMockMvc.perform(get("/api/_search/car-attrs?query=id:" + carAttr.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carAttr.getId().intValue())))
            .andExpect(jsonPath("$.[*].attrname").value(hasItem(DEFAULT_ATTRNAME.toString())))
            .andExpect(jsonPath("$.[*].attrval").value(hasItem(DEFAULT_ATTRVAL.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CarAttr.class);
        CarAttr carAttr1 = new CarAttr();
        carAttr1.setId(1L);
        CarAttr carAttr2 = new CarAttr();
        carAttr2.setId(carAttr1.getId());
        assertThat(carAttr1).isEqualTo(carAttr2);
        carAttr2.setId(2L);
        assertThat(carAttr1).isNotEqualTo(carAttr2);
        carAttr1.setId(null);
        assertThat(carAttr1).isNotEqualTo(carAttr2);
    }
}
