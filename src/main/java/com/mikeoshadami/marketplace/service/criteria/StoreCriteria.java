package com.mikeoshadami.marketplace.service.criteria;

import com.mikeoshadami.marketplace.domain.enumeration.Status;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.mikeoshadami.marketplace.domain.Store} entity. This class is used
 * in {@link com.mikeoshadami.marketplace.web.rest.StoreResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /stores?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class StoreCriteria implements Serializable, Criteria {

    /**
     * Class for filtering Status
     */
    public static class StatusFilter extends Filter<Status> {

        public StatusFilter() {}

        public StatusFilter(StatusFilter filter) {
            super(filter);
        }

        @Override
        public StatusFilter copy() {
            return new StatusFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private StringFilter storeUrl;

    private StringFilter logoUrl;

    private StringFilter bannerUrl;

    private StringFilter contactEmail;

    private StringFilter contactPhone;

    private StringFilter contactAddress;

    private StringFilter alias;

    private StatusFilter status;

    private InstantFilter dateCreated;

    private LongFilter storeCategoryId;

    private LongFilter productCategoryId;

    private LongFilter productId;

    private Boolean distinct;

    public StoreCriteria() {}

    public StoreCriteria(StoreCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.storeUrl = other.storeUrl == null ? null : other.storeUrl.copy();
        this.logoUrl = other.logoUrl == null ? null : other.logoUrl.copy();
        this.bannerUrl = other.bannerUrl == null ? null : other.bannerUrl.copy();
        this.contactEmail = other.contactEmail == null ? null : other.contactEmail.copy();
        this.contactPhone = other.contactPhone == null ? null : other.contactPhone.copy();
        this.contactAddress = other.contactAddress == null ? null : other.contactAddress.copy();
        this.alias = other.alias == null ? null : other.alias.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.dateCreated = other.dateCreated == null ? null : other.dateCreated.copy();
        this.storeCategoryId = other.storeCategoryId == null ? null : other.storeCategoryId.copy();
        this.productCategoryId = other.productCategoryId == null ? null : other.productCategoryId.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public StoreCriteria copy() {
        return new StoreCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getDescription() {
        return description;
    }

    public StringFilter description() {
        if (description == null) {
            description = new StringFilter();
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getStoreUrl() {
        return storeUrl;
    }

    public StringFilter storeUrl() {
        if (storeUrl == null) {
            storeUrl = new StringFilter();
        }
        return storeUrl;
    }

    public void setStoreUrl(StringFilter storeUrl) {
        this.storeUrl = storeUrl;
    }

    public StringFilter getLogoUrl() {
        return logoUrl;
    }

    public StringFilter logoUrl() {
        if (logoUrl == null) {
            logoUrl = new StringFilter();
        }
        return logoUrl;
    }

    public void setLogoUrl(StringFilter logoUrl) {
        this.logoUrl = logoUrl;
    }

    public StringFilter getBannerUrl() {
        return bannerUrl;
    }

    public StringFilter bannerUrl() {
        if (bannerUrl == null) {
            bannerUrl = new StringFilter();
        }
        return bannerUrl;
    }

    public void setBannerUrl(StringFilter bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public StringFilter getContactEmail() {
        return contactEmail;
    }

    public StringFilter contactEmail() {
        if (contactEmail == null) {
            contactEmail = new StringFilter();
        }
        return contactEmail;
    }

    public void setContactEmail(StringFilter contactEmail) {
        this.contactEmail = contactEmail;
    }

    public StringFilter getContactPhone() {
        return contactPhone;
    }

    public StringFilter contactPhone() {
        if (contactPhone == null) {
            contactPhone = new StringFilter();
        }
        return contactPhone;
    }

    public void setContactPhone(StringFilter contactPhone) {
        this.contactPhone = contactPhone;
    }

    public StringFilter getContactAddress() {
        return contactAddress;
    }

    public StringFilter contactAddress() {
        if (contactAddress == null) {
            contactAddress = new StringFilter();
        }
        return contactAddress;
    }

    public void setContactAddress(StringFilter contactAddress) {
        this.contactAddress = contactAddress;
    }

    public StringFilter getAlias() {
        return alias;
    }

    public StringFilter alias() {
        if (alias == null) {
            alias = new StringFilter();
        }
        return alias;
    }

    public void setAlias(StringFilter alias) {
        this.alias = alias;
    }

    public StatusFilter getStatus() {
        return status;
    }

    public StatusFilter status() {
        if (status == null) {
            status = new StatusFilter();
        }
        return status;
    }

    public void setStatus(StatusFilter status) {
        this.status = status;
    }

    public InstantFilter getDateCreated() {
        return dateCreated;
    }

    public InstantFilter dateCreated() {
        if (dateCreated == null) {
            dateCreated = new InstantFilter();
        }
        return dateCreated;
    }

    public void setDateCreated(InstantFilter dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LongFilter getStoreCategoryId() {
        return storeCategoryId;
    }

    public LongFilter storeCategoryId() {
        if (storeCategoryId == null) {
            storeCategoryId = new LongFilter();
        }
        return storeCategoryId;
    }

    public void setStoreCategoryId(LongFilter storeCategoryId) {
        this.storeCategoryId = storeCategoryId;
    }

    public LongFilter getProductCategoryId() {
        return productCategoryId;
    }

    public LongFilter productCategoryId() {
        if (productCategoryId == null) {
            productCategoryId = new LongFilter();
        }
        return productCategoryId;
    }

    public void setProductCategoryId(LongFilter productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public LongFilter getProductId() {
        return productId;
    }

    public LongFilter productId() {
        if (productId == null) {
            productId = new LongFilter();
        }
        return productId;
    }

    public void setProductId(LongFilter productId) {
        this.productId = productId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final StoreCriteria that = (StoreCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(storeUrl, that.storeUrl) &&
            Objects.equals(logoUrl, that.logoUrl) &&
            Objects.equals(bannerUrl, that.bannerUrl) &&
            Objects.equals(contactEmail, that.contactEmail) &&
            Objects.equals(contactPhone, that.contactPhone) &&
            Objects.equals(contactAddress, that.contactAddress) &&
            Objects.equals(alias, that.alias) &&
            Objects.equals(status, that.status) &&
            Objects.equals(dateCreated, that.dateCreated) &&
            Objects.equals(storeCategoryId, that.storeCategoryId) &&
            Objects.equals(productCategoryId, that.productCategoryId) &&
            Objects.equals(productId, that.productId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            description,
            storeUrl,
            logoUrl,
            bannerUrl,
            contactEmail,
            contactPhone,
            contactAddress,
            alias,
            status,
            dateCreated,
            storeCategoryId,
            productCategoryId,
            productId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StoreCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (storeUrl != null ? "storeUrl=" + storeUrl + ", " : "") +
            (logoUrl != null ? "logoUrl=" + logoUrl + ", " : "") +
            (bannerUrl != null ? "bannerUrl=" + bannerUrl + ", " : "") +
            (contactEmail != null ? "contactEmail=" + contactEmail + ", " : "") +
            (contactPhone != null ? "contactPhone=" + contactPhone + ", " : "") +
            (contactAddress != null ? "contactAddress=" + contactAddress + ", " : "") +
            (alias != null ? "alias=" + alias + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (dateCreated != null ? "dateCreated=" + dateCreated + ", " : "") +
            (storeCategoryId != null ? "storeCategoryId=" + storeCategoryId + ", " : "") +
            (productCategoryId != null ? "productCategoryId=" + productCategoryId + ", " : "") +
            (productId != null ? "productId=" + productId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
