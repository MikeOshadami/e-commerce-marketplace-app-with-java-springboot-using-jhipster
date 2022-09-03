package com.mikeoshadami.marketplace.extended.service.impl;

import com.mikeoshadami.marketplace.extended.config.ExtendedConstants;
import com.mikeoshadami.marketplace.extended.dto.CreateProductDto;
import com.mikeoshadami.marketplace.extended.dto.DefaultApiResponse;
import com.mikeoshadami.marketplace.service.ProductCategoryQueryService;
import com.mikeoshadami.marketplace.service.ProductQueryService;
import com.mikeoshadami.marketplace.service.ProductService;
import com.mikeoshadami.marketplace.service.StoreQueryService;
import com.mikeoshadami.marketplace.service.criteria.ProductCategoryCriteria;
import com.mikeoshadami.marketplace.service.criteria.ProductCriteria;
import com.mikeoshadami.marketplace.service.criteria.StoreCriteria;
import com.mikeoshadami.marketplace.service.dto.ProductCategoryDTO;
import com.mikeoshadami.marketplace.service.dto.ProductDTO;
import com.mikeoshadami.marketplace.service.dto.StoreDTO;
import liquibase.repackaged.org.apache.commons.collections4.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

import java.util.List;

@Service
@Slf4j
public class MarketPlaceProductServiceImpl implements MarketPlaceProductService {

    @Autowired
    private StoreQueryService storeQueryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductQueryService productQueryService;

    @Autowired
    private ProductCategoryQueryService productCategoryQueryService;

    @Override
    public DefaultApiResponse createProduct(CreateProductDto createProductDto) {
        DefaultApiResponse responseDto = new DefaultApiResponse();
        responseDto.setStatus(ExtendedConstants.ResponseCode.FAILED.getCode());
        responseDto.setMessage(ExtendedConstants.ResponseCode.FAILED.getDescription());
        ProductCategoryCriteria productCategoryCriteria = new ProductCategoryCriteria();
        LongFilter IdFilter = new LongFilter();
        IdFilter.setEquals(createProductDto.getProductCategoryId());
        productCategoryCriteria.setId(IdFilter);
        ProductCategoryDTO productCategoryDto = productCategoryQueryService.findByCriteria(productCategoryCriteria).stream().findFirst().orElse(null);
        if (productCategoryDto != null) {
            ProductDTO productDto = new ProductDTO();
            BeanUtils.copyProperties(createProductDto, productDto);
            productDto.setProductCategory(productCategoryDto);
            productDto.setStore(productCategoryDto.getStore());
            ProductDTO save = productService.save(productDto);
            if (save != null) {
                responseDto.setStatus(ExtendedConstants.ResponseCode.SUCCESS.getCode());
                responseDto.setMessage(ExtendedConstants.ResponseCode.SUCCESS.getDescription());
            }
        }
        return responseDto;
    }

    @Override
    public DefaultApiResponse productsByAlias(String alias) {
        DefaultApiResponse responseDto = new DefaultApiResponse();
        responseDto.setStatus(ExtendedConstants.ResponseCode.FAILED.getCode());
        responseDto.setMessage(ExtendedConstants.ResponseCode.FAILED.getDescription());
        StoreCriteria storeCriteria = new StoreCriteria();
        StringFilter aliasFilter = new StringFilter();
        aliasFilter.setEquals(alias);
        storeCriteria.setAlias(aliasFilter);
        StoreDTO storeDTO = storeQueryService.findByCriteria(storeCriteria).stream().findFirst().orElse(null);
        if (storeDTO != null) {
            ProductCriteria productCriteria = new ProductCriteria();
            LongFilter storeIdFilter = new LongFilter();
            storeIdFilter.setEquals(storeDTO.getId());
            productCriteria.setStoreId(storeIdFilter);
            List<ProductDTO> productDTOS = productQueryService.findByCriteria(productCriteria);
            if (CollectionUtils.isNotEmpty(productDTOS)) {
                responseDto.setData(productDTOS);
            }
            responseDto.setStatus(ExtendedConstants.ResponseCode.SUCCESS.getCode());
            responseDto.setMessage(ExtendedConstants.ResponseCode.SUCCESS.getDescription());
        }

        return responseDto;
    }

    @Override
    public DefaultApiResponse productById(Long productId) {
        DefaultApiResponse responseDto = new DefaultApiResponse();
        responseDto.setStatus(ExtendedConstants.ResponseCode.FAILED.getCode());
        responseDto.setMessage(ExtendedConstants.ResponseCode.FAILED.getDescription());
        ProductCriteria productCriteria = new ProductCriteria();
        LongFilter idFilter = new LongFilter();
        idFilter.setEquals(productId);
        productCriteria.setId(idFilter);
        ProductDTO productDTO = productQueryService.findByCriteria(productCriteria).stream().findFirst().orElse(null);
        if (productDTO != null) {
            responseDto.setData(productDTO);
            responseDto.setStatus(ExtendedConstants.ResponseCode.SUCCESS.getCode());
            responseDto.setMessage(ExtendedConstants.ResponseCode.SUCCESS.getDescription());
        }
        return responseDto;
    }

}
