package com.mikeoshadami.marketplace.service.mapper;

import com.mikeoshadami.marketplace.domain.Product;
import com.mikeoshadami.marketplace.domain.ProductCategory;
import com.mikeoshadami.marketplace.domain.Store;
import com.mikeoshadami.marketplace.service.dto.ProductCategoryDTO;
import com.mikeoshadami.marketplace.service.dto.ProductDTO;
import com.mikeoshadami.marketplace.service.dto.StoreDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Product} and its DTO {@link ProductDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {
    @Mapping(target = "productCategory", source = "productCategory", qualifiedByName = "productCategoryName")
    @Mapping(target = "store", source = "store", qualifiedByName = "storeName")
    ProductDTO toDto(Product s);

    @Named("productCategoryName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ProductCategoryDTO toDtoProductCategoryName(ProductCategory productCategory);

    @Named("storeName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    StoreDTO toDtoStoreName(Store store);
}
