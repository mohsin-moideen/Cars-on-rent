package org.carsonrent.rentals.web.rest;

import org.carsonrent.rentals.CarsOnRentApp;

import org.carsonrent.rentals.domain.CarPrice;
import org.carsonrent.rentals.repository.CarPriceRepository;
import org.carsonrent.rentals.service.CarPriceService;
import org.carsonrent.rentals.repository.search.CarPriceSearchRepository;
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
 * Test class for the CarPriceResource REST controller.
 *
 * @see CarPriceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CarsOnRentApp.class)
public class CarPriceResourceIntTest {

    private static final Double DEFAULT_PRICE_PER_HOUR = 1D;
    private static final Double UPDATED_PRICE_PER_HOUR = 2D;

    private static final Double DEFAULT_DEPOSIT_AMOUNT = 1D;
    private static final Double UPDATED_DEPOSIT_AMOUNT = 2D;

    @Autowired
    private CarPriceRepository carPriceRepository;

    @Autowired
    private CarPriceService carPriceService;

    @Autowired
    private CarPriceSearchRepository carPriceSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCarPriceMockMvc;

    private CarPrice carPrice;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CarPriceResource carPriceResource = new CarPriceResource(carPriceService);
        this.restCarPriceMockMvc = MockMvcBuilders.standaloneSetup(carPriceResource)
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
    public static CarPrice createEntity(EntityManager em) {
        CarPrice carPrice = new CarPrice()
            .pricePerHour(DEFAULT_PRICE_PER_HOUR)
            .depositAmount(DEFAULT_DEPOSIT_AMOUNT);
        return carPrice;
    }

    @Before
    public void initTest() {
        carPriceSearchRepository.deleteAll();
        carPrice = createEntity(em);
    }

