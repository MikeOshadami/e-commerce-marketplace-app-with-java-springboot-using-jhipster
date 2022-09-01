package com.mikeoshadami.marketplace.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mikeoshadami.marketplace.IntegrationTest;
import com.mikeoshadami.marketplace.domain.Store;
import com.mikeoshadami.marketplace.domain.StoreCategory;
import com.mikeoshadami.marketplace.domain.enumeration.Status;
import com.mikeoshadami.marketplace.repository.StoreCategoryRepository;
import com.mikeoshadami.marketplace.service.criteria.StoreCategoryCriteria;
import com.mikeoshadami.marketplace.service.dto.StoreCategoryDTO;
import com.mikeoshadami.marketplace.service.mapper.StoreCategoryMapper;
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
 * Integration tests for the {@link StoreCategoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StoreCategoryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.ACTIVE;
    private static final Status UPDATED_STATUS = Status.INACTIVE;

    private static final Instant DEFAULT_DATE_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/store-categories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StoreCategoryRepository storeCategoryRepository;

    @Autowired
    private StoreCategoryMapper storeCategoryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStoreCategoryMockMvc;

    private StoreCategory storeCategory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StoreCategory createEntity(EntityManager em) {
        StoreCategory storeCategory = new StoreCategory()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .status(DEFAULT_STATUS)
            .dateCreated(DEFAULT_DATE_CREATED);
        // Add required entity
        Store store;
        if (TestUtil.findAll(em, Store.class).isEmpty()) {
            store = StoreResourceIT.createEntity(em);
            em.persist(store);
            em.flush();
        } else {
            store = TestUtil.findAll(em, Store.class).get(0);
        }
        storeCategory.setStore(store);
        return storeCategory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StoreCategory createUpdatedEntity(EntityManager em) {
        StoreCategory storeCategory = new StoreCategory()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS)
            .dateCreated(UPDATED_DATE_CREATED);
        // Add required entity
        Store store;
        if (TestUtil.findAll(em, Store.class).isEmpty()) {
            store = StoreResourceIT.createUpdatedEntity(em);
            em.persist(store);
            em.flush();
        } else {
            store = TestUtil.findAll(em, Store.class).get(0);
        }
        storeCategory.setStore(store);
        return storeCategory;
    }

    @BeforeEach
    public void initTest() {
        storeCategory = createEntity(em);
    }

    @Test
    @Transactional
    void createStoreCategory() throws Exception {
        int databaseSizeBeforeCreate = storeCategoryRepository.findAll().size();
        // Create the StoreCategory
        StoreCategoryDTO storeCategoryDTO = storeCategoryMapper.toDto(storeCategory);
        restStoreCategoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(storeCategoryDTO))
            )
            .andExpect(status().isCreated());

        // Validate the StoreCategory in the database
        List<StoreCategory> storeCategoryList = storeCategoryRepository.findAll();
        assertThat(storeCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        StoreCategory testStoreCategory = storeCategoryList.get(storeCategoryList.size() - 1);
        assertThat(testStoreCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStoreCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testStoreCategory.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testStoreCategory.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
    }

    @Test
    @Transactional
    void createStoreCategoryWithExistingId() throws Exception {
        // Create the StoreCategory with an existing ID
        storeCategory.setId(1L);
        StoreCategoryDTO storeCategoryDTO = storeCategoryMapper.toDto(storeCategory);

        int databaseSizeBeforeCreate = storeCategoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStoreCategoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(storeCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StoreCategory in the database
        List<StoreCategory> storeCategoryList = storeCategoryRepository.findAll();
        assertThat(storeCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = storeCategoryRepository.findAll().size();
        // set the field null
        storeCategory.setName(null);

        // Create the StoreCategory, which fails.
        StoreCategoryDTO storeCategoryDTO = storeCategoryMapper.toDto(storeCategory);

        restStoreCategoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(storeCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        List<StoreCategory> storeCategoryList = storeCategoryRepository.findAll();
        assertThat(storeCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = storeCategoryRepository.findAll().size();
        // set the field null
        storeCategory.setDescription(null);

        // Create the StoreCategory, which fails.
        StoreCategoryDTO storeCategoryDTO = storeCategoryMapper.toDto(storeCategory);

        restStoreCategoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(storeCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        List<StoreCategory> storeCategoryList = storeCategoryRepository.findAll();
        assertThat(storeCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = storeCategoryRepository.findAll().size();
        // set the field null
        storeCategory.setStatus(null);

        // Create the StoreCategory, which fails.
        StoreCategoryDTO storeCategoryDTO = storeCategoryMapper.toDto(storeCategory);

        restStoreCategoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(storeCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        List<StoreCategory> storeCategoryList = storeCategoryRepository.findAll();
        assertThat(storeCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = storeCategoryRepository.findAll().size();
        // set the field null
        storeCategory.setDateCreated(null);

        // Create the StoreCategory, which fails.
        StoreCategoryDTO storeCategoryDTO = storeCategoryMapper.toDto(storeCategory);

        restStoreCategoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(storeCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        List<StoreCategory> storeCategoryList = storeCategoryRepository.findAll();
        assertThat(storeCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllStoreCategories() throws Exception {
        // Initialize the database
        storeCategoryRepository.saveAndFlush(storeCategory);

        // Get all the storeCategoryList
        restStoreCategoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(storeCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())));
    }

    @Test
    @Transactional
    void getStoreCategory() throws Exception {
        // Initialize the database
        storeCategoryRepository.saveAndFlush(storeCategory);

        // Get the storeCategory
        restStoreCategoryMockMvc
            .perform(get(ENTITY_API_URL_ID, storeCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(storeCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()));
    }

    @Test
    @Transactional
    void getStoreCategoriesByIdFiltering() throws Exception {
        // Initialize the database
        storeCategoryRepository.saveAndFlush(storeCategory);

        Long id = storeCategory.getId();

        defaultStoreCategoryShouldBeFound("id.equals=" + id);
        defaultStoreCategoryShouldNotBeFound("id.notEquals=" + id);

        defaultStoreCategoryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultStoreCategoryShouldNotBeFound("id.greaterThan=" + id);

        defaultStoreCategoryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultStoreCategoryShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllStoreCategoriesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        storeCategoryRepository.saveAndFlush(storeCategory);

        // Get all the storeCategoryList where name equals to DEFAULT_NAME
        defaultStoreCategoryShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the storeCategoryList where name equals to UPDATED_NAME
        defaultStoreCategoryShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllStoreCategoriesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        storeCategoryRepository.saveAndFlush(storeCategory);

        // Get all the storeCategoryList where name not equals to DEFAULT_NAME
        defaultStoreCategoryShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the storeCategoryList where name not equals to UPDATED_NAME
        defaultStoreCategoryShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllStoreCategoriesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        storeCategoryRepository.saveAndFlush(storeCategory);

        // Get all the storeCategoryList where name in DEFAULT_NAME or UPDATED_NAME
        defaultStoreCategoryShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the storeCategoryList where name equals to UPDATED_NAME
        defaultStoreCategoryShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllStoreCategoriesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        storeCategoryRepository.saveAndFlush(storeCategory);

        // Get all the storeCategoryList where name is not null
        defaultStoreCategoryShouldBeFound("name.specified=true");

        // Get all the storeCategoryList where name is null
        defaultStoreCategoryShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllStoreCategoriesByNameContainsSomething() throws Exception {
        // Initialize the database
        storeCategoryRepository.saveAndFlush(storeCategory);

        // Get all the storeCategoryList where name contains DEFAULT_NAME
        defaultStoreCategoryShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the storeCategoryList where name contains UPDATED_NAME
        defaultStoreCategoryShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllStoreCategoriesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        storeCategoryRepository.saveAndFlush(storeCategory);

        // Get all the storeCategoryList where name does not contain DEFAULT_NAME
        defaultStoreCategoryShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the storeCategoryList where name does not contain UPDATED_NAME
        defaultStoreCategoryShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllStoreCategoriesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        storeCategoryRepository.saveAndFlush(storeCategory);

        // Get all the storeCategoryList where description equals to DEFAULT_DESCRIPTION
        defaultStoreCategoryShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the storeCategoryList where description equals to UPDATED_DESCRIPTION
        defaultStoreCategoryShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllStoreCategoriesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        storeCategoryRepository.saveAndFlush(storeCategory);

        // Get all the storeCategoryList where description not equals to DEFAULT_DESCRIPTION
        defaultStoreCategoryShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the storeCategoryList where description not equals to UPDATED_DESCRIPTION
        defaultStoreCategoryShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllStoreCategoriesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        storeCategoryRepository.saveAndFlush(storeCategory);

        // Get all the storeCategoryList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultStoreCategoryShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the storeCategoryList where description equals to UPDATED_DESCRIPTION
        defaultStoreCategoryShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllStoreCategoriesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        storeCategoryRepository.saveAndFlush(storeCategory);

        // Get all the storeCategoryList where description is not null
        defaultStoreCategoryShouldBeFound("description.specified=true");

        // Get all the storeCategoryList where description is null
        defaultStoreCategoryShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllStoreCategoriesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        storeCategoryRepository.saveAndFlush(storeCategory);

        // Get all the storeCategoryList where description contains DEFAULT_DESCRIPTION
        defaultStoreCategoryShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the storeCategoryList where description contains UPDATED_DESCRIPTION
        defaultStoreCategoryShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllStoreCategoriesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        storeCategoryRepository.saveAndFlush(storeCategory);

        // Get all the storeCategoryList where description does not contain DEFAULT_DESCRIPTION
        defaultStoreCategoryShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the storeCategoryList where description does not contain UPDATED_DESCRIPTION
        defaultStoreCategoryShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllStoreCategoriesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        storeCategoryRepository.saveAndFlush(storeCategory);

        // Get all the storeCategoryList where status equals to DEFAULT_STATUS
        defaultStoreCategoryShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the storeCategoryList where status equals to UPDATED_STATUS
        defaultStoreCategoryShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllStoreCategoriesByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        storeCategoryRepository.saveAndFlush(storeCategory);

        // Get all the storeCategoryList where status not equals to DEFAULT_STATUS
        defaultStoreCategoryShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the storeCategoryList where status not equals to UPDATED_STATUS
        defaultStoreCategoryShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllStoreCategoriesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        storeCategoryRepository.saveAndFlush(storeCategory);

        // Get all the storeCategoryList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultStoreCategoryShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the storeCategoryList where status equals to UPDATED_STATUS
        defaultStoreCategoryShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllStoreCategoriesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        storeCategoryRepository.saveAndFlush(storeCategory);

        // Get all the storeCategoryList where status is not null
        defaultStoreCategoryShouldBeFound("status.specified=true");

        // Get all the storeCategoryList where status is null
        defaultStoreCategoryShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllStoreCategoriesByDateCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        storeCategoryRepository.saveAndFlush(storeCategory);

        // Get all the storeCategoryList where dateCreated equals to DEFAULT_DATE_CREATED
        defaultStoreCategoryShouldBeFound("dateCreated.equals=" + DEFAULT_DATE_CREATED);

        // Get all the storeCategoryList where dateCreated equals to UPDATED_DATE_CREATED
        defaultStoreCategoryShouldNotBeFound("dateCreated.equals=" + UPDATED_DATE_CREATED);
    }

    @Test
    @Transactional
    void getAllStoreCategoriesByDateCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        storeCategoryRepository.saveAndFlush(storeCategory);

        // Get all the storeCategoryList where dateCreated not equals to DEFAULT_DATE_CREATED
        defaultStoreCategoryShouldNotBeFound("dateCreated.notEquals=" + DEFAULT_DATE_CREATED);

        // Get all the storeCategoryList where dateCreated not equals to UPDATED_DATE_CREATED
        defaultStoreCategoryShouldBeFound("dateCreated.notEquals=" + UPDATED_DATE_CREATED);
    }

    @Test
    @Transactional
    void getAllStoreCategoriesByDateCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        storeCategoryRepository.saveAndFlush(storeCategory);

        // Get all the storeCategoryList where dateCreated in DEFAULT_DATE_CREATED or UPDATED_DATE_CREATED
        defaultStoreCategoryShouldBeFound("dateCreated.in=" + DEFAULT_DATE_CREATED + "," + UPDATED_DATE_CREATED);

        // Get all the storeCategoryList where dateCreated equals to UPDATED_DATE_CREATED
        defaultStoreCategoryShouldNotBeFound("dateCreated.in=" + UPDATED_DATE_CREATED);
    }

    @Test
    @Transactional
    void getAllStoreCategoriesByDateCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        storeCategoryRepository.saveAndFlush(storeCategory);

        // Get all the storeCategoryList where dateCreated is not null
        defaultStoreCategoryShouldBeFound("dateCreated.specified=true");

        // Get all the storeCategoryList where dateCreated is null
        defaultStoreCategoryShouldNotBeFound("dateCreated.specified=false");
    }

    @Test
    @Transactional
    void getAllStoreCategoriesByStoreIsEqualToSomething() throws Exception {
        // Get already existing entity
        Store store = storeCategory.getStore();
        storeCategoryRepository.saveAndFlush(storeCategory);
        Long storeId = store.getId();

        // Get all the storeCategoryList where store equals to storeId
        defaultStoreCategoryShouldBeFound("storeId.equals=" + storeId);

        // Get all the storeCategoryList where store equals to (storeId + 1)
        defaultStoreCategoryShouldNotBeFound("storeId.equals=" + (storeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStoreCategoryShouldBeFound(String filter) throws Exception {
        restStoreCategoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(storeCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())));

        // Check, that the count call also returns 1
        restStoreCategoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStoreCategoryShouldNotBeFound(String filter) throws Exception {
        restStoreCategoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStoreCategoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingStoreCategory() throws Exception {
        // Get the storeCategory
        restStoreCategoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewStoreCategory() throws Exception {
        // Initialize the database
        storeCategoryRepository.saveAndFlush(storeCategory);

        int databaseSizeBeforeUpdate = storeCategoryRepository.findAll().size();

        // Update the storeCategory
        StoreCategory updatedStoreCategory = storeCategoryRepository.findById(storeCategory.getId()).get();
        // Disconnect from session so that the updates on updatedStoreCategory are not directly saved in db
        em.detach(updatedStoreCategory);
        updatedStoreCategory.name(UPDATED_NAME).description(UPDATED_DESCRIPTION).status(UPDATED_STATUS).dateCreated(UPDATED_DATE_CREATED);
        StoreCategoryDTO storeCategoryDTO = storeCategoryMapper.toDto(updatedStoreCategory);

        restStoreCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, storeCategoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(storeCategoryDTO))
            )
            .andExpect(status().isOk());

        // Validate the StoreCategory in the database
        List<StoreCategory> storeCategoryList = storeCategoryRepository.findAll();
        assertThat(storeCategoryList).hasSize(databaseSizeBeforeUpdate);
        StoreCategory testStoreCategory = storeCategoryList.get(storeCategoryList.size() - 1);
        assertThat(testStoreCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStoreCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testStoreCategory.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testStoreCategory.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
    }

    @Test
    @Transactional
    void putNonExistingStoreCategory() throws Exception {
        int databaseSizeBeforeUpdate = storeCategoryRepository.findAll().size();
        storeCategory.setId(count.incrementAndGet());

        // Create the StoreCategory
        StoreCategoryDTO storeCategoryDTO = storeCategoryMapper.toDto(storeCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStoreCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, storeCategoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(storeCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StoreCategory in the database
        List<StoreCategory> storeCategoryList = storeCategoryRepository.findAll();
        assertThat(storeCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStoreCategory() throws Exception {
        int databaseSizeBeforeUpdate = storeCategoryRepository.findAll().size();
        storeCategory.setId(count.incrementAndGet());

        // Create the StoreCategory
        StoreCategoryDTO storeCategoryDTO = storeCategoryMapper.toDto(storeCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStoreCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(storeCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StoreCategory in the database
        List<StoreCategory> storeCategoryList = storeCategoryRepository.findAll();
        assertThat(storeCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStoreCategory() throws Exception {
        int databaseSizeBeforeUpdate = storeCategoryRepository.findAll().size();
        storeCategory.setId(count.incrementAndGet());

        // Create the StoreCategory
        StoreCategoryDTO storeCategoryDTO = storeCategoryMapper.toDto(storeCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStoreCategoryMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(storeCategoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StoreCategory in the database
        List<StoreCategory> storeCategoryList = storeCategoryRepository.findAll();
        assertThat(storeCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStoreCategoryWithPatch() throws Exception {
        // Initialize the database
        storeCategoryRepository.saveAndFlush(storeCategory);

        int databaseSizeBeforeUpdate = storeCategoryRepository.findAll().size();

        // Update the storeCategory using partial update
        StoreCategory partialUpdatedStoreCategory = new StoreCategory();
        partialUpdatedStoreCategory.setId(storeCategory.getId());

        partialUpdatedStoreCategory.name(UPDATED_NAME).description(UPDATED_DESCRIPTION).dateCreated(UPDATED_DATE_CREATED);

        restStoreCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStoreCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStoreCategory))
            )
            .andExpect(status().isOk());

        // Validate the StoreCategory in the database
        List<StoreCategory> storeCategoryList = storeCategoryRepository.findAll();
        assertThat(storeCategoryList).hasSize(databaseSizeBeforeUpdate);
        StoreCategory testStoreCategory = storeCategoryList.get(storeCategoryList.size() - 1);
        assertThat(testStoreCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStoreCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testStoreCategory.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testStoreCategory.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
    }

    @Test
    @Transactional
    void fullUpdateStoreCategoryWithPatch() throws Exception {
        // Initialize the database
        storeCategoryRepository.saveAndFlush(storeCategory);

        int databaseSizeBeforeUpdate = storeCategoryRepository.findAll().size();

        // Update the storeCategory using partial update
        StoreCategory partialUpdatedStoreCategory = new StoreCategory();
        partialUpdatedStoreCategory.setId(storeCategory.getId());

        partialUpdatedStoreCategory
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS)
            .dateCreated(UPDATED_DATE_CREATED);

        restStoreCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStoreCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStoreCategory))
            )
            .andExpect(status().isOk());

        // Validate the StoreCategory in the database
        List<StoreCategory> storeCategoryList = storeCategoryRepository.findAll();
        assertThat(storeCategoryList).hasSize(databaseSizeBeforeUpdate);
        StoreCategory testStoreCategory = storeCategoryList.get(storeCategoryList.size() - 1);
        assertThat(testStoreCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStoreCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testStoreCategory.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testStoreCategory.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
    }

    @Test
    @Transactional
    void patchNonExistingStoreCategory() throws Exception {
        int databaseSizeBeforeUpdate = storeCategoryRepository.findAll().size();
        storeCategory.setId(count.incrementAndGet());

        // Create the StoreCategory
        StoreCategoryDTO storeCategoryDTO = storeCategoryMapper.toDto(storeCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStoreCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, storeCategoryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(storeCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StoreCategory in the database
        List<StoreCategory> storeCategoryList = storeCategoryRepository.findAll();
        assertThat(storeCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStoreCategory() throws Exception {
        int databaseSizeBeforeUpdate = storeCategoryRepository.findAll().size();
        storeCategory.setId(count.incrementAndGet());

        // Create the StoreCategory
        StoreCategoryDTO storeCategoryDTO = storeCategoryMapper.toDto(storeCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStoreCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(storeCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StoreCategory in the database
        List<StoreCategory> storeCategoryList = storeCategoryRepository.findAll();
        assertThat(storeCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStoreCategory() throws Exception {
        int databaseSizeBeforeUpdate = storeCategoryRepository.findAll().size();
        storeCategory.setId(count.incrementAndGet());

        // Create the StoreCategory
        StoreCategoryDTO storeCategoryDTO = storeCategoryMapper.toDto(storeCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStoreCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(storeCategoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StoreCategory in the database
        List<StoreCategory> storeCategoryList = storeCategoryRepository.findAll();
        assertThat(storeCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStoreCategory() throws Exception {
        // Initialize the database
        storeCategoryRepository.saveAndFlush(storeCategory);

        int databaseSizeBeforeDelete = storeCategoryRepository.findAll().size();

        // Delete the storeCategory
        restStoreCategoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, storeCategory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StoreCategory> storeCategoryList = storeCategoryRepository.findAll();
        assertThat(storeCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
