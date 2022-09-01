package com.mikeoshadami.marketplace.extended.controller;

import com.mikeoshadami.marketplace.extended.config.ApplicationUrl;
import com.mikeoshadami.marketplace.extended.dto.DefaultApiResponse;
import com.mikeoshadami.marketplace.extended.dto.OpenStoreDto;
import com.mikeoshadami.marketplace.extended.service.MarketplaceStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(ApplicationUrl.BASE_CONTEXT_URL)
public class StoreController {

    @Autowired
    private MarketplaceStoreService marketplaceStoreService;

    @PostMapping(value = ApplicationUrl.OPEN_STORE)
    public DefaultApiResponse openStore(@Valid @RequestBody OpenStoreDto openStoreDto) throws Exception {
        log.info("[+] Inside StoreController.openStore with payload {}", openStoreDto);
        return marketplaceStoreService.openStore(openStoreDto);
    }

    @GetMapping(value = ApplicationUrl.LOOKUP_STORE)
    public DefaultApiResponse storeLookupDetails(@PathVariable String alias) throws Exception {
        log.info("[+] Inside StoreController.storeLookupDetails with alias {}", alias);
        return marketplaceStoreService.storeLookupDetails(alias);
    }

}
