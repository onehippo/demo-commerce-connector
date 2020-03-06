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

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bloomreach.commercedxp.api.v2.connector.model.CategoryModel;
import com.bloomreach.commercedxp.api.v2.connector.model.PageResult;
import com.bloomreach.commercedxp.api.v2.connector.repository.CategoryRepository;
import com.bloomreach.commercedxp.api.v2.connector.repository.QuerySpec;
import com.bloomreach.commercedxp.starterstore.connectors.CommerceConnector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class MyDemoCategoryRepositoryImplWithCustomCSVTest extends AbstractMyDemoRepositoryTest {

    private static final String DEMO_PRODUCTS_CSV_LOCATION = "classpath:/com/bloomreach/commercedxp/demo/connectors/mydemoconnector/demoproducts.csv";

    private static final String DEMO_CATEGORIES_CSV_LOCATION = "classpath:/com/bloomreach/commercedxp/demo/connectors/mydemoconnector/democategories.csv";

    private CategoryRepository categoryRepository;

    @BeforeClass
    public static void beforeClass() throws Exception {
        System.setProperty(MyDemoDataLoader.SYS_PROP_DEMO_PRODUCTS_CSV, DEMO_PRODUCTS_CSV_LOCATION);
        System.setProperty(MyDemoDataLoader.SYS_PROP_DEMO_CATEGORIES_CSV, DEMO_CATEGORIES_CSV_LOCATION);
        MyDemoDataLoader.clearMyDemoData();
    }

    @AfterClass
    public static void afterClass() throws Exception {
        MyDemoDataLoader.clearMyDemoData();
        System.setProperty(MyDemoDataLoader.SYS_PROP_DEMO_PRODUCTS_CSV, "");
        System.setProperty(MyDemoDataLoader.SYS_PROP_DEMO_CATEGORIES_CSV, "");
    }

    @Before
    public void setUp() throws Exception {
        categoryRepository = new MyDemoCategoryRepositoryImpl();
    }

    @Test
    public void testFindAll() throws Exception {
        final CommerceConnector mockConnector = createMockCommerceConnector("mydemo", "mydemoSpace");

        QuerySpec querySpec = new QuerySpec();
        PageResult<CategoryModel> pageResult = categoryRepository.findAll(mockConnector, querySpec);
        assertEquals(3, pageResult.getSize());

        final Map<String, CategoryModel> categoryModelMap = new LinkedHashMap<>();
        pageResult.forEach(item -> {
            categoryModelMap.put(item.getId(), item);
        });

        CategoryModel categoryModel = categoryModelMap.get("VPA");
        assertNotNull(categoryModel);
        assertEquals("VPA", categoryModel.getDisplayName());
        assertEquals(2, categoryModel.getChildCount());
        assertEquals("VPA_T_MCLASS", categoryModel.getChildren().get(0).getId());
        assertEquals("VPA_WOMEN", categoryModel.getChildren().get(1).getId());

        categoryModel = categoryModelMap.get("VPA_T_MCLASS");
        assertNotNull(categoryModel);
        assertEquals("VPA T M Class", categoryModel.getDisplayName());
        assertEquals(0, categoryModel.getChildCount());

        categoryModel = categoryModelMap.get("VPA_WOMEN");
        assertNotNull(categoryModel);
        assertEquals("VPA Women", categoryModel.getDisplayName());
        assertEquals(0, categoryModel.getChildCount());
    }

    @Test
    public void testFindOne() throws Exception {
        final CommerceConnector mockConnector = createMockCommerceConnector("mydemo", "mydemoSpace");

        QuerySpec querySpec = new QuerySpec();

        CategoryModel categoryModel = categoryRepository.findOne(mockConnector, "VPA", querySpec);
        assertNotNull(categoryModel);
        assertEquals("VPA", categoryModel.getDisplayName());
        assertEquals(2, categoryModel.getChildCount());
        assertEquals("VPA_T_MCLASS", categoryModel.getChildren().get(0).getId());
        assertEquals("VPA_WOMEN", categoryModel.getChildren().get(1).getId());

        categoryModel = categoryRepository.findOne(mockConnector, "VPA_T_MCLASS", querySpec);
        assertNotNull(categoryModel);
        assertEquals("VPA T M Class", categoryModel.getDisplayName());
        assertEquals(0, categoryModel.getChildCount());

        categoryModel = categoryRepository.findOne(mockConnector, "VPA_WOMEN", querySpec);
        assertNotNull(categoryModel);
        assertEquals("VPA Women", categoryModel.getDisplayName());
        assertEquals(0, categoryModel.getChildCount());

        categoryModel = categoryRepository.findOne(mockConnector, "NON_EXISTING", querySpec);
        assertNull(categoryModel);
    }
}
