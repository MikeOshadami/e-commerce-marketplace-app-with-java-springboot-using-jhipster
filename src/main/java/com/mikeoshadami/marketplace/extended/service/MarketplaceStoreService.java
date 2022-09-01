package com.mikeoshadami.marketplace.extended.service;

import com.mikeoshadami.marketplace.extended.dto.DefaultApiResponse;
import com.mikeoshadami.marketplace.extended.dto.OpenStoreDto;

public interface MarketplaceStoreService {
    DefaultApiResponse openStore(OpenStoreDto openStoreDto);

    DefaultApiResponse storeLookupDetails(String alias);
}
