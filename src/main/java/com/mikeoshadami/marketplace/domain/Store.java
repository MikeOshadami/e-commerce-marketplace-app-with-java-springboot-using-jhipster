package com.mikeoshadami.marketplace.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mikeoshadami.marketplace.domain.enumeration.Status;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Database Schema Design
 */
@Entity
@Table(name = "store")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Store implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "store_url")
    private String storeUrl;

    @Column(name = "logo_url")
    private String logoUrl;

    @Column(name = "banner_url")
    private String bannerUrl;

    @Column(name = "contact_email")
    private String contactEmail;

    @Column(name = "contact_phone")
    private String contactPhone;

    @Column(name = "contact_address")
    private String contactAddress;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @JsonIgnoreProperties(value = { "store" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private StoreCategory storeCategory;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Store id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Store name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Store description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStoreUrl() {
        return this.storeUrl;
    }

    public Store storeUrl(String storeUrl) {
        this.setStoreUrl(storeUrl);
        return this;
    }

    public void setStoreUrl(String storeUrl) {
        this.storeUrl = storeUrl;
    }

    public String getLogoUrl() {
        return this.logoUrl;
    }

    public Store logoUrl(String logoUrl) {
        this.setLogoUrl(logoUrl);
        return this;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getBannerUrl() {
        return this.bannerUrl;
    }

    public Store bannerUrl(String bannerUrl) {
        this.setBannerUrl(bannerUrl);
        return this;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public String getContactEmail() {
        return this.contactEmail;
    }

    public Store contactEmail(String contactEmail) {
        this.setContactEmail(contactEmail);
        return this;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone() {
        return this.contactPhone;
    }

    public Store contactPhone(String contactPhone) {
        this.setContactPhone(contactPhone);
        return this;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactAddress() {
        return this.contactAddress;
    }

    public Store contactAddress(String contactAddress) {
        this.setContactAddress(contactAddress);
        return this;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public Status getStatus() {
        return this.status;
    }

    public Store status(Status status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public StoreCategory getStoreCategory() {
        return this.storeCategory;
    }

    public void setStoreCategory(StoreCategory storeCategory) {
        this.storeCategory = storeCategory;
    }

    public Store storeCategory(StoreCategory storeCategory) {
        this.setStoreCategory(storeCategory);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Store)) {
            return false;
        }
        return id != null && id.equals(((Store) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Store{" +
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
            "}";
    }
}
