package com.mikeoshadami.marketplace.extended.controller;

import com.mikeoshadami.marketplace.extended.config.ApplicationUrl;
import com.mikeoshadami.marketplace.extended.dto.CreateProductDto;
import com.mikeoshadami.marketplace.extended.dto.DefaultApiResponse;
import com.mikeoshadami.marketplace.extended.service.impl.MarketPlaceProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(ApplicationUrl.BASE_CONTEXT_URL)
public class ProductController {

    @Autowired
    private MarketPlaceProductService marketPlaceProductService;

    @PostMapping(value = ApplicationUrl.CREATE_PRODUCT)
    public DefaultApiResponse createProduct(@Valid @RequestBody CreateProductDto createProductDto) throws Exception {
        log.info("[+] Inside ProductController.createProduct with payload {}", createProductDto);
        return marketPlaceProductService.createProduct(createProductDto);
    }

    @GetMapping(value = ApplicationUrl.PRODUCT_LIST)
    public DefaultApiResponse products(@PathVariable String alias) throws Exception {
        log.info("[+] Inside ProductController.products with alias {}", alias);
        return marketPlaceProductService.productsByAlias(alias);
    }

    @GetMapping(value = ApplicationUrl.PRODUCT_DETAILS)
    public DefaultApiResponse productById(@PathVariable Long productId) throws Exception {
        log.info("[+] Inside ProductController.productById with productId {}", productId);
        return marketPlaceProductService.productById(productId);
    }

}
