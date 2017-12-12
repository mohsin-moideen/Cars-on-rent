package org.carsonrent.rentals.web.rest;

import org.carsonrent.rentals.CarsOnRentApp;

import org.carsonrent.rentals.domain.Bookings;
import org.carsonrent.rentals.repository.BookingsRepository;
import org.carsonrent.rentals.service.BookingsService;
import org.carsonrent.rentals.repository.search.BookingsSearchRepository;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static org.carsonrent.rentals.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BookingsResource REST controller.
 *
 * @see BookingsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CarsOnRentApp.class)
public class BookingsResourceIntTest {

    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    @Autowired
    private BookingsRepository bookingsRepository;

    @Autowired
    private BookingsService bookingsService;

    @Autowired
    private BookingsSearchRepository bookingsSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBookingsMockMvc;

    private Bookings bookings;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BookingsResource bookingsResource = new BookingsResource(bookingsService);
        this.restBookingsMockMvc = MockMvcBuilders.standaloneSetup(bookingsResource)
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
    public static Bookings createEntity(EntityManager em) {
        Bookings bookings = new Bookings()
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .status(DEFAULT_STATUS);
        return bookings;
    }

    @Before
    public void initTest() {
        bookingsSearchRepository.deleteAll();
        bookings = createEntity(em);
    }

    @Test
    @Transactional
    public void createBookings() throws Exception {
        int databaseSizeBeforeCreate = bookingsRepository.findAll().size();

        // Create the Bookings
        restBookingsMockMvc.perform(post("/api/bookings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookings)))
            .andExpect(status().isCreated());

        // Validate the Bookings in the database
        List<Bookings> bookingsList = bookingsRepository.findAll();
        assertThat(bookingsList).hasSize(databaseSizeBeforeCreate + 1);
        Bookings testBookings = bookingsList.get(bookingsList.size() - 1);
        assertThat(testBookings.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testBookings.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testBookings.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the Bookings in Elasticsearch
        Bookings bookingsEs = bookingsSearchRepository.findOne(testBookings.getId());
        assertThat(bookingsEs).isEqualToComparingFieldByField(testBookings);
    }

    @Test
    @Transactional
    public void createBookingsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bookingsRepository.findAll().size();

        // Create the Bookings with an existing ID
        bookings.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBookingsMockMvc.perform(post("/api/bookings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookings)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Bookings> bookingsList = bookingsRepository.findAll();
        assertThat(bookingsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookingsRepository.findAll().size();
        // set the field null
        bookings.setStartDate(null);

        // Create the Bookings, which fails.

        restBookingsMockMvc.perform(post("/api/bookings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookings)))
            .andExpect(status().isBadRequest());

        List<Bookings> bookingsList = bookingsRepository.findAll();
        assertThat(bookingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookingsRepository.findAll().size();
        // set the field null
        bookings.setEndDate(null);

        // Create the Bookings, which fails.

        restBookingsMockMvc.perform(post("/api/bookings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookings)))
            .andExpect(status().isBadRequest());

        List<Bookings> bookingsList = bookingsRepository.findAll();
        assertThat(bookingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookingsRepository.findAll().size();
        // set the field null
        bookings.setStatus(null);

        // Create the Bookings, which fails.

        restBookingsMockMvc.perform(post("/api/bookings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookings)))
            .andExpect(status().isBadRequest());

        List<Bookings> bookingsList = bookingsRepository.findAll();
        assertThat(bookingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBookings() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList
        restBookingsMockMvc.perform(get("/api/bookings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bookings.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(sameInstant(DEFAULT_END_DATE))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getBookings() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get the bookings
        restBookingsMockMvc.perform(get("/api/bookings/{id}", bookings.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bookings.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(sameInstant(DEFAULT_START_DATE)))
            .andExpect(jsonPath("$.endDate").value(sameInstant(DEFAULT_END_DATE)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBookings() throws Exception {
        // Get the bookings
        restBookingsMockMvc.perform(get("/api/bookings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBookings() throws Exception {
        // Initialize the database
        bookingsService.save(bookings);

        int databaseSizeBeforeUpdate = bookingsRepository.findAll().size();

        // Update the bookings
        Bookings updatedBookings = bookingsRepository.findOne(bookings.getId());
        updatedBookings
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .status(UPDATED_STATUS);

        restBookingsMockMvc.perform(put("/api/bookings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBookings)))
            .andExpect(status().isOk());

        // Validate the Bookings in the database
        List<Bookings> bookingsList = bookingsRepository.findAll();
        assertThat(bookingsList).hasSize(databaseSizeBeforeUpdate);
        Bookings testBookings = bookingsList.get(bookingsList.size() - 1);
        assertThat(testBookings.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testBookings.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testBookings.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the Bookings in Elasticsearch
        Bookings bookingsEs = bookingsSearchRepository.findOne(testBookings.getId());
        assertThat(bookingsEs).isEqualToComparingFieldByField(testBookings);
    }

    @Test
    @Transactional
    public void updateNonExistingBookings() throws Exception {
        int databaseSizeBeforeUpdate = bookingsRepository.findAll().size();

        // Create the Bookings

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBookingsMockMvc.perform(put("/api/bookings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookings)))
            .andExpect(status().isCreated());

        // Validate the Bookings in the database
        List<Bookings> bookingsList = bookingsRepository.findAll();
        assertThat(bookingsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBookings() throws Exception {
        // Initialize the database
        bookingsService.save(bookings);

        int databaseSizeBeforeDelete = bookingsRepository.findAll().size();

        // Get the bookings
        restBookingsMockMvc.perform(delete("/api/bookings/{id}", bookings.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean bookingsExistsInEs = bookingsSearchRepository.exists(bookings.getId());
        assertThat(bookingsExistsInEs).isFalse();

        // Validate the database is empty
        List<Bookings> bookingsList = bookingsRepository.findAll();
        assertThat(bookingsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchBookings() throws Exception {
        // Initialize the database
        bookingsService.save(bookings);

        // Search the bookings
        restBookingsMockMvc.perform(get("/api/_search/bookings?query=id:" + bookings.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bookings.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(sameInstant(DEFAULT_END_DATE))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bookings.class);
        Bookings bookings1 = new Bookings();
        bookings1.setId(1L);
        Bookings bookings2 = new Bookings();
        bookings2.setId(bookings1.getId());
        assertThat(bookings1).isEqualTo(bookings2);
        bookings2.setId(2L);
        assertThat(bookings1).isNotEqualTo(bookings2);
        bookings1.setId(null);
        assertThat(bookings1).isNotEqualTo(bookings2);
    }
}
