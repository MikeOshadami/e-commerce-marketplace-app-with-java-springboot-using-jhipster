package com.mikeoshadami.marketplace.service.dto;

import com.mikeoshadami.marketplace.domain.enumeration.Status;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mikeoshadami.marketplace.domain.StoreCategory} entity.
 */
public class StoreCategoryDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private Status status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StoreCategoryDTO)) {
            return false;
        }

        StoreCategoryDTO storeCategoryDTO = (StoreCategoryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, storeCategoryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StoreCategoryDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
