package com.mikeoshadami.marketplace.extended.dto;

import com.mikeoshadami.marketplace.service.dto.StoreCategoryDTO;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OpenStoreDto {

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private String storeUrl;

    private String logoUrl;

    private String bannerUrl;

    @NotNull
    private String contactEmail;

    @NotNull
    private String contactPhone;

    @NotNull
    private String contactAddress;

    @NotNull
    private String categoryId;


}
