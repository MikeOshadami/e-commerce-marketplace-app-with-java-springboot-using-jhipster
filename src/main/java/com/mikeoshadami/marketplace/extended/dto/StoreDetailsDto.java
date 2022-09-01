package com.mikeoshadami.marketplace.extended.dto;

import lombok.Data;

@Data
public class StoreDetailsDto {

    private String name;

    private String description;

    private String storeUrl;

    private String logoUrl;

    private String bannerUrl;

    private String contactEmail;

    private String contactPhone;

    private String contactAddress;

    private String categoryId;

    private String alias;
}
