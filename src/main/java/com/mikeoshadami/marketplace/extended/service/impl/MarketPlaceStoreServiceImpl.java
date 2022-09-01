package com.mikeoshadami.marketplace.extended.service.impl;

import com.mikeoshadami.marketplace.domain.enumeration.Status;
import com.mikeoshadami.marketplace.extended.config.ExtendedConstants;
import com.mikeoshadami.marketplace.extended.dto.DefaultApiResponse;
import com.mikeoshadami.marketplace.extended.dto.OpenStoreDto;
import com.mikeoshadami.marketplace.extended.dto.OpenStoreResponseDto;
import com.mikeoshadami.marketplace.extended.dto.StoreDetailsDto;
import com.mikeoshadami.marketplace.extended.service.MarketplaceStoreService;
import com.mikeoshadami.marketplace.service.StoreQueryService;
import com.mikeoshadami.marketplace.service.StoreService;
import com.mikeoshadami.marketplace.service.criteria.StoreCriteria;
import com.mikeoshadami.marketplace.service.dto.StoreDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.jhipster.service.filter.StringFilter;

import java.time.Instant;

@Service
@Slf4j
public class MarketPlaceStoreServiceImpl implements MarketplaceStoreService {

    //  @Autowired
    // private ExtendedStoreRepository storeRepository;

    @Autowired
    private StoreService storeService;

    @Autowired
    private StoreQueryService storeQueryService;


    @Override
    public DefaultApiResponse openStore(OpenStoreDto openStoreDto) {
        DefaultApiResponse responseDto = new DefaultApiResponse();
        responseDto.setStatus(ExtendedConstants.ResponseCode.FAILED.getCode());
        responseDto.setMessage(ExtendedConstants.ResponseCode.FAILED.getDescription());
        StoreDTO storeDTO = new StoreDTO();
        BeanUtils.copyProperties(openStoreDto, storeDTO);
        storeDTO.setStatus(Status.ACTIVE);
        storeDTO.setDateCreated(Instant.now());
        StoreDTO save = storeService.save(storeDTO);
        if (save != null) {
            OpenStoreResponseDto openStoreResponseDto = new OpenStoreResponseDto();
            openStoreResponseDto.setName(storeDTO.getName());
            openStoreResponseDto.setAlias(storeDTO.getAlias());
            openStoreResponseDto.setStoreId(String.valueOf(save.getId()));
            responseDto.setData(openStoreResponseDto);
            responseDto.setStatus(ExtendedConstants.ResponseCode.SUCCESS.getCode());
            responseDto.setMessage(ExtendedConstants.ResponseCode.SUCCESS.getDescription());
        }
        return responseDto;
    }

    @Override
    public DefaultApiResponse storeLookupDetails(String alias) {
        DefaultApiResponse responseDto = new DefaultApiResponse();
        responseDto.setStatus(ExtendedConstants.ResponseCode.FAILED.getCode());
        responseDto.setMessage(ExtendedConstants.ResponseCode.FAILED.getDescription());
        StoreCriteria storeCriteria = new StoreCriteria();
        StringFilter aliasFilter = new StringFilter();
        aliasFilter.setEquals(alias);
        storeCriteria.setAlias(aliasFilter);
        StoreDTO storeDTO = storeQueryService.findByCriteria(storeCriteria).stream().findFirst().orElse(null);
        if (storeDTO != null) {
            StoreDetailsDto detailsDto = new StoreDetailsDto();
            BeanUtils.copyProperties(storeDTO, detailsDto);
            responseDto.setData(detailsDto);
            responseDto.setStatus(ExtendedConstants.ResponseCode.SUCCESS.getCode());
            responseDto.setMessage(ExtendedConstants.ResponseCode.SUCCESS.getDescription());
        }
        return responseDto;
    }

}
