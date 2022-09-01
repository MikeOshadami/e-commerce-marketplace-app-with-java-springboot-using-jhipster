package com.mikeoshadami.marketplace.service.criteria;

import com.mikeoshadami.marketplace.domain.enumeration.Size;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BigDecimalFilter;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.mikeoshadami.marketplace.domain.Product} entity. This class is used
 * in {@link com.mikeoshadami.marketplace.web.rest.ProductResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /products?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class ProductCriteria implements Serializable, Criteria {

    /**
     * Class for filtering Size
     */
    public static class SizeFilter extends Filter<Size> {

        public SizeFilter() {}

        public SizeFilter(SizeFilter filter) {
            super(filter);
        }

        @Override
        public SizeFilter copy() {
            return new SizeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private BigDecimalFilter price;

    private SizeFilter itemSize;

    private IntegerFilter stock;

    private StringFilter imageUrl;

    private InstantFilter dateCreated;

    private LongFilter productCategoryId;

    private LongFilter storeId;

    private Boolean distinct;

    public ProductCriteria() {}

    public ProductCriteria(ProductCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.price = other.price == null ? null : other.price.copy();
        this.itemSize = other.itemSize == null ? null : other.itemSize.copy();
        this.stock = other.stock == null ? null : other.stock.copy();
        this.imageUrl = other.imageUrl == null ? null : other.imageUrl.copy();
        this.dateCreated = other.dateCreated == null ? null : other.dateCreated.copy();
        this.productCategoryId = other.productCategoryId == null ? null : other.productCategoryId.copy();
        this.storeId = other.storeId == null ? null : other.storeId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ProductCriteria copy() {
        return new ProductCriteria(this);
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

    public BigDecimalFilter getPrice() {
        return price;
    }

    public BigDecimalFilter price() {
        if (price == null) {
            price = new BigDecimalFilter();
        }
        return price;
    }

    public void setPrice(BigDecimalFilter price) {
        this.price = price;
    }

    public SizeFilter getItemSize() {
        return itemSize;
    }

    public SizeFilter itemSize() {
        if (itemSize == null) {
            itemSize = new SizeFilter();
        }
        return itemSize;
    }

    public void setItemSize(SizeFilter itemSize) {
        this.itemSize = itemSize;
    }

    public IntegerFilter getStock() {
        return stock;
    }

    public IntegerFilter stock() {
        if (stock == null) {
            stock = new IntegerFilter();
        }
        return stock;
    }

    public void setStock(IntegerFilter stock) {
        this.stock = stock;
    }

    public StringFilter getImageUrl() {
        return imageUrl;
    }

    public StringFilter imageUrl() {
        if (imageUrl == null) {
            imageUrl = new StringFilter();
        }
        return imageUrl;
    }

    public void setImageUrl(StringFilter imageUrl) {
        this.imageUrl = imageUrl;
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

    public LongFilter getStoreId() {
        return storeId;
    }

    public LongFilter storeId() {
        if (storeId == null) {
            storeId = new LongFilter();
        }
        return storeId;
    }

    public void setStoreId(LongFilter storeId) {
        this.storeId = storeId;
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
        final ProductCriteria that = (ProductCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(price, that.price) &&
            Objects.equals(itemSize, that.itemSize) &&
            Objects.equals(stock, that.stock) &&
            Objects.equals(imageUrl, that.imageUrl) &&
            Objects.equals(dateCreated, that.dateCreated) &&
            Objects.equals(productCategoryId, that.productCategoryId) &&
            Objects.equals(storeId, that.storeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price, itemSize, stock, imageUrl, dateCreated, productCategoryId, storeId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (price != null ? "price=" + price + ", " : "") +
            (itemSize != null ? "itemSize=" + itemSize + ", " : "") +
            (stock != null ? "stock=" + stock + ", " : "") +
            (imageUrl != null ? "imageUrl=" + imageUrl + ", " : "") +
            (dateCreated != null ? "dateCreated=" + dateCreated + ", " : "") +
            (productCategoryId != null ? "productCategoryId=" + productCategoryId + ", " : "") +
            (storeId != null ? "storeId=" + storeId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
