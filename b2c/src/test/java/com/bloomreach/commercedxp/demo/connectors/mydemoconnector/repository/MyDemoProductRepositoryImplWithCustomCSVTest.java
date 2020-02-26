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

import java.math.BigDecimal;
import java.util.Iterator;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bloomreach.commercedxp.api.v2.connector.model.ItemId;
import com.bloomreach.commercedxp.api.v2.connector.model.ItemModel;
import com.bloomreach.commercedxp.api.v2.connector.model.PageResult;
import com.bloomreach.commercedxp.api.v2.connector.model.SimpleItemId;
import com.bloomreach.commercedxp.api.v2.connector.repository.ProductRepository;
import com.bloomreach.commercedxp.api.v2.connector.repository.QuerySpec;
import com.bloomreach.commercedxp.common.v2.connector.form.SimpleCategoryForm;
import com.bloomreach.commercedxp.demo.connectors.mydemoconnector.MyDemoConstants;
import com.bloomreach.commercedxp.starterstore.connectors.CommerceConnector;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for my ProductRepository implementation.
 */
public class MyDemoProductRepositoryImplWithCustomCSVTest extends AbstractMyDemoRepositoryTest {

    private static final String DEMO_PRODUCTS_CSV_LOCATION = "classpath:/com/bloomreach/commercedxp/demo/connectors/mydemoconnector/demoproducts.csv";

    private static final String DEMO_CATEGORIES_CSV_LOCATION = "classpath:/com/bloomreach/commercedxp/demo/connectors/mydemoconnector/democategories.csv";

