package com.mikeoshadami.marketplace.service.dto;

import com.mikeoshadami.marketplace.domain.enumeration.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mikeoshadami.marketplace.domain.Store} entity.
 */
@Schema(description = "Database Schema Design")
public class StoreDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String description;

    private String storeUrl;

    private String logoUrl;

    private String bannerUrl;

    private String contactEmail;

    private String contactPhone;

    private String contactAddress;

    @NotNull
    private Status status;

    private StoreCategoryDTO storeCategory;

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

    public String getStoreUrl() {
        return storeUrl;
    }

    public void setStoreUrl(String storeUrl) {
        this.storeUrl = storeUrl;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public StoreCategoryDTO getStoreCategory() {
        return storeCategory;
    }

    public void setStoreCategory(StoreCategoryDTO storeCategory) {
        this.storeCategory = storeCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StoreDTO)) {
            return false;
        }

        StoreDTO storeDTO = (StoreDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, storeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StoreDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", storeUrl='" + getStoreUrl() + "'" +
            ", logoUrl='" + getLogoUrl() + "'" +
            ", bannerUrl='" + getBannerUrl() + "'" +
            ", contactEmail='" + getContactEmail() + "'" +
            ", contactPhone='" + getContactPhone() + "'" +
            ", contactAddress='" + getContactAddress() + "'" +
            ", status='" + getStatus() + "'" +
            ", storeCategory=" + getStoreCategory() +
            "}";
    }
}
