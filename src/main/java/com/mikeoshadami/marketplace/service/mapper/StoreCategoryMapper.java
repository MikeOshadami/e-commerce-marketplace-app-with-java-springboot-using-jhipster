package com.mikeoshadami.marketplace.service.mapper;

import com.mikeoshadami.marketplace.domain.StoreCategory;
import com.mikeoshadami.marketplace.service.dto.StoreCategoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link StoreCategory} and its DTO {@link StoreCategoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface StoreCategoryMapper extends EntityMapper<StoreCategoryDTO, StoreCategory> {}
