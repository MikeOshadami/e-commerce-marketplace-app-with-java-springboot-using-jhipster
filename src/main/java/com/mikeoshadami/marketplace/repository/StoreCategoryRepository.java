package com.mikeoshadami.marketplace.repository;

import com.mikeoshadami.marketplace.domain.StoreCategory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the StoreCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StoreCategoryRepository extends JpaRepository<StoreCategory, Long>, JpaSpecificationExecutor<StoreCategory> {}
