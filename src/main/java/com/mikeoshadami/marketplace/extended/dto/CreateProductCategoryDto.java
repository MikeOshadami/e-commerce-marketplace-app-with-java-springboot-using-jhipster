package com.mikeoshadami.marketplace.extended.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateProductCategoryDto {

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private String alias;

}
