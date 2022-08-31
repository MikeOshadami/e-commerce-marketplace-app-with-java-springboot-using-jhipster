package com.mikeoshadami.marketplace.service;

import com.mikeoshadami.marketplace.service.dto.StoreCategoryDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mikeoshadami.marketplace.domain.StoreCategory}.
 */
public interface StoreCategoryService {
    /**
     * Save a storeCategory.
     *
     * @param storeCategoryDTO the entity to save.
     * @return the persisted entity.
     */
    StoreCategoryDTO save(StoreCategoryDTO storeCategoryDTO);

    /**
     * Updates a storeCategory.
     *
     * @param storeCategoryDTO the entity to update.
     * @return the persisted entity.
     */
    StoreCategoryDTO update(StoreCategoryDTO storeCategoryDTO);

    /**
     * Partially updates a storeCategory.
     *
     * @param storeCategoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<StoreCategoryDTO> partialUpdate(StoreCategoryDTO storeCategoryDTO);

    /**
     * Get all the storeCategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StoreCategoryDTO> findAll(Pageable pageable);
    /**
     * Get all the StoreCategoryDTO where Store is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<StoreCategoryDTO> findAllWhereStoreIsNull();

    /**
     * Get the "id" storeCategory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StoreCategoryDTO> findOne(Long id);

    /**
     * Delete the "id" storeCategory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
