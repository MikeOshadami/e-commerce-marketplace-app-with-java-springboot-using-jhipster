package com.mikeoshadami.marketplace.web.rest;

import com.mikeoshadami.marketplace.repository.StoreCategoryRepository;
import com.mikeoshadami.marketplace.service.StoreCategoryQueryService;
import com.mikeoshadami.marketplace.service.StoreCategoryService;
import com.mikeoshadami.marketplace.service.criteria.StoreCategoryCriteria;
import com.mikeoshadami.marketplace.service.dto.StoreCategoryDTO;
import com.mikeoshadami.marketplace.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mikeoshadami.marketplace.domain.StoreCategory}.
 */
@RestController
@RequestMapping("/api")
public class StoreCategoryResource {

    private final Logger log = LoggerFactory.getLogger(StoreCategoryResource.class);

    private static final String ENTITY_NAME = "storeCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StoreCategoryService storeCategoryService;

    private final StoreCategoryRepository storeCategoryRepository;

    private final StoreCategoryQueryService storeCategoryQueryService;

    public StoreCategoryResource(
        StoreCategoryService storeCategoryService,
        StoreCategoryRepository storeCategoryRepository,
        StoreCategoryQueryService storeCategoryQueryService
    ) {
        this.storeCategoryService = storeCategoryService;
        this.storeCategoryRepository = storeCategoryRepository;
        this.storeCategoryQueryService = storeCategoryQueryService;
    }

    /**
     * {@code POST  /store-categories} : Create a new storeCategory.
     *
     * @param storeCategoryDTO the storeCategoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new storeCategoryDTO, or with status {@code 400 (Bad Request)} if the storeCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/store-categories")
    public ResponseEntity<StoreCategoryDTO> createStoreCategory(@Valid @RequestBody StoreCategoryDTO storeCategoryDTO)
        throws URISyntaxException {
        log.debug("REST request to save StoreCategory : {}", storeCategoryDTO);
        if (storeCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new storeCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StoreCategoryDTO result = storeCategoryService.save(storeCategoryDTO);
        return ResponseEntity
            .created(new URI("/api/store-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /store-categories/:id} : Updates an existing storeCategory.
     *
     * @param id the id of the storeCategoryDTO to save.
     * @param storeCategoryDTO the storeCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated storeCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the storeCategoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the storeCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/store-categories/{id}")
    public ResponseEntity<StoreCategoryDTO> updateStoreCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody StoreCategoryDTO storeCategoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update StoreCategory : {}, {}", id, storeCategoryDTO);
        if (storeCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, storeCategoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!storeCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        StoreCategoryDTO result = storeCategoryService.update(storeCategoryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, storeCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /store-categories/:id} : Partial updates given fields of an existing storeCategory, field will ignore if it is null
     *
     * @param id the id of the storeCategoryDTO to save.
     * @param storeCategoryDTO the storeCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated storeCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the storeCategoryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the storeCategoryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the storeCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/store-categories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<StoreCategoryDTO> partialUpdateStoreCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody StoreCategoryDTO storeCategoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update StoreCategory partially : {}, {}", id, storeCategoryDTO);
        if (storeCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, storeCategoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!storeCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StoreCategoryDTO> result = storeCategoryService.partialUpdate(storeCategoryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, storeCategoryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /store-categories} : get all the storeCategories.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of storeCategories in body.
     */
    @GetMapping("/store-categories")
    public ResponseEntity<List<StoreCategoryDTO>> getAllStoreCategories(
        StoreCategoryCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get StoreCategories by criteria: {}", criteria);
        Page<StoreCategoryDTO> page = storeCategoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /store-categories/count} : count all the storeCategories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/store-categories/count")
    public ResponseEntity<Long> countStoreCategories(StoreCategoryCriteria criteria) {
        log.debug("REST request to count StoreCategories by criteria: {}", criteria);
        return ResponseEntity.ok().body(storeCategoryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /store-categories/:id} : get the "id" storeCategory.
     *
     * @param id the id of the storeCategoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the storeCategoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/store-categories/{id}")
    public ResponseEntity<StoreCategoryDTO> getStoreCategory(@PathVariable Long id) {
        log.debug("REST request to get StoreCategory : {}", id);
        Optional<StoreCategoryDTO> storeCategoryDTO = storeCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(storeCategoryDTO);
    }

    /**
     * {@code DELETE  /store-categories/:id} : delete the "id" storeCategory.
     *
     * @param id the id of the storeCategoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/store-categories/{id}")
    public ResponseEntity<Void> deleteStoreCategory(@PathVariable Long id) {
        log.debug("REST request to delete StoreCategory : {}", id);
        storeCategoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
