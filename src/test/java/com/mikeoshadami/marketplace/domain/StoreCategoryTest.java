package com.mikeoshadami.marketplace.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mikeoshadami.marketplace.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StoreCategoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StoreCategory.class);
        StoreCategory storeCategory1 = new StoreCategory();
        storeCategory1.setId(1L);
        StoreCategory storeCategory2 = new StoreCategory();
        storeCategory2.setId(storeCategory1.getId());
        assertThat(storeCategory1).isEqualTo(storeCategory2);
        storeCategory2.setId(2L);
        assertThat(storeCategory1).isNotEqualTo(storeCategory2);
        storeCategory1.setId(null);
        assertThat(storeCategory1).isNotEqualTo(storeCategory2);
    }
}
