package com.mikeoshadami.marketplace.service;

import com.mikeoshadami.marketplace.domain.*; // for static metamodels
import com.mikeoshadami.marketplace.domain.StoreCategory;
import com.mikeoshadami.marketplace.repository.StoreCategoryRepository;
import com.mikeoshadami.marketplace.service.criteria.StoreCategoryCriteria;
import com.mikeoshadami.marketplace.service.dto.StoreCategoryDTO;
import com.mikeoshadami.marketplace.service.mapper.StoreCategoryMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link StoreCategory} entities in the database.
 * The main input is a {@link StoreCategoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StoreCategoryDTO} or a {@link Page} of {@link StoreCategoryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StoreCategoryQueryService extends QueryService<StoreCategory> {

    private final Logger log = LoggerFactory.getLogger(StoreCategoryQueryService.class);

    private final StoreCategoryRepository storeCategoryRepository;

    private final StoreCategoryMapper storeCategoryMapper;

    public StoreCategoryQueryService(StoreCategoryRepository storeCategoryRepository, StoreCategoryMapper storeCategoryMapper) {
        this.storeCategoryRepository = storeCategoryRepository;
        this.storeCategoryMapper = storeCategoryMapper;
    }

    /**
     * Return a {@link List} of {@link StoreCategoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StoreCategoryDTO> findByCriteria(StoreCategoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<StoreCategory> specification = createSpecification(criteria);
        return storeCategoryMapper.toDto(storeCategoryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link StoreCategoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StoreCategoryDTO> findByCriteria(StoreCategoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<StoreCategory> specification = createSpecification(criteria);
        return storeCategoryRepository.findAll(specification, page).map(storeCategoryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StoreCategoryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<StoreCategory> specification = createSpecification(criteria);
        return storeCategoryRepository.count(specification);
    }

    /**
     * Function to convert {@link StoreCategoryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<StoreCategory> createSpecification(StoreCategoryCriteria criteria) {
        Specification<StoreCategory> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), StoreCategory_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), StoreCategory_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), StoreCategory_.description));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), StoreCategory_.status));
            }
            if (criteria.getDateCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateCreated(), StoreCategory_.dateCreated));
            }
            if (criteria.getStoreId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getStoreId(), root -> root.join(StoreCategory_.store, JoinType.LEFT).get(Store_.id))
                    );
            }
        }
        return specification;
    }
}
