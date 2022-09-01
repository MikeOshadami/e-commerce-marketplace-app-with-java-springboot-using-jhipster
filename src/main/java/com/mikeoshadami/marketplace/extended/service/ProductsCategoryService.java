package com.mikeoshadami.marketplace.extended.service;

import com.mikeoshadami.marketplace.extended.dto.CreateProductCategoryDto;
import com.mikeoshadami.marketplace.extended.dto.DefaultApiResponse;

public interface ProductsCategoryService {
    DefaultApiResponse createProductCategory(CreateProductCategoryDto categoryDto);

    DefaultApiResponse productCategoriesByAlias(String alias);
}
