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

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MyDemoResponse {

    private List<MyDemoProductItem> productItems;

    @JsonProperty("docs")
    public List<MyDemoProductItem> getProductItems() {
        return productItems;
    }

    public void setProductItems(List<MyDemoProductItem> productItems) {
        this.productItems = productItems;
    }

    public MyDemoProductItem getProductItemById(final String id) {
        if (productItems == null) {
            return null;
        }

        for (MyDemoProductItem item : productItems) {
            if (item.getId().equals(id)) {
                return item;
            }
        }

        return null;
    }
}
