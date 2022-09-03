package com.mikeoshadami.marketplace.extended.service.impl;

import com.mikeoshadami.marketplace.extended.dto.CreateProductDto;
import com.mikeoshadami.marketplace.extended.dto.DefaultApiResponse;

public interface MarketPlaceProductService {
    DefaultApiResponse createProduct(CreateProductDto createProductDto);

    DefaultApiResponse productsByAlias(String alias);

    DefaultApiResponse productById(Long productId);
}
