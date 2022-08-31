package com.mikeoshadami.marketplace.service;

import com.mikeoshadami.marketplace.domain.*; // for static metamodels
import com.mikeoshadami.marketplace.domain.Store;
import com.mikeoshadami.marketplace.repository.StoreRepository;
import com.mikeoshadami.marketplace.service.criteria.StoreCriteria;
import com.mikeoshadami.marketplace.service.dto.StoreDTO;
import com.mikeoshadami.marketplace.service.mapper.StoreMapper;
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
 * Service for executing complex queries for {@link Store} entities in the database.
 * The main input is a {@link StoreCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StoreDTO} or a {@link Page} of {@link StoreDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StoreQueryService extends QueryService<Store> {

    private final Logger log = LoggerFactory.getLogger(StoreQueryService.class);

    private final StoreRepository storeRepository;

    private final StoreMapper storeMapper;

    public StoreQueryService(StoreRepository storeRepository, StoreMapper storeMapper) {
        this.storeRepository = storeRepository;
        this.storeMapper = storeMapper;
    }

    /**
     * Return a {@link List} of {@link StoreDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StoreDTO> findByCriteria(StoreCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Store> specification = createSpecification(criteria);
        return storeMapper.toDto(storeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link StoreDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StoreDTO> findByCriteria(StoreCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Store> specification = createSpecification(criteria);
        return storeRepository.findAll(specification, page).map(storeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StoreCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Store> specification = createSpecification(criteria);
        return storeRepository.count(specification);
    }

    /**
     * Function to convert {@link StoreCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Store> createSpecification(StoreCriteria criteria) {
        Specification<Store> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Store_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Store_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Store_.description));
            }
            if (criteria.getStoreUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStoreUrl(), Store_.storeUrl));
            }
            if (criteria.getLogoUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLogoUrl(), Store_.logoUrl));
            }
            if (criteria.getBannerUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBannerUrl(), Store_.bannerUrl));
            }
            if (criteria.getContactEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContactEmail(), Store_.contactEmail));
            }
            if (criteria.getContactPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContactPhone(), Store_.contactPhone));
            }
            if (criteria.getContactAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContactAddress(), Store_.contactAddress));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Store_.status));
            }
            if (criteria.getStoreCategoryId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getStoreCategoryId(),
                            root -> root.join(Store_.storeCategory, JoinType.LEFT).get(StoreCategory_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
