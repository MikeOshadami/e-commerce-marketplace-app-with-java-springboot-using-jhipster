package com.mikeoshadami.marketplace.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StoreCategoryMapperTest {

    private StoreCategoryMapper storeCategoryMapper;

    @BeforeEach
    public void setUp() {
        storeCategoryMapper = new StoreCategoryMapperImpl();
    }
}
