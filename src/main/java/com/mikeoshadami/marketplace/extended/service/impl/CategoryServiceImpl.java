package com.mikeoshadami.marketplace.extended.service.impl;

import com.mikeoshadami.marketplace.extended.config.ExtendedConstants;
import com.mikeoshadami.marketplace.extended.dto.CreateProductCategoryDto;
import com.mikeoshadami.marketplace.extended.dto.DefaultApiResponse;
import com.mikeoshadami.marketplace.extended.dto.OpenStoreResponseDto;
import com.mikeoshadami.marketplace.extended.service.ProductsCategoryService;
import com.mikeoshadami.marketplace.service.ProductCategoryQueryService;
import com.mikeoshadami.marketplace.service.ProductCategoryService;
import com.mikeoshadami.marketplace.service.StoreQueryService;
import com.mikeoshadami.marketplace.service.criteria.ProductCategoryCriteria;
import com.mikeoshadami.marketplace.service.criteria.StoreCriteria;
import com.mikeoshadami.marketplace.service.dto.ProductCategoryDTO;
import com.mikeoshadami.marketplace.service.dto.StoreDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

import java.time.Instant;
import java.util.List;

@Service
@Slf4j
public class CategoryServiceImpl implements ProductsCategoryService {

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private StoreQueryService storeQueryService;

    @Autowired
    private ProductCategoryQueryService productCategoryQueryService;


    @Override
    public DefaultApiResponse createProductCategory(CreateProductCategoryDto createProductCategoryDto) {
        DefaultApiResponse responseDto = new DefaultApiResponse();
        responseDto.setStatus(ExtendedConstants.ResponseCode.FAILED.getCode());
        responseDto.setMessage(ExtendedConstants.ResponseCode.FAILED.getDescription());
        StoreCriteria storeCriteria = new StoreCriteria();
        StringFilter aliasFilter = new StringFilter();
        aliasFilter.setEquals(createProductCategoryDto.getAlias());
        storeCriteria.setAlias(aliasFilter);
        StoreDTO storeDTO = storeQueryService.findByCriteria(storeCriteria).stream().findFirst().orElse(null);
        if (storeDTO != null) {
            ProductCategoryDTO productCategoryDto = new ProductCategoryDTO();
            BeanUtils.copyProperties(createProductCategoryDto, productCategoryDto);
            productCategoryDto.setDateCreated(Instant.now());
            productCategoryDto.setStore(storeDTO);
            ProductCategoryDTO save = productCategoryService.save(productCategoryDto);
            if (save != null) {
                OpenStoreResponseDto openStoreResponseDto = new OpenStoreResponseDto();
                openStoreResponseDto.setName(storeDTO.getName());
                openStoreResponseDto.setAlias(storeDTO.getAlias());
                openStoreResponseDto.setStoreId(String.valueOf(save.getId()));
                responseDto.setData(openStoreResponseDto);
                responseDto.setStatus(ExtendedConstants.ResponseCode.SUCCESS.getCode());
                responseDto.setMessage(ExtendedConstants.ResponseCode.SUCCESS.getDescription());
            }
        }
        return responseDto;
    }

    @Override
    public DefaultApiResponse productCategoriesByAlias(String alias) {
        DefaultApiResponse responseDto = new DefaultApiResponse();
        responseDto.setStatus(ExtendedConstants.ResponseCode.FAILED.getCode());
        responseDto.setMessage(ExtendedConstants.ResponseCode.FAILED.getDescription());
        StoreCriteria storeCriteria = new StoreCriteria();
        StringFilter aliasFilter = new StringFilter();
        aliasFilter.setEquals(alias);
        storeCriteria.setAlias(aliasFilter);
        StoreDTO storeDTO = storeQueryService.findByCriteria(storeCriteria).stream().findFirst().orElse(null);
        if (storeDTO != null) {
            ProductCategoryCriteria productCategoryCriteria = new ProductCategoryCriteria();
            LongFilter storeFilter = new LongFilter();
            storeFilter.setEquals(storeDTO.getId());
            productCategoryCriteria.setStoreId(storeFilter);
            List<ProductCategoryDTO> productCategoryDTOS = productCategoryQueryService.findByCriteria(productCategoryCriteria).;
            if (productCategoryDTOS.size() > 0) {
                responseDto.setData(productCategoryDTOS);
                responseDto.setStatus(ExtendedConstants.ResponseCode.SUCCESS.getCode());
                responseDto.setMessage(ExtendedConstants.ResponseCode.SUCCESS.getDescription());
            }
        }
        return responseDto;
    }
}
