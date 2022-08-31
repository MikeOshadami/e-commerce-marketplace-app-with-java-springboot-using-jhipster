package com.mikeoshadami.marketplace.service.impl;

import com.mikeoshadami.marketplace.domain.StoreCategory;
import com.mikeoshadami.marketplace.repository.StoreCategoryRepository;
import com.mikeoshadami.marketplace.service.StoreCategoryService;
import com.mikeoshadami.marketplace.service.dto.StoreCategoryDTO;
import com.mikeoshadami.marketplace.service.mapper.StoreCategoryMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link StoreCategory}.
 */
@Service
@Transactional
public class StoreCategoryServiceImpl implements StoreCategoryService {

    private final Logger log = LoggerFactory.getLogger(StoreCategoryServiceImpl.class);

    private final StoreCategoryRepository storeCategoryRepository;

    private final StoreCategoryMapper storeCategoryMapper;

    public StoreCategoryServiceImpl(StoreCategoryRepository storeCategoryRepository, StoreCategoryMapper storeCategoryMapper) {
        this.storeCategoryRepository = storeCategoryRepository;
        this.storeCategoryMapper = storeCategoryMapper;
    }

    @Override
    public StoreCategoryDTO save(StoreCategoryDTO storeCategoryDTO) {
        log.debug("Request to save StoreCategory : {}", storeCategoryDTO);
        StoreCategory storeCategory = storeCategoryMapper.toEntity(storeCategoryDTO);
        storeCategory = storeCategoryRepository.save(storeCategory);
        return storeCategoryMapper.toDto(storeCategory);
    }

    @Override
    public StoreCategoryDTO update(StoreCategoryDTO storeCategoryDTO) {
        log.debug("Request to save StoreCategory : {}", storeCategoryDTO);
        StoreCategory storeCategory = storeCategoryMapper.toEntity(storeCategoryDTO);
        storeCategory = storeCategoryRepository.save(storeCategory);
        return storeCategoryMapper.toDto(storeCategory);
    }

    @Override
    public Optional<StoreCategoryDTO> partialUpdate(StoreCategoryDTO storeCategoryDTO) {
        log.debug("Request to partially update StoreCategory : {}", storeCategoryDTO);

        return storeCategoryRepository
            .findById(storeCategoryDTO.getId())
            .map(existingStoreCategory -> {
                storeCategoryMapper.partialUpdate(existingStoreCategory, storeCategoryDTO);

                return existingStoreCategory;
            })
            .map(storeCategoryRepository::save)
            .map(storeCategoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StoreCategoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StoreCategories");
        return storeCategoryRepository.findAll(pageable).map(storeCategoryMapper::toDto);
    }

    /**
     *  Get all the storeCategories where Store is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<StoreCategoryDTO> findAllWhereStoreIsNull() {
        log.debug("Request to get all storeCategories where Store is null");
        return StreamSupport
            .stream(storeCategoryRepository.findAll().spliterator(), false)
            .filter(storeCategory -> storeCategory.getStore() == null)
            .map(storeCategoryMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StoreCategoryDTO> findOne(Long id) {
        log.debug("Request to get StoreCategory : {}", id);
        return storeCategoryRepository.findById(id).map(storeCategoryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete StoreCategory : {}", id);
        storeCategoryRepository.deleteById(id);
    }
}
