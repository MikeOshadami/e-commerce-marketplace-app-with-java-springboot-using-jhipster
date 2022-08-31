package com.mikeoshadami.marketplace.service.mapper;

import com.mikeoshadami.marketplace.domain.Store;
import com.mikeoshadami.marketplace.domain.StoreCategory;
import com.mikeoshadami.marketplace.service.dto.StoreCategoryDTO;
import com.mikeoshadami.marketplace.service.dto.StoreDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Store} and its DTO {@link StoreDTO}.
 */
@Mapper(componentModel = "spring")
public interface StoreMapper extends EntityMapper<StoreDTO, Store> {
    @Mapping(target = "storeCategory", source = "storeCategory", qualifiedByName = "storeCategoryId")
    StoreDTO toDto(Store s);

    @Named("storeCategoryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    StoreCategoryDTO toDtoStoreCategoryId(StoreCategory storeCategory);
}