    @Test
    @Transactional
    public void createCarPrice() throws Exception {
        int databaseSizeBeforeCreate = carPriceRepository.findAll().size();

        // Create the CarPrice
        restCarPriceMockMvc.perform(post("/api/car-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carPrice)))
            .andExpect(status().isCreated());

        // Validate the CarPrice in the database
        List<CarPrice> carPriceList = carPriceRepository.findAll();
        assertThat(carPriceList).hasSize(databaseSizeBeforeCreate + 1);
        CarPrice testCarPrice = carPriceList.get(carPriceList.size() - 1);
        assertThat(testCarPrice.getPricePerHour()).isEqualTo(DEFAULT_PRICE_PER_HOUR);
        assertThat(testCarPrice.getDepositAmount()).isEqualTo(DEFAULT_DEPOSIT_AMOUNT);

        // Validate the CarPrice in Elasticsearch
        CarPrice carPriceEs = carPriceSearchRepository.findOne(testCarPrice.getId());
        assertThat(carPriceEs).isEqualToComparingFieldByField(testCarPrice);
    }

    @Test
    @Transactional
    public void createCarPriceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = carPriceRepository.findAll().size();

        // Create the CarPrice with an existing ID
        carPrice.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCarPriceMockMvc.perform(post("/api/car-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carPrice)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CarPrice> carPriceList = carPriceRepository.findAll();
        assertThat(carPriceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPricePerHourIsRequired() throws Exception {
        int databaseSizeBeforeTest = carPriceRepository.findAll().size();
        // set the field null
        carPrice.setPricePerHour(null);

        // Create the CarPrice, which fails.

        restCarPriceMockMvc.perform(post("/api/car-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carPrice)))
            .andExpect(status().isBadRequest());

        List<CarPrice> carPriceList = carPriceRepository.findAll();
        assertThat(carPriceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDepositAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = carPriceRepository.findAll().size();
        // set the field null
        carPrice.setDepositAmount(null);

        // Create the CarPrice, which fails.

        restCarPriceMockMvc.perform(post("/api/car-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carPrice)))
            .andExpect(status().isBadRequest());

        List<CarPrice> carPriceList = carPriceRepository.findAll();
        assertThat(carPriceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCarPrices() throws Exception {
        // Initialize the database
        carPriceRepository.saveAndFlush(carPrice);

        // Get all the carPriceList
        restCarPriceMockMvc.perform(get("/api/car-prices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carPrice.getId().intValue())))
            .andExpect(jsonPath("$.[*].pricePerHour").value(hasItem(DEFAULT_PRICE_PER_HOUR.doubleValue())))
            .andExpect(jsonPath("$.[*].depositAmount").value(hasItem(DEFAULT_DEPOSIT_AMOUNT.doubleValue())));
    }

    @Test
    @Transactional
    public void getCarPrice() throws Exception {
        // Initialize the database
        carPriceRepository.saveAndFlush(carPrice);

        // Get the carPrice
        restCarPriceMockMvc.perform(get("/api/car-prices/{id}", carPrice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(carPrice.getId().intValue()))
            .andExpect(jsonPath("$.pricePerHour").value(DEFAULT_PRICE_PER_HOUR.doubleValue()))
            .andExpect(jsonPath("$.depositAmount").value(DEFAULT_DEPOSIT_AMOUNT.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCarPrice() throws Exception {
        // Get the carPrice
        restCarPriceMockMvc.perform(get("/api/car-prices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCarPrice() throws Exception {
        // Initialize the database
        carPriceService.save(carPrice);

        int databaseSizeBeforeUpdate = carPriceRepository.findAll().size();

        // Update the carPrice
        CarPrice updatedCarPrice = carPriceRepository.findOne(carPrice.getId());
        updatedCarPrice
            .pricePerHour(UPDATED_PRICE_PER_HOUR)
            .depositAmount(UPDATED_DEPOSIT_AMOUNT);

        restCarPriceMockMvc.perform(put("/api/car-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCarPrice)))
            .andExpect(status().isOk());

        // Validate the CarPrice in the database
        List<CarPrice> carPriceList = carPriceRepository.findAll();
        assertThat(carPriceList).hasSize(databaseSizeBeforeUpdate);
        CarPrice testCarPrice = carPriceList.get(carPriceList.size() - 1);
        assertThat(testCarPrice.getPricePerHour()).isEqualTo(UPDATED_PRICE_PER_HOUR);
        assertThat(testCarPrice.getDepositAmount()).isEqualTo(UPDATED_DEPOSIT_AMOUNT);

        // Validate the CarPrice in Elasticsearch
        CarPrice carPriceEs = carPriceSearchRepository.findOne(testCarPrice.getId());
        assertThat(carPriceEs).isEqualToComparingFieldByField(testCarPrice);
    }

    @Test
    @Transactional
    public void updateNonExistingCarPrice() throws Exception {
        int databaseSizeBeforeUpdate = carPriceRepository.findAll().size();

        // Create the CarPrice

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCarPriceMockMvc.perform(put("/api/car-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carPrice)))
            .andExpect(status().isCreated());

        // Validate the CarPrice in the database
        List<CarPrice> carPriceList = carPriceRepository.findAll();
        assertThat(carPriceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCarPrice() throws Exception {
        // Initialize the database
        carPriceService.save(carPrice);

        int databaseSizeBeforeDelete = carPriceRepository.findAll().size();

        // Get the carPrice
        restCarPriceMockMvc.perform(delete("/api/car-prices/{id}", carPrice.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean carPriceExistsInEs = carPriceSearchRepository.exists(carPrice.getId());
        assertThat(carPriceExistsInEs).isFalse();

        // Validate the database is empty
        List<CarPrice> carPriceList = carPriceRepository.findAll();
        assertThat(carPriceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCarPrice() throws Exception {
        // Initialize the database
        carPriceService.save(carPrice);

        // Search the carPrice
        restCarPriceMockMvc.perform(get("/api/_search/car-prices?query=id:" + carPrice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carPrice.getId().intValue())))
            .andExpect(jsonPath("$.[*].pricePerHour").value(hasItem(DEFAULT_PRICE_PER_HOUR.doubleValue())))
            .andExpect(jsonPath("$.[*].depositAmount").value(hasItem(DEFAULT_DEPOSIT_AMOUNT.doubleValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CarPrice.class);
        CarPrice carPrice1 = new CarPrice();
        carPrice1.setId(1L);
        CarPrice carPrice2 = new CarPrice();
        carPrice2.setId(carPrice1.getId());
        assertThat(carPrice1).isEqualTo(carPrice2);
        carPrice2.setId(2L);
        assertThat(carPrice1).isNotEqualTo(carPrice2);
        carPrice1.setId(null);
        assertThat(carPrice1).isNotEqualTo(carPrice2);
    }
}
