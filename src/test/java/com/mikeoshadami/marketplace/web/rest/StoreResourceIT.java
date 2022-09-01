package com.mikeoshadami.marketplace.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mikeoshadami.marketplace.IntegrationTest;
import com.mikeoshadami.marketplace.domain.Product;
import com.mikeoshadami.marketplace.domain.ProductCategory;
import com.mikeoshadami.marketplace.domain.Store;
import com.mikeoshadami.marketplace.domain.StoreCategory;
import com.mikeoshadami.marketplace.domain.enumeration.Status;
import com.mikeoshadami.marketplace.repository.StoreRepository;
import com.mikeoshadami.marketplace.service.criteria.StoreCriteria;
import com.mikeoshadami.marketplace.service.dto.StoreDTO;
import com.mikeoshadami.marketplace.service.mapper.StoreMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link StoreResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StoreResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_STORE_URL = "AAAAAAAAAA";
    private static final String UPDATED_STORE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_LOGO_URL = "AAAAAAAAAA";
    private static final String UPDATED_LOGO_URL = "BBBBBBBBBB";

    private static final String DEFAULT_BANNER_URL = "AAAAAAAAAA";
    private static final String UPDATED_BANNER_URL = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_ALIAS = "AAAAAAAAAA";
    private static final String UPDATED_ALIAS = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.ACTIVE;
    private static final Status UPDATED_STATUS = Status.INACTIVE;

    private static final Instant DEFAULT_DATE_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/stores";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private StoreMapper storeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStoreMockMvc;

    private Store store;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Store createEntity(EntityManager em) {
        Store store = new Store()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .storeUrl(DEFAULT_STORE_URL)
            .logoUrl(DEFAULT_LOGO_URL)
            .bannerUrl(DEFAULT_BANNER_URL)
            .contactEmail(DEFAULT_CONTACT_EMAIL)
            .contactPhone(DEFAULT_CONTACT_PHONE)
            .contactAddress(DEFAULT_CONTACT_ADDRESS)
            .alias(DEFAULT_ALIAS)
            .status(DEFAULT_STATUS)
            .dateCreated(DEFAULT_DATE_CREATED);
        return store;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Store createUpdatedEntity(EntityManager em) {
        Store store = new Store()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .storeUrl(UPDATED_STORE_URL)
            .logoUrl(UPDATED_LOGO_URL)
            .bannerUrl(UPDATED_BANNER_URL)
            .contactEmail(UPDATED_CONTACT_EMAIL)
            .contactPhone(UPDATED_CONTACT_PHONE)
            .contactAddress(UPDATED_CONTACT_ADDRESS)
            .alias(UPDATED_ALIAS)
            .status(UPDATED_STATUS)
            .dateCreated(UPDATED_DATE_CREATED);
        return store;
    }

    @BeforeEach
    public void initTest() {
        store = createEntity(em);
    }

    @Test
    @Transactional
    void createStore() throws Exception {
        int databaseSizeBeforeCreate = storeRepository.findAll().size();
        // Create the Store
        StoreDTO storeDTO = storeMapper.toDto(store);
        restStoreMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(storeDTO)))
            .andExpect(status().isCreated());

        // Validate the Store in the database
        List<Store> storeList = storeRepository.findAll();
        assertThat(storeList).hasSize(databaseSizeBeforeCreate + 1);
        Store testStore = storeList.get(storeList.size() - 1);
        assertThat(testStore.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStore.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testStore.getStoreUrl()).isEqualTo(DEFAULT_STORE_URL);
        assertThat(testStore.getLogoUrl()).isEqualTo(DEFAULT_LOGO_URL);
        assertThat(testStore.getBannerUrl()).isEqualTo(DEFAULT_BANNER_URL);
        assertThat(testStore.getContactEmail()).isEqualTo(DEFAULT_CONTACT_EMAIL);
        assertThat(testStore.getContactPhone()).isEqualTo(DEFAULT_CONTACT_PHONE);
        assertThat(testStore.getContactAddress()).isEqualTo(DEFAULT_CONTACT_ADDRESS);
        assertThat(testStore.getAlias()).isEqualTo(DEFAULT_ALIAS);
        assertThat(testStore.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testStore.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
    }

    @Test
    @Transactional
    void createStoreWithExistingId() throws Exception {
        // Create the Store with an existing ID
        store.setId(1L);
        StoreDTO storeDTO = storeMapper.toDto(store);

        int databaseSizeBeforeCreate = storeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStoreMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(storeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Store in the database
        List<Store> storeList = storeRepository.findAll();
        assertThat(storeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = storeRepository.findAll().size();
        // set the field null
        store.setName(null);

        // Create the Store, which fails.
        StoreDTO storeDTO = storeMapper.toDto(store);

        restStoreMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(storeDTO)))
            .andExpect(status().isBadRequest());

        List<Store> storeList = storeRepository.findAll();
        assertThat(storeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAliasIsRequired() throws Exception {
        int databaseSizeBeforeTest = storeRepository.findAll().size();
        // set the field null
        store.setAlias(null);

        // Create the Store, which fails.
        StoreDTO storeDTO = storeMapper.toDto(store);

        restStoreMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(storeDTO)))
            .andExpect(status().isBadRequest());

        List<Store> storeList = storeRepository.findAll();
        assertThat(storeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = storeRepository.findAll().size();
        // set the field null
        store.setStatus(null);

        // Create the Store, which fails.
        StoreDTO storeDTO = storeMapper.toDto(store);

        restStoreMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(storeDTO)))
            .andExpect(status().isBadRequest());

        List<Store> storeList = storeRepository.findAll();
        assertThat(storeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = storeRepository.findAll().size();
        // set the field null
        store.setDateCreated(null);

        // Create the Store, which fails.
        StoreDTO storeDTO = storeMapper.toDto(store);

        restStoreMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(storeDTO)))
            .andExpect(status().isBadRequest());

        List<Store> storeList = storeRepository.findAll();
        assertThat(storeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllStores() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList
        restStoreMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(store.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].storeUrl").value(hasItem(DEFAULT_STORE_URL)))
            .andExpect(jsonPath("$.[*].logoUrl").value(hasItem(DEFAULT_LOGO_URL)))
            .andExpect(jsonPath("$.[*].bannerUrl").value(hasItem(DEFAULT_BANNER_URL)))
            .andExpect(jsonPath("$.[*].contactEmail").value(hasItem(DEFAULT_CONTACT_EMAIL)))
            .andExpect(jsonPath("$.[*].contactPhone").value(hasItem(DEFAULT_CONTACT_PHONE)))
            .andExpect(jsonPath("$.[*].contactAddress").value(hasItem(DEFAULT_CONTACT_ADDRESS)))
            .andExpect(jsonPath("$.[*].alias").value(hasItem(DEFAULT_ALIAS)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())));
    }

    @Test
    @Transactional
    void getStore() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get the store
        restStoreMockMvc
            .perform(get(ENTITY_API_URL_ID, store.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(store.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.storeUrl").value(DEFAULT_STORE_URL))
            .andExpect(jsonPath("$.logoUrl").value(DEFAULT_LOGO_URL))
            .andExpect(jsonPath("$.bannerUrl").value(DEFAULT_BANNER_URL))
            .andExpect(jsonPath("$.contactEmail").value(DEFAULT_CONTACT_EMAIL))
            .andExpect(jsonPath("$.contactPhone").value(DEFAULT_CONTACT_PHONE))
            .andExpect(jsonPath("$.contactAddress").value(DEFAULT_CONTACT_ADDRESS))
            .andExpect(jsonPath("$.alias").value(DEFAULT_ALIAS))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()));
    }

    @Test
    @Transactional
    void getStoresByIdFiltering() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        Long id = store.getId();

        defaultStoreShouldBeFound("id.equals=" + id);
        defaultStoreShouldNotBeFound("id.notEquals=" + id);

        defaultStoreShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultStoreShouldNotBeFound("id.greaterThan=" + id);

        defaultStoreShouldBeFound("id.lessThanOrEqual=" + id);
        defaultStoreShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllStoresByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where name equals to DEFAULT_NAME
        defaultStoreShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the storeList where name equals to UPDATED_NAME
        defaultStoreShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllStoresByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where name not equals to DEFAULT_NAME
        defaultStoreShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the storeList where name not equals to UPDATED_NAME
        defaultStoreShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllStoresByNameIsInShouldWork() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultStoreShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the storeList where name equals to UPDATED_NAME
        defaultStoreShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllStoresByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where name is not null
        defaultStoreShouldBeFound("name.specified=true");

        // Get all the storeList where name is null
        defaultStoreShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllStoresByNameContainsSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where name contains DEFAULT_NAME
        defaultStoreShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the storeList where name contains UPDATED_NAME
        defaultStoreShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllStoresByNameNotContainsSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where name does not contain DEFAULT_NAME
        defaultStoreShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the storeList where name does not contain UPDATED_NAME
        defaultStoreShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllStoresByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where description equals to DEFAULT_DESCRIPTION
        defaultStoreShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the storeList where description equals to UPDATED_DESCRIPTION
        defaultStoreShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllStoresByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where description not equals to DEFAULT_DESCRIPTION
        defaultStoreShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the storeList where description not equals to UPDATED_DESCRIPTION
        defaultStoreShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllStoresByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultStoreShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the storeList where description equals to UPDATED_DESCRIPTION
        defaultStoreShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllStoresByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where description is not null
        defaultStoreShouldBeFound("description.specified=true");

        // Get all the storeList where description is null
        defaultStoreShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllStoresByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where description contains DEFAULT_DESCRIPTION
        defaultStoreShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the storeList where description contains UPDATED_DESCRIPTION
        defaultStoreShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllStoresByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where description does not contain DEFAULT_DESCRIPTION
        defaultStoreShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the storeList where description does not contain UPDATED_DESCRIPTION
        defaultStoreShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllStoresByStoreUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where storeUrl equals to DEFAULT_STORE_URL
        defaultStoreShouldBeFound("storeUrl.equals=" + DEFAULT_STORE_URL);

        // Get all the storeList where storeUrl equals to UPDATED_STORE_URL
        defaultStoreShouldNotBeFound("storeUrl.equals=" + UPDATED_STORE_URL);
    }

    @Test
    @Transactional
    void getAllStoresByStoreUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where storeUrl not equals to DEFAULT_STORE_URL
        defaultStoreShouldNotBeFound("storeUrl.notEquals=" + DEFAULT_STORE_URL);

        // Get all the storeList where storeUrl not equals to UPDATED_STORE_URL
        defaultStoreShouldBeFound("storeUrl.notEquals=" + UPDATED_STORE_URL);
    }

    @Test
    @Transactional
    void getAllStoresByStoreUrlIsInShouldWork() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where storeUrl in DEFAULT_STORE_URL or UPDATED_STORE_URL
        defaultStoreShouldBeFound("storeUrl.in=" + DEFAULT_STORE_URL + "," + UPDATED_STORE_URL);

        // Get all the storeList where storeUrl equals to UPDATED_STORE_URL
        defaultStoreShouldNotBeFound("storeUrl.in=" + UPDATED_STORE_URL);
    }

    @Test
    @Transactional
    void getAllStoresByStoreUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where storeUrl is not null
        defaultStoreShouldBeFound("storeUrl.specified=true");

        // Get all the storeList where storeUrl is null
        defaultStoreShouldNotBeFound("storeUrl.specified=false");
    }

    @Test
    @Transactional
    void getAllStoresByStoreUrlContainsSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where storeUrl contains DEFAULT_STORE_URL
        defaultStoreShouldBeFound("storeUrl.contains=" + DEFAULT_STORE_URL);

        // Get all the storeList where storeUrl contains UPDATED_STORE_URL
        defaultStoreShouldNotBeFound("storeUrl.contains=" + UPDATED_STORE_URL);
    }

    @Test
    @Transactional
    void getAllStoresByStoreUrlNotContainsSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where storeUrl does not contain DEFAULT_STORE_URL
        defaultStoreShouldNotBeFound("storeUrl.doesNotContain=" + DEFAULT_STORE_URL);

        // Get all the storeList where storeUrl does not contain UPDATED_STORE_URL
        defaultStoreShouldBeFound("storeUrl.doesNotContain=" + UPDATED_STORE_URL);
    }

    @Test
    @Transactional
    void getAllStoresByLogoUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where logoUrl equals to DEFAULT_LOGO_URL
        defaultStoreShouldBeFound("logoUrl.equals=" + DEFAULT_LOGO_URL);

        // Get all the storeList where logoUrl equals to UPDATED_LOGO_URL
        defaultStoreShouldNotBeFound("logoUrl.equals=" + UPDATED_LOGO_URL);
    }

    @Test
    @Transactional
    void getAllStoresByLogoUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where logoUrl not equals to DEFAULT_LOGO_URL
        defaultStoreShouldNotBeFound("logoUrl.notEquals=" + DEFAULT_LOGO_URL);

        // Get all the storeList where logoUrl not equals to UPDATED_LOGO_URL
        defaultStoreShouldBeFound("logoUrl.notEquals=" + UPDATED_LOGO_URL);
    }

    @Test
    @Transactional
    void getAllStoresByLogoUrlIsInShouldWork() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where logoUrl in DEFAULT_LOGO_URL or UPDATED_LOGO_URL
        defaultStoreShouldBeFound("logoUrl.in=" + DEFAULT_LOGO_URL + "," + UPDATED_LOGO_URL);

        // Get all the storeList where logoUrl equals to UPDATED_LOGO_URL
        defaultStoreShouldNotBeFound("logoUrl.in=" + UPDATED_LOGO_URL);
    }

    @Test
    @Transactional
    void getAllStoresByLogoUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where logoUrl is not null
        defaultStoreShouldBeFound("logoUrl.specified=true");

        // Get all the storeList where logoUrl is null
        defaultStoreShouldNotBeFound("logoUrl.specified=false");
    }

    @Test
    @Transactional
    void getAllStoresByLogoUrlContainsSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where logoUrl contains DEFAULT_LOGO_URL
        defaultStoreShouldBeFound("logoUrl.contains=" + DEFAULT_LOGO_URL);

        // Get all the storeList where logoUrl contains UPDATED_LOGO_URL
        defaultStoreShouldNotBeFound("logoUrl.contains=" + UPDATED_LOGO_URL);
    }

    @Test
    @Transactional
    void getAllStoresByLogoUrlNotContainsSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where logoUrl does not contain DEFAULT_LOGO_URL
        defaultStoreShouldNotBeFound("logoUrl.doesNotContain=" + DEFAULT_LOGO_URL);

        // Get all the storeList where logoUrl does not contain UPDATED_LOGO_URL
        defaultStoreShouldBeFound("logoUrl.doesNotContain=" + UPDATED_LOGO_URL);
    }

    @Test
    @Transactional
    void getAllStoresByBannerUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where bannerUrl equals to DEFAULT_BANNER_URL
        defaultStoreShouldBeFound("bannerUrl.equals=" + DEFAULT_BANNER_URL);

        // Get all the storeList where bannerUrl equals to UPDATED_BANNER_URL
        defaultStoreShouldNotBeFound("bannerUrl.equals=" + UPDATED_BANNER_URL);
    }

    @Test
    @Transactional
    void getAllStoresByBannerUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where bannerUrl not equals to DEFAULT_BANNER_URL
        defaultStoreShouldNotBeFound("bannerUrl.notEquals=" + DEFAULT_BANNER_URL);

        // Get all the storeList where bannerUrl not equals to UPDATED_BANNER_URL
        defaultStoreShouldBeFound("bannerUrl.notEquals=" + UPDATED_BANNER_URL);
    }

    @Test
    @Transactional
    void getAllStoresByBannerUrlIsInShouldWork() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where bannerUrl in DEFAULT_BANNER_URL or UPDATED_BANNER_URL
        defaultStoreShouldBeFound("bannerUrl.in=" + DEFAULT_BANNER_URL + "," + UPDATED_BANNER_URL);

        // Get all the storeList where bannerUrl equals to UPDATED_BANNER_URL
        defaultStoreShouldNotBeFound("bannerUrl.in=" + UPDATED_BANNER_URL);
    }

    @Test
    @Transactional
    void getAllStoresByBannerUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where bannerUrl is not null
        defaultStoreShouldBeFound("bannerUrl.specified=true");

        // Get all the storeList where bannerUrl is null
        defaultStoreShouldNotBeFound("bannerUrl.specified=false");
    }

    @Test
    @Transactional
    void getAllStoresByBannerUrlContainsSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where bannerUrl contains DEFAULT_BANNER_URL
        defaultStoreShouldBeFound("bannerUrl.contains=" + DEFAULT_BANNER_URL);

        // Get all the storeList where bannerUrl contains UPDATED_BANNER_URL
        defaultStoreShouldNotBeFound("bannerUrl.contains=" + UPDATED_BANNER_URL);
    }

    @Test
    @Transactional
    void getAllStoresByBannerUrlNotContainsSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where bannerUrl does not contain DEFAULT_BANNER_URL
        defaultStoreShouldNotBeFound("bannerUrl.doesNotContain=" + DEFAULT_BANNER_URL);

        // Get all the storeList where bannerUrl does not contain UPDATED_BANNER_URL
        defaultStoreShouldBeFound("bannerUrl.doesNotContain=" + UPDATED_BANNER_URL);
    }

    @Test
    @Transactional
    void getAllStoresByContactEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where contactEmail equals to DEFAULT_CONTACT_EMAIL
        defaultStoreShouldBeFound("contactEmail.equals=" + DEFAULT_CONTACT_EMAIL);

        // Get all the storeList where contactEmail equals to UPDATED_CONTACT_EMAIL
        defaultStoreShouldNotBeFound("contactEmail.equals=" + UPDATED_CONTACT_EMAIL);
    }

    @Test
    @Transactional
    void getAllStoresByContactEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where contactEmail not equals to DEFAULT_CONTACT_EMAIL
        defaultStoreShouldNotBeFound("contactEmail.notEquals=" + DEFAULT_CONTACT_EMAIL);

        // Get all the storeList where contactEmail not equals to UPDATED_CONTACT_EMAIL
        defaultStoreShouldBeFound("contactEmail.notEquals=" + UPDATED_CONTACT_EMAIL);
    }

    @Test
    @Transactional
    void getAllStoresByContactEmailIsInShouldWork() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where contactEmail in DEFAULT_CONTACT_EMAIL or UPDATED_CONTACT_EMAIL
        defaultStoreShouldBeFound("contactEmail.in=" + DEFAULT_CONTACT_EMAIL + "," + UPDATED_CONTACT_EMAIL);

        // Get all the storeList where contactEmail equals to UPDATED_CONTACT_EMAIL
        defaultStoreShouldNotBeFound("contactEmail.in=" + UPDATED_CONTACT_EMAIL);
    }

    @Test
    @Transactional
    void getAllStoresByContactEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where contactEmail is not null
        defaultStoreShouldBeFound("contactEmail.specified=true");

        // Get all the storeList where contactEmail is null
        defaultStoreShouldNotBeFound("contactEmail.specified=false");
    }

    @Test
    @Transactional
    void getAllStoresByContactEmailContainsSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where contactEmail contains DEFAULT_CONTACT_EMAIL
        defaultStoreShouldBeFound("contactEmail.contains=" + DEFAULT_CONTACT_EMAIL);

        // Get all the storeList where contactEmail contains UPDATED_CONTACT_EMAIL
        defaultStoreShouldNotBeFound("contactEmail.contains=" + UPDATED_CONTACT_EMAIL);
    }

    @Test
    @Transactional
    void getAllStoresByContactEmailNotContainsSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where contactEmail does not contain DEFAULT_CONTACT_EMAIL
        defaultStoreShouldNotBeFound("contactEmail.doesNotContain=" + DEFAULT_CONTACT_EMAIL);

        // Get all the storeList where contactEmail does not contain UPDATED_CONTACT_EMAIL
        defaultStoreShouldBeFound("contactEmail.doesNotContain=" + UPDATED_CONTACT_EMAIL);
    }

    @Test
    @Transactional
    void getAllStoresByContactPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where contactPhone equals to DEFAULT_CONTACT_PHONE
        defaultStoreShouldBeFound("contactPhone.equals=" + DEFAULT_CONTACT_PHONE);

        // Get all the storeList where contactPhone equals to UPDATED_CONTACT_PHONE
        defaultStoreShouldNotBeFound("contactPhone.equals=" + UPDATED_CONTACT_PHONE);
    }

    @Test
    @Transactional
    void getAllStoresByContactPhoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where contactPhone not equals to DEFAULT_CONTACT_PHONE
        defaultStoreShouldNotBeFound("contactPhone.notEquals=" + DEFAULT_CONTACT_PHONE);

        // Get all the storeList where contactPhone not equals to UPDATED_CONTACT_PHONE
        defaultStoreShouldBeFound("contactPhone.notEquals=" + UPDATED_CONTACT_PHONE);
    }

    @Test
    @Transactional
    void getAllStoresByContactPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where contactPhone in DEFAULT_CONTACT_PHONE or UPDATED_CONTACT_PHONE
        defaultStoreShouldBeFound("contactPhone.in=" + DEFAULT_CONTACT_PHONE + "," + UPDATED_CONTACT_PHONE);

        // Get all the storeList where contactPhone equals to UPDATED_CONTACT_PHONE
        defaultStoreShouldNotBeFound("contactPhone.in=" + UPDATED_CONTACT_PHONE);
    }

    @Test
    @Transactional
    void getAllStoresByContactPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where contactPhone is not null
        defaultStoreShouldBeFound("contactPhone.specified=true");

        // Get all the storeList where contactPhone is null
        defaultStoreShouldNotBeFound("contactPhone.specified=false");
    }

    @Test
    @Transactional
    void getAllStoresByContactPhoneContainsSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where contactPhone contains DEFAULT_CONTACT_PHONE
        defaultStoreShouldBeFound("contactPhone.contains=" + DEFAULT_CONTACT_PHONE);

        // Get all the storeList where contactPhone contains UPDATED_CONTACT_PHONE
        defaultStoreShouldNotBeFound("contactPhone.contains=" + UPDATED_CONTACT_PHONE);
    }

    @Test
    @Transactional
    void getAllStoresByContactPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where contactPhone does not contain DEFAULT_CONTACT_PHONE
        defaultStoreShouldNotBeFound("contactPhone.doesNotContain=" + DEFAULT_CONTACT_PHONE);

        // Get all the storeList where contactPhone does not contain UPDATED_CONTACT_PHONE
        defaultStoreShouldBeFound("contactPhone.doesNotContain=" + UPDATED_CONTACT_PHONE);
    }

    @Test
    @Transactional
    void getAllStoresByContactAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where contactAddress equals to DEFAULT_CONTACT_ADDRESS
        defaultStoreShouldBeFound("contactAddress.equals=" + DEFAULT_CONTACT_ADDRESS);

        // Get all the storeList where contactAddress equals to UPDATED_CONTACT_ADDRESS
        defaultStoreShouldNotBeFound("contactAddress.equals=" + UPDATED_CONTACT_ADDRESS);
    }

    @Test
    @Transactional
    void getAllStoresByContactAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where contactAddress not equals to DEFAULT_CONTACT_ADDRESS
        defaultStoreShouldNotBeFound("contactAddress.notEquals=" + DEFAULT_CONTACT_ADDRESS);

        // Get all the storeList where contactAddress not equals to UPDATED_CONTACT_ADDRESS
        defaultStoreShouldBeFound("contactAddress.notEquals=" + UPDATED_CONTACT_ADDRESS);
    }

    @Test
    @Transactional
    void getAllStoresByContactAddressIsInShouldWork() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where contactAddress in DEFAULT_CONTACT_ADDRESS or UPDATED_CONTACT_ADDRESS
        defaultStoreShouldBeFound("contactAddress.in=" + DEFAULT_CONTACT_ADDRESS + "," + UPDATED_CONTACT_ADDRESS);

        // Get all the storeList where contactAddress equals to UPDATED_CONTACT_ADDRESS
        defaultStoreShouldNotBeFound("contactAddress.in=" + UPDATED_CONTACT_ADDRESS);
    }

    @Test
    @Transactional
    void getAllStoresByContactAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where contactAddress is not null
        defaultStoreShouldBeFound("contactAddress.specified=true");

        // Get all the storeList where contactAddress is null
        defaultStoreShouldNotBeFound("contactAddress.specified=false");
    }

    @Test
    @Transactional
    void getAllStoresByContactAddressContainsSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where contactAddress contains DEFAULT_CONTACT_ADDRESS
        defaultStoreShouldBeFound("contactAddress.contains=" + DEFAULT_CONTACT_ADDRESS);

        // Get all the storeList where contactAddress contains UPDATED_CONTACT_ADDRESS
        defaultStoreShouldNotBeFound("contactAddress.contains=" + UPDATED_CONTACT_ADDRESS);
    }

    @Test
    @Transactional
    void getAllStoresByContactAddressNotContainsSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where contactAddress does not contain DEFAULT_CONTACT_ADDRESS
        defaultStoreShouldNotBeFound("contactAddress.doesNotContain=" + DEFAULT_CONTACT_ADDRESS);

        // Get all the storeList where contactAddress does not contain UPDATED_CONTACT_ADDRESS
        defaultStoreShouldBeFound("contactAddress.doesNotContain=" + UPDATED_CONTACT_ADDRESS);
    }

    @Test
    @Transactional
    void getAllStoresByAliasIsEqualToSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where alias equals to DEFAULT_ALIAS
        defaultStoreShouldBeFound("alias.equals=" + DEFAULT_ALIAS);

        // Get all the storeList where alias equals to UPDATED_ALIAS
        defaultStoreShouldNotBeFound("alias.equals=" + UPDATED_ALIAS);
    }

    @Test
    @Transactional
    void getAllStoresByAliasIsNotEqualToSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where alias not equals to DEFAULT_ALIAS
        defaultStoreShouldNotBeFound("alias.notEquals=" + DEFAULT_ALIAS);

        // Get all the storeList where alias not equals to UPDATED_ALIAS
        defaultStoreShouldBeFound("alias.notEquals=" + UPDATED_ALIAS);
    }

    @Test
    @Transactional
    void getAllStoresByAliasIsInShouldWork() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where alias in DEFAULT_ALIAS or UPDATED_ALIAS
        defaultStoreShouldBeFound("alias.in=" + DEFAULT_ALIAS + "," + UPDATED_ALIAS);

        // Get all the storeList where alias equals to UPDATED_ALIAS
        defaultStoreShouldNotBeFound("alias.in=" + UPDATED_ALIAS);
    }

    @Test
    @Transactional
    void getAllStoresByAliasIsNullOrNotNull() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where alias is not null
        defaultStoreShouldBeFound("alias.specified=true");

        // Get all the storeList where alias is null
        defaultStoreShouldNotBeFound("alias.specified=false");
    }

    @Test
    @Transactional
    void getAllStoresByAliasContainsSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where alias contains DEFAULT_ALIAS
        defaultStoreShouldBeFound("alias.contains=" + DEFAULT_ALIAS);

        // Get all the storeList where alias contains UPDATED_ALIAS
        defaultStoreShouldNotBeFound("alias.contains=" + UPDATED_ALIAS);
    }

    @Test
    @Transactional
    void getAllStoresByAliasNotContainsSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where alias does not contain DEFAULT_ALIAS
        defaultStoreShouldNotBeFound("alias.doesNotContain=" + DEFAULT_ALIAS);

        // Get all the storeList where alias does not contain UPDATED_ALIAS
        defaultStoreShouldBeFound("alias.doesNotContain=" + UPDATED_ALIAS);
    }

    @Test
    @Transactional
    void getAllStoresByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where status equals to DEFAULT_STATUS
        defaultStoreShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the storeList where status equals to UPDATED_STATUS
        defaultStoreShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllStoresByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where status not equals to DEFAULT_STATUS
        defaultStoreShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the storeList where status not equals to UPDATED_STATUS
        defaultStoreShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllStoresByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultStoreShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the storeList where status equals to UPDATED_STATUS
        defaultStoreShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllStoresByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where status is not null
        defaultStoreShouldBeFound("status.specified=true");

        // Get all the storeList where status is null
        defaultStoreShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllStoresByDateCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where dateCreated equals to DEFAULT_DATE_CREATED
        defaultStoreShouldBeFound("dateCreated.equals=" + DEFAULT_DATE_CREATED);

        // Get all the storeList where dateCreated equals to UPDATED_DATE_CREATED
        defaultStoreShouldNotBeFound("dateCreated.equals=" + UPDATED_DATE_CREATED);
    }

    @Test
    @Transactional
    void getAllStoresByDateCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where dateCreated not equals to DEFAULT_DATE_CREATED
        defaultStoreShouldNotBeFound("dateCreated.notEquals=" + DEFAULT_DATE_CREATED);

        // Get all the storeList where dateCreated not equals to UPDATED_DATE_CREATED
        defaultStoreShouldBeFound("dateCreated.notEquals=" + UPDATED_DATE_CREATED);
    }

    @Test
    @Transactional
    void getAllStoresByDateCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where dateCreated in DEFAULT_DATE_CREATED or UPDATED_DATE_CREATED
        defaultStoreShouldBeFound("dateCreated.in=" + DEFAULT_DATE_CREATED + "," + UPDATED_DATE_CREATED);

        // Get all the storeList where dateCreated equals to UPDATED_DATE_CREATED
        defaultStoreShouldNotBeFound("dateCreated.in=" + UPDATED_DATE_CREATED);
    }

    @Test
    @Transactional
    void getAllStoresByDateCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where dateCreated is not null
        defaultStoreShouldBeFound("dateCreated.specified=true");

        // Get all the storeList where dateCreated is null
        defaultStoreShouldNotBeFound("dateCreated.specified=false");
    }

    @Test
    @Transactional
    void getAllStoresByStoreCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);
        StoreCategory storeCategory;
        if (TestUtil.findAll(em, StoreCategory.class).isEmpty()) {
            storeCategory = StoreCategoryResourceIT.createEntity(em);
            em.persist(storeCategory);
            em.flush();
        } else {
            storeCategory = TestUtil.findAll(em, StoreCategory.class).get(0);
        }
        em.persist(storeCategory);
        em.flush();
        store.setStoreCategory(storeCategory);
        storeRepository.saveAndFlush(store);
        Long storeCategoryId = storeCategory.getId();

        // Get all the storeList where storeCategory equals to storeCategoryId
        defaultStoreShouldBeFound("storeCategoryId.equals=" + storeCategoryId);

        // Get all the storeList where storeCategory equals to (storeCategoryId + 1)
        defaultStoreShouldNotBeFound("storeCategoryId.equals=" + (storeCategoryId + 1));
    }

    @Test
    @Transactional
    void getAllStoresByProductCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);
        ProductCategory productCategory;
        if (TestUtil.findAll(em, ProductCategory.class).isEmpty()) {
            productCategory = ProductCategoryResourceIT.createEntity(em);
            em.persist(productCategory);
            em.flush();
        } else {
            productCategory = TestUtil.findAll(em, ProductCategory.class).get(0);
        }
        em.persist(productCategory);
        em.flush();
        store.addProductCategory(productCategory);
        storeRepository.saveAndFlush(store);
        Long productCategoryId = productCategory.getId();

        // Get all the storeList where productCategory equals to productCategoryId
        defaultStoreShouldBeFound("productCategoryId.equals=" + productCategoryId);

        // Get all the storeList where productCategory equals to (productCategoryId + 1)
        defaultStoreShouldNotBeFound("productCategoryId.equals=" + (productCategoryId + 1));
    }

    @Test
    @Transactional
    void getAllStoresByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);
        Product product;
        if (TestUtil.findAll(em, Product.class).isEmpty()) {
            product = ProductResourceIT.createEntity(em);
            em.persist(product);
            em.flush();
        } else {
            product = TestUtil.findAll(em, Product.class).get(0);
        }
        em.persist(product);
        em.flush();
        store.addProduct(product);
        storeRepository.saveAndFlush(store);
        Long productId = product.getId();

        // Get all the storeList where product equals to productId
        defaultStoreShouldBeFound("productId.equals=" + productId);

        // Get all the storeList where product equals to (productId + 1)
        defaultStoreShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStoreShouldBeFound(String filter) throws Exception {
        restStoreMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(store.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].storeUrl").value(hasItem(DEFAULT_STORE_URL)))
            .andExpect(jsonPath("$.[*].logoUrl").value(hasItem(DEFAULT_LOGO_URL)))
            .andExpect(jsonPath("$.[*].bannerUrl").value(hasItem(DEFAULT_BANNER_URL)))
            .andExpect(jsonPath("$.[*].contactEmail").value(hasItem(DEFAULT_CONTACT_EMAIL)))
            .andExpect(jsonPath("$.[*].contactPhone").value(hasItem(DEFAULT_CONTACT_PHONE)))
            .andExpect(jsonPath("$.[*].contactAddress").value(hasItem(DEFAULT_CONTACT_ADDRESS)))
            .andExpect(jsonPath("$.[*].alias").value(hasItem(DEFAULT_ALIAS)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())));

        // Check, that the count call also returns 1
        restStoreMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStoreShouldNotBeFound(String filter) throws Exception {
        restStoreMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStoreMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingStore() throws Exception {
        // Get the store
        restStoreMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewStore() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        int databaseSizeBeforeUpdate = storeRepository.findAll().size();

        // Update the store
        Store updatedStore = storeRepository.findById(store.getId()).get();
        // Disconnect from session so that the updates on updatedStore are not directly saved in db
        em.detach(updatedStore);
        updatedStore
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .storeUrl(UPDATED_STORE_URL)
            .logoUrl(UPDATED_LOGO_URL)
            .bannerUrl(UPDATED_BANNER_URL)
            .contactEmail(UPDATED_CONTACT_EMAIL)
            .contactPhone(UPDATED_CONTACT_PHONE)
            .contactAddress(UPDATED_CONTACT_ADDRESS)
            .alias(UPDATED_ALIAS)
            .status(UPDATED_STATUS)
            .dateCreated(UPDATED_DATE_CREATED);
        StoreDTO storeDTO = storeMapper.toDto(updatedStore);

        restStoreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, storeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(storeDTO))
            )
            .andExpect(status().isOk());

        // Validate the Store in the database
        List<Store> storeList = storeRepository.findAll();
        assertThat(storeList).hasSize(databaseSizeBeforeUpdate);
        Store testStore = storeList.get(storeList.size() - 1);
        assertThat(testStore.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStore.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testStore.getStoreUrl()).isEqualTo(UPDATED_STORE_URL);
        assertThat(testStore.getLogoUrl()).isEqualTo(UPDATED_LOGO_URL);
        assertThat(testStore.getBannerUrl()).isEqualTo(UPDATED_BANNER_URL);
        assertThat(testStore.getContactEmail()).isEqualTo(UPDATED_CONTACT_EMAIL);
        assertThat(testStore.getContactPhone()).isEqualTo(UPDATED_CONTACT_PHONE);
        assertThat(testStore.getContactAddress()).isEqualTo(UPDATED_CONTACT_ADDRESS);
        assertThat(testStore.getAlias()).isEqualTo(UPDATED_ALIAS);
        assertThat(testStore.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testStore.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
    }

    @Test
    @Transactional
    void putNonExistingStore() throws Exception {
        int databaseSizeBeforeUpdate = storeRepository.findAll().size();
        store.setId(count.incrementAndGet());

        // Create the Store
        StoreDTO storeDTO = storeMapper.toDto(store);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStoreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, storeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(storeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Store in the database
        List<Store> storeList = storeRepository.findAll();
        assertThat(storeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStore() throws Exception {
        int databaseSizeBeforeUpdate = storeRepository.findAll().size();
        store.setId(count.incrementAndGet());

        // Create the Store
        StoreDTO storeDTO = storeMapper.toDto(store);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStoreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(storeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Store in the database
        List<Store> storeList = storeRepository.findAll();
        assertThat(storeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStore() throws Exception {
        int databaseSizeBeforeUpdate = storeRepository.findAll().size();
        store.setId(count.incrementAndGet());

        // Create the Store
        StoreDTO storeDTO = storeMapper.toDto(store);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStoreMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(storeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Store in the database
        List<Store> storeList = storeRepository.findAll();
        assertThat(storeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStoreWithPatch() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        int databaseSizeBeforeUpdate = storeRepository.findAll().size();

        // Update the store using partial update
        Store partialUpdatedStore = new Store();
        partialUpdatedStore.setId(store.getId());

        partialUpdatedStore
            .description(UPDATED_DESCRIPTION)
            .contactEmail(UPDATED_CONTACT_EMAIL)
            .contactPhone(UPDATED_CONTACT_PHONE)
            .contactAddress(UPDATED_CONTACT_ADDRESS)
            .dateCreated(UPDATED_DATE_CREATED);

        restStoreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStore.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStore))
            )
            .andExpect(status().isOk());

        // Validate the Store in the database
        List<Store> storeList = storeRepository.findAll();
        assertThat(storeList).hasSize(databaseSizeBeforeUpdate);
        Store testStore = storeList.get(storeList.size() - 1);
        assertThat(testStore.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStore.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testStore.getStoreUrl()).isEqualTo(DEFAULT_STORE_URL);
        assertThat(testStore.getLogoUrl()).isEqualTo(DEFAULT_LOGO_URL);
        assertThat(testStore.getBannerUrl()).isEqualTo(DEFAULT_BANNER_URL);
        assertThat(testStore.getContactEmail()).isEqualTo(UPDATED_CONTACT_EMAIL);
        assertThat(testStore.getContactPhone()).isEqualTo(UPDATED_CONTACT_PHONE);
        assertThat(testStore.getContactAddress()).isEqualTo(UPDATED_CONTACT_ADDRESS);
        assertThat(testStore.getAlias()).isEqualTo(DEFAULT_ALIAS);
        assertThat(testStore.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testStore.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
    }

    @Test
    @Transactional
    void fullUpdateStoreWithPatch() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        int databaseSizeBeforeUpdate = storeRepository.findAll().size();

        // Update the store using partial update
        Store partialUpdatedStore = new Store();
        partialUpdatedStore.setId(store.getId());

        partialUpdatedStore
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .storeUrl(UPDATED_STORE_URL)
            .logoUrl(UPDATED_LOGO_URL)
            .bannerUrl(UPDATED_BANNER_URL)
            .contactEmail(UPDATED_CONTACT_EMAIL)
            .contactPhone(UPDATED_CONTACT_PHONE)
            .contactAddress(UPDATED_CONTACT_ADDRESS)
            .alias(UPDATED_ALIAS)
            .status(UPDATED_STATUS)
            .dateCreated(UPDATED_DATE_CREATED);

        restStoreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStore.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStore))
            )
            .andExpect(status().isOk());

        // Validate the Store in the database
        List<Store> storeList = storeRepository.findAll();
        assertThat(storeList).hasSize(databaseSizeBeforeUpdate);
        Store testStore = storeList.get(storeList.size() - 1);
        assertThat(testStore.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStore.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testStore.getStoreUrl()).isEqualTo(UPDATED_STORE_URL);
        assertThat(testStore.getLogoUrl()).isEqualTo(UPDATED_LOGO_URL);
        assertThat(testStore.getBannerUrl()).isEqualTo(UPDATED_BANNER_URL);
        assertThat(testStore.getContactEmail()).isEqualTo(UPDATED_CONTACT_EMAIL);
        assertThat(testStore.getContactPhone()).isEqualTo(UPDATED_CONTACT_PHONE);
        assertThat(testStore.getContactAddress()).isEqualTo(UPDATED_CONTACT_ADDRESS);
        assertThat(testStore.getAlias()).isEqualTo(UPDATED_ALIAS);
        assertThat(testStore.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testStore.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
    }

    @Test
    @Transactional
    void patchNonExistingStore() throws Exception {
        int databaseSizeBeforeUpdate = storeRepository.findAll().size();
        store.setId(count.incrementAndGet());

        // Create the Store
        StoreDTO storeDTO = storeMapper.toDto(store);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStoreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, storeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(storeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Store in the database
        List<Store> storeList = storeRepository.findAll();
        assertThat(storeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStore() throws Exception {
        int databaseSizeBeforeUpdate = storeRepository.findAll().size();
        store.setId(count.incrementAndGet());

        // Create the Store
        StoreDTO storeDTO = storeMapper.toDto(store);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStoreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(storeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Store in the database
        List<Store> storeList = storeRepository.findAll();
        assertThat(storeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStore() throws Exception {
        int databaseSizeBeforeUpdate = storeRepository.findAll().size();
        store.setId(count.incrementAndGet());

        // Create the Store
        StoreDTO storeDTO = storeMapper.toDto(store);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStoreMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(storeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Store in the database
        List<Store> storeList = storeRepository.findAll();
        assertThat(storeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStore() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        int databaseSizeBeforeDelete = storeRepository.findAll().size();

        // Delete the store
        restStoreMockMvc
            .perform(delete(ENTITY_API_URL_ID, store.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Store> storeList = storeRepository.findAll();
        assertThat(storeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
