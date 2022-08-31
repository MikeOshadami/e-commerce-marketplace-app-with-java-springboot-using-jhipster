package com.mikeoshadami.marketplace.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mikeoshadami.marketplace.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StoreCategoryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StoreCategoryDTO.class);
        StoreCategoryDTO storeCategoryDTO1 = new StoreCategoryDTO();
        storeCategoryDTO1.setId(1L);
        StoreCategoryDTO storeCategoryDTO2 = new StoreCategoryDTO();
        assertThat(storeCategoryDTO1).isNotEqualTo(storeCategoryDTO2);
        storeCategoryDTO2.setId(storeCategoryDTO1.getId());
        assertThat(storeCategoryDTO1).isEqualTo(storeCategoryDTO2);
        storeCategoryDTO2.setId(2L);
        assertThat(storeCategoryDTO1).isNotEqualTo(storeCategoryDTO2);
        storeCategoryDTO1.setId(null);
        assertThat(storeCategoryDTO1).isNotEqualTo(storeCategoryDTO2);
    }
}
