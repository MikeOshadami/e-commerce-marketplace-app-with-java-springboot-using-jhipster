package com.mikeoshadami.marketplace.extended.dto;

import com.mikeoshadami.marketplace.domain.enumeration.Size;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class CreateProductDto {

    private String name;

    private String description;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal price;

    private Size itemSize;

    private Integer stock;

    private String imageUrl;

    private Long productCategoryId;

}
