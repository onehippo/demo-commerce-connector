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
import java.util.LinkedList;
import java.util.List;

import com.bloomreach.commercedxp.api.v2.connector.model.CategoryModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MyDemoCategoryModel implements CategoryModel {

    @JsonProperty("cat_id")
    private String id;

    @JsonProperty("cat_name")
    private String displayName;

    @JsonProperty("parent")
    private String parentId;

    @JsonIgnore
    private List<CategoryModel> children;

    public MyDemoCategoryModel() {
    }

    public MyDemoCategoryModel(final String id, final String displayName) {
        this(id, displayName, null);
    }

    public MyDemoCategoryModel(final String id, final String displayName, final List<CategoryModel> children) {
        this.id = id;
        this.displayName = displayName;
        this.children = children;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Override
    public List<CategoryModel> getChildren() {
        if (children == null) {
            return Collections.emptyList();
        }

        return Collections.unmodifiableList(children);
    }

    public void addChild(final CategoryModel child) {
        if (children == null) {
            children = new LinkedList<>();
        }

        children.add(child);
    }

}
