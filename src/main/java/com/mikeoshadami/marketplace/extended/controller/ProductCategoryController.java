package com.mikeoshadami.marketplace.extended.controller;

import com.mikeoshadami.marketplace.extended.config.ApplicationUrl;
import com.mikeoshadami.marketplace.extended.dto.CreateProductCategoryDto;
import com.mikeoshadami.marketplace.extended.dto.DefaultApiResponse;
import com.mikeoshadami.marketplace.extended.service.ProductsCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(ApplicationUrl.BASE_CONTEXT_URL)
public class ProductCategoryController {

    @Autowired
    private ProductsCategoryService productsCategoryService;

    @PostMapping(value = ApplicationUrl.CREATE_PRODUCT_CATEGORY)
    public DefaultApiResponse createCategory(@Valid @RequestBody CreateProductCategoryDto categoryDto) throws Exception {
        log.info("[+] Inside ProductCategoryController.createCategory with payload {}", categoryDto);
        return productsCategoryService.createProductCategory(categoryDto);
    }

    @GetMapping(value = ApplicationUrl.STORE_PRODUCT_CATEGORIES)
    public DefaultApiResponse storeCategories(@PathVariable String alias) throws Exception {
        log.info("[+] Inside ProductCategoryController.productCategories with alias {}", alias);
        return productsCategoryService.productCategoriesByAlias(alias);
    }

}
