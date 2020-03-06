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
package com.bloomreach.commercedxp.demo.connectors.mydemoconnector.repository;

import java.util.LinkedList;
import java.util.List;

import com.bloomreach.commercedxp.api.v2.connector.ConnectorException;
import com.bloomreach.commercedxp.api.v2.connector.model.CategoryModel;
import com.bloomreach.commercedxp.api.v2.connector.model.PageResult;
import com.bloomreach.commercedxp.api.v2.connector.model.SimplePageResult;
import com.bloomreach.commercedxp.api.v2.connector.repository.AbstractCategoryRepository;
import com.bloomreach.commercedxp.api.v2.connector.repository.QuerySpec;
import com.bloomreach.commercedxp.starterstore.connectors.CommerceConnector;

public class MyDemoCategoryRepositoryImpl extends AbstractCategoryRepository {

    @Override
    public CategoryModel findOne(CommerceConnector connector, String id, QuerySpec querySpec)
            throws ConnectorException {
        return MyDemoDataLoader.getMyDemoData().getCategoryMap().get(id);
    }

    @Override
    public PageResult<CategoryModel> findAll(CommerceConnector connector, QuerySpec querySpec)
            throws ConnectorException {
        final List<CategoryModel> categoryModels = new LinkedList<>(
                MyDemoDataLoader.getMyDemoData().getCategoryMap().values());
        return new SimplePageResult<>(categoryModels);
    }

}
