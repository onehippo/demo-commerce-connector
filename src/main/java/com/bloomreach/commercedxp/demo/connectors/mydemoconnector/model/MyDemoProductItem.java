/**
 * Copyright 2019 BloomReach Inc. (https://www.bloomreach.com/)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.bloomreach.commercedxp.demo.connectors.mydemoconnector.model;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Currency;
import java.util.Set;

import com.bloomreach.commercedxp.api.v2.connector.model.ImageModel;
import com.bloomreach.commercedxp.api.v2.connector.model.ImageSetModel;
import com.bloomreach.commercedxp.api.v2.connector.model.ItemId;
import com.bloomreach.commercedxp.api.v2.connector.model.ItemModel;
import com.bloomreach.commercedxp.api.v2.connector.model.Price;
import com.bloomreach.commercedxp.api.v2.connector.model.SimpleImageModel;
import com.bloomreach.commercedxp.api.v2.connector.model.SimpleImageSetModel;
import com.bloomreach.commercedxp.api.v2.connector.model.SimpleItemId;
import com.bloomreach.commercedxp.api.v2.connector.model.SimpleLinkModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MyDemoProductItem implements ItemModel {

    private String id;
    private String code;
    private String displayName;
    private String description;
    private ImageSetModel imageSet;
    private Price listPrice;
    private Price purchasePrice;

    private String thumbnailImageUrl;
    private BigDecimal listPriceAmount;
    private BigDecimal purchasePriceAmount;

    private Set<String> categories;
    
    private ItemId itemId;

    @JsonProperty("pid")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("default_sku")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @JsonProperty("title")
    @Override
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @JsonProperty("description")
    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonIgnore
    @Override
    public ImageSetModel getImageSet() {
        if (imageSet == null) {
            if (thumbnailImageUrl != null) {
                final ImageModel imageModel = new SimpleImageModel(new SimpleLinkModel(thumbnailImageUrl));
                imageSet = new SimpleImageSetModel(imageModel, imageModel);
            }
        }

        return imageSet;
    }

    public void setImageSet(ImageSetModel imageSet) {
        this.imageSet = imageSet;
    }

    @JsonIgnore
    @Override
    public Price getListPrice() {
        if (listPrice == null) {
            if (listPriceAmount != null) {
                listPrice = new MyDemoPrice(
                        Arrays.asList(new MyDemoMoneyAmount(Currency.getInstance("USD"), listPriceAmount)));
            }
        }

        return listPrice;
    }

    public void setListPrice(Price listPrice) {
        this.listPrice = listPrice;
    }

    @JsonIgnore
    @Override
    public Price getPurchasePrice() {
        if (purchasePrice == null) {
            if (purchasePriceAmount != null) {
                purchasePrice = new MyDemoPrice(
                        Arrays.asList(new MyDemoMoneyAmount(Currency.getInstance("USD"), purchasePriceAmount)));
            }
        }

        return purchasePrice;
    }

    public void setPurchasePrice(Price purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    @JsonProperty("thumb_image")
    public String getThumbnailImageUrl() {
        return thumbnailImageUrl;
    }

    public void setThumbnailImageUrl(String thumbnailImageUrl) {
        this.thumbnailImageUrl = thumbnailImageUrl;
    }

    @JsonProperty("price")
    public BigDecimal getListPriceAmount() {
        return listPriceAmount;
    }

    public void setListPriceAmount(BigDecimal listPriceAmount) {
        this.listPriceAmount = listPriceAmount;
    }

    @JsonProperty("sale_price")
    public BigDecimal getPurchasePriceAmount() {
        return purchasePriceAmount;
    }

    public void setPurchasePriceAmount(BigDecimal purchasePriceAmount) {
        this.purchasePriceAmount = purchasePriceAmount;
    }

    @JsonProperty("categories")
    public Set<String> getCategories() {
        if (categories == null) {
            return Collections.emptySet();
        }

        return Collections.unmodifiableSet(categories);
    }

    public void setCategories(Set<String> categories) {
        this.categories = categories;
    }

    @JsonIgnore
    @Override
    public ItemId getItemId() {
        if (itemId == null) {
            if (id != null && code != null) {
                itemId = new SimpleItemId(id, code);
            }
        }
        return itemId;
    }

}
