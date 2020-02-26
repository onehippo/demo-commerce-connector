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
package com.bloomreach.commercedxp.demo.connectors.myb2bdemoconnector.repository;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.bloomreach.commercedxp.api.v2.connector.model.CategoryModel;
import com.bloomreach.commercedxp.api.v2.connector.model.PageResult;
import com.bloomreach.commercedxp.api.v2.connector.repository.QuerySpec;
import com.bloomreach.commercedxp.b2b.api.v2.connector.repository.BizCategoryRepository;
import com.bloomreach.commercedxp.starterstore.connectors.CommerceConnector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class MyDemoBizCategoryRepositoryImplTest extends AbstractMyDemoBizRepositoryTest {

    private BizCategoryRepository categoryRepository;

    @Before
    public void setUp() throws Exception {
        categoryRepository = new MyDemoBizCategoryRepositoryImpl();
    }

    @Test
    public void testFindAll() throws Exception {
        final CommerceConnector mockConnector = createMockCommerceConnector("mydemo", "mydemoSpace");

        QuerySpec querySpec = new QuerySpec();
        PageResult<CategoryModel> pageResult = categoryRepository.findAll(mockConnector, querySpec);
        assertEquals(16, pageResult.getSize());

        final Map<String, CategoryModel> categoryModelMap = new LinkedHashMap<>();
        pageResult.forEach(item -> {
            categoryModelMap.put(item.getId(), item);
        });

        CategoryModel categoryModel = categoryModelMap.get("VESTRI_BM_APPAREL");
        assertNotNull(categoryModel);
        assertEquals("Apparel", categoryModel.getDisplayName());

        categoryModel = categoryModelMap.get("VPA_VA_MCLASS");
        assertNotNull(categoryModel);
        assertEquals("M-Class", categoryModel.getDisplayName());

        categoryModel = categoryModelMap.get("VESTRI_BM_ACCESSORIES");
        assertNotNull(categoryModel);
        assertEquals("Accessories", categoryModel.getDisplayName());
    }

    @Test
    public void testFindOne() throws Exception {
        final CommerceConnector mockConnector = createMockCommerceConnector("mydemo", "mydemoSpace");

        QuerySpec querySpec = new QuerySpec();
        CategoryModel categoryModel = categoryRepository.findOne(mockConnector, "VPA_VA_MCLASS", querySpec);
        assertNotNull(categoryModel);
        assertEquals("M-Class", categoryModel.getDisplayName());

        categoryModel = categoryRepository.findOne(mockConnector, "VESTRI_BM_ACCESSORIES", querySpec);
        assertNotNull(categoryModel);
        assertEquals("Accessories", categoryModel.getDisplayName());

        categoryModel = categoryRepository.findOne(mockConnector, "NON_EXISTING", querySpec);
        assertNull(categoryModel);
    }
}