    private ProductRepository productRepository;

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
        productRepository = new MyDemoProductRepositoryImpl();
    }

    @Test
    public void testFindAll() throws Exception {
        // Create a mock CommerceConnector instance which simply sets the default (CRISP) resource space name
        // even if CRISP is not used in our demo module. See AbstractMyDemoRepositoryTest#createMockCommerceConnector()
        // for detail on how it can create a mock CommerceConnector and CommerceConnectorComponent instances using EasyMock.
        final CommerceConnector mockConnector = createMockCommerceConnector("mydemo", "mydemoSpace");

        // Create a QuerySpec with default pagination info,
        // and invoke the ProductRepository with that.
        QuerySpec querySpec = new QuerySpec();
        PageResult<ItemModel> pageResult = productRepository.findAll(mockConnector, querySpec);

        // Check the paginated result starting at the zero index.
        assertEquals(0, pageResult.getOffset());
        assertEquals(MyDemoConstants.DEFAULT_PAGE_LIMIT, pageResult.getLimit());
        assertEquals(3, pageResult.getSize());
        assertEquals(3, pageResult.getTotalSize());

        final Iterator<ItemModel> itemModelIt = pageResult.iterator();

        ItemModel itemModel = itemModelIt.next();
        assertEquals("X-ICE_X13", itemModel.getItemId().getId());
        assertEquals("24288", itemModel.getItemId().getCode());
        assertEquals("X-ICE X13", itemModel.getDisplayName());
        assertEquals("Michelin's third-generation Studless Ice & Snow winter tire", itemModel.getDescription());
        assertEquals(new BigDecimal("136.98"), itemModel.getListPrice().getMoneyAmounts().get(0).getAmount());
        assertEquals(new BigDecimal("136.98"), itemModel.getPurchasePrice().getMoneyAmounts().get(0).getAmount());
        assertEquals("https://www.example.com/images/24288.png",
                itemModel.getImageSet().getThumbnail().getSelfLink().getHref());

        itemModel = itemModelIt.next();
        assertEquals("WOMENS_M-Class_TEE", itemModel.getItemId().getId());
        assertEquals("97115", itemModel.getItemId().getCode());
        assertEquals("Women's M-Class Tee", itemModel.getDisplayName());
        assertEquals("Vestri M-Class - a women's polo cut for comfort from knit stretch cotton.", itemModel.getDescription());
        assertEquals(new BigDecimal("49.99"), itemModel.getListPrice().getMoneyAmounts().get(0).getAmount());
        assertEquals(new BigDecimal("49.99"), itemModel.getPurchasePrice().getMoneyAmounts().get(0).getAmount());
        assertEquals("https://www.example.com/images/97115.png",
                itemModel.getImageSet().getThumbnail().getSelfLink().getHref());

        itemModel = itemModelIt.next();
        assertEquals("X-Class-S", itemModel.getItemId().getId());
        assertEquals("10002", itemModel.getItemId().getCode());
        assertEquals("X-Class Full Size Premium Sedan", itemModel.getDisplayName());
        assertEquals("Our full size electric/hybrid primum four door sedan.", itemModel.getDescription());
        assertEquals(new BigDecimal("35900.0"), itemModel.getListPrice().getMoneyAmounts().get(0).getAmount());
        assertEquals(new BigDecimal("25900.0"), itemModel.getPurchasePrice().getMoneyAmounts().get(0).getAmount());
        assertEquals("https://www.example.com/images/10002.png",
                itemModel.getImageSet().getThumbnail().getSelfLink().getHref());
    }

    @Test
    public void testFindOne() throws Exception {
        final CommerceConnector mockConnector = createMockCommerceConnector("mydemo", "mydemoSpace");

        QuerySpec querySpec = new QuerySpec();
        ItemId itemId = new SimpleItemId("X-Class-S", "10002");

        ItemModel itemModel = productRepository.findOne(mockConnector, itemId, querySpec);

        assertEquals("X-Class-S", itemModel.getItemId().getId());
        assertEquals("10002", itemModel.getItemId().getCode());
        assertEquals("X-Class Full Size Premium Sedan", itemModel.getDisplayName());
        assertEquals("Our full size electric/hybrid primum four door sedan.", itemModel.getDescription());
        assertEquals(new BigDecimal("35900.0"), itemModel.getListPrice().getMoneyAmounts().get(0).getAmount());
        assertEquals(new BigDecimal("25900.0"), itemModel.getPurchasePrice().getMoneyAmounts().get(0).getAmount());
    }

    @Test
    public void testFindAllByCategory() throws Exception {
        final CommerceConnector mockConnector = createMockCommerceConnector("mydemo", "mydemoSpace");

        QuerySpec querySpec = new QuerySpec();
        PageResult<ItemModel> pageResult = productRepository.findAllByCategory(mockConnector,
                new SimpleCategoryForm("VPA_T_MCLASS"), querySpec);

        assertEquals(0, pageResult.getOffset());
        assertEquals(MyDemoConstants.DEFAULT_PAGE_LIMIT, pageResult.getLimit());
        assertEquals(2, pageResult.getSize());
        assertEquals(2, pageResult.getTotalSize());

        final Iterator<ItemModel> itemModelIt = pageResult.iterator();

        ItemModel itemModel = itemModelIt.next();
        assertEquals("X-ICE_X13", itemModel.getItemId().getId());
        assertEquals("24288", itemModel.getItemId().getCode());
        assertEquals("X-ICE X13", itemModel.getDisplayName());
        assertEquals("Michelin's third-generation Studless Ice & Snow winter tire", itemModel.getDescription());
        assertEquals(new BigDecimal("136.98"), itemModel.getListPrice().getMoneyAmounts().get(0).getAmount());
        assertEquals(new BigDecimal("136.98"), itemModel.getPurchasePrice().getMoneyAmounts().get(0).getAmount());

        itemModel = itemModelIt.next();
        assertEquals("X-Class-S", itemModel.getItemId().getId());
        assertEquals("10002", itemModel.getItemId().getCode());
        assertEquals("X-Class Full Size Premium Sedan", itemModel.getDisplayName());
        assertEquals("Our full size electric/hybrid primum four door sedan.", itemModel.getDescription());
        assertEquals(new BigDecimal("35900.0"), itemModel.getListPrice().getMoneyAmounts().get(0).getAmount());
        assertEquals(new BigDecimal("25900.0"), itemModel.getPurchasePrice().getMoneyAmounts().get(0).getAmount());
    }
}
