package com.mikeoshadami.marketplace.service.mapper;

import com.mikeoshadami.marketplace.domain.ProductCategory;
import com.mikeoshadami.marketplace.domain.Store;
import com.mikeoshadami.marketplace.service.dto.ProductCategoryDTO;
import com.mikeoshadami.marketplace.service.dto.StoreDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductCategory} and its DTO {@link ProductCategoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProductCategoryMapper extends EntityMapper<ProductCategoryDTO, ProductCategory> {
    @Mapping(target = "store", source = "store", qualifiedByName = "storeName")
    ProductCategoryDTO toDto(ProductCategory s);

    @Named("storeName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    StoreDTO toDtoStoreName(Store store);
}
