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

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MyDemoData {

    @JsonProperty("response")
    private MyDemoResponse response;

    @JsonProperty("category_map")
    private Map<String, String> categoryIdNameMap;

    @JsonIgnore
    private Map<String, MyDemoCategoryModel> categoryMap;

    public MyDemoResponse getResponse() {
        return response;
    }

    public void setResponse(MyDemoResponse response) {
        this.response = response;
    }

    public Map<String, MyDemoCategoryModel> getCategoryMap() {
        if (categoryMap == null && categoryIdNameMap != null) {
            final Map<String, MyDemoCategoryModel> tempMap = new LinkedHashMap<>();

            for (Map.Entry<String, String> entry : categoryIdNameMap.entrySet()) {
                final String catId = entry.getKey();
                final String catName = entry.getValue();
                tempMap.put(catId, new MyDemoCategoryModel(catId, catName));
            }

            categoryMap = tempMap;
        }

        return (categoryMap != null) ? Collections.unmodifiableMap(categoryMap) : Collections.emptyMap();
    }

    public void setCategoryMap(Map<String, MyDemoCategoryModel> categoryMap) {
        this.categoryMap = categoryMap;
    }

    public void setCategoryModels(final List<MyDemoCategoryModel> categoryModels) {
        categoryMap = new LinkedHashMap<>();

        for (MyDemoCategoryModel categoryModel : categoryModels) {
            categoryMap.put(categoryModel.getId(), categoryModel);
        }
    }

    public void resetCategoryModelHierarchy() {
        final Map<String, MyDemoCategoryModel> catMap = getCategoryMap();

        for (MyDemoCategoryModel catModel : catMap.values()) {
            final String parentId = catModel.getParentId();

            if (StringUtils.isNotBlank(parentId)) {
                final MyDemoCategoryModel parentCatModel = catMap.get(parentId);

                if (parentCatModel != null) {
                    parentCatModel.addChild(catModel);
                }
            }
        }
    }
}
