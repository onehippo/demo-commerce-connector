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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Before;
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

/**
 * Unit tests for my ProductRepository implementation.
 */
public class MyDemoProductRepositoryImplTest extends AbstractMyDemoRepositoryTest {

    private ProductRepository productRepository;

    @Before
    public void setUp() throws Exception {
        productRepository = new MyDemoProductRepositoryImpl();
    }

    @Test
    public void testFindAll() throws Exception {
        // Create a mock CommerceConnector instance which simply sets the default (CRISP) resource space name
        // even if CRISP is not used in our demo module. See AbstractMyDemoRepositoryTest#createMockCommerceConnector()
        // for detail on how it can create a mock CommerceConnector and CommerceConnectorComponent instances using EasyMock.
        final CommerceConnector mockConnector = createMockCommerceConnector("mydemoSpace");

        // Create a QuerySpec with default pagination info,
        // and invoke the ProductRepository with that.
        QuerySpec querySpec = new QuerySpec();
        PageResult<ItemModel> pageResult = productRepository.findAll(mockConnector, querySpec);

        // Check the paginated result starting at the zero index.
        assertEquals(0, pageResult.getOffset());
        assertEquals(MyDemoConstants.DEFAULT_PAGE_LIMIT, pageResult.getLimit());
        assertEquals(10, pageResult.getSize());
        assertEquals(10, pageResult.getTotalSize());

        // Also, check the first product item in the result collection.
        ItemModel itemModel = pageResult.iterator().next();
        assertEquals("WOMENS_M-Class_TEE", itemModel.getItemId().getId());
        assertEquals("97115", itemModel.getItemId().getCode());
        assertEquals("Women's M-Class Tee", itemModel.getDisplayName());
        assertTrue(itemModel.getDescription().startsWith("Vestri M-Class logo anchors the signature web stripes racing"));
        assertEquals(new BigDecimal("49.99"), itemModel.getListPrice().getMoneyAmounts().get(0).getAmount());
        assertEquals(new BigDecimal("49.99"), itemModel.getPurchasePrice().getMoneyAmounts().get(0).getAmount());
        assertEquals("https://s3-us-west-2.amazonaws.com/elasticpath-demo-images/VESTRI_VIRTUAL/97115.png",
                itemModel.getImageSet().getThumbnail().getSelfLink().getHref());

        querySpec = new QuerySpec(0L, 5L);
        pageResult = productRepository.findAll(mockConnector, querySpec);

        assertEquals(0, pageResult.getOffset());
        assertEquals(5, pageResult.getLimit());
        assertEquals(5, pageResult.getSize());
        assertEquals(10, pageResult.getTotalSize());

        itemModel = pageResult.iterator().next();
        assertEquals("WOMENS_M-Class_TEE", itemModel.getItemId().getId());
        assertEquals("97115", itemModel.getItemId().getCode());
        assertEquals("Women's M-Class Tee", itemModel.getDisplayName());
        assertTrue(itemModel.getDescription().startsWith("Vestri M-Class logo anchors the signature web stripes racing"));
        assertEquals(new BigDecimal("49.99"), itemModel.getListPrice().getMoneyAmounts().get(0).getAmount());
        assertEquals(new BigDecimal("49.99"), itemModel.getPurchasePrice().getMoneyAmounts().get(0).getAmount());
        assertEquals("https://s3-us-west-2.amazonaws.com/elasticpath-demo-images/VESTRI_VIRTUAL/97115.png",
                itemModel.getImageSet().getThumbnail().getSelfLink().getHref());

        // Create a QuerySpec with a specific pagination info in range [5, 10),
        // and invoke the ProductRepository with that. 
        querySpec = new QuerySpec(5L, 5L);
        pageResult = productRepository.findAll(mockConnector, querySpec);

        // Check the paginated result starting at the 5th index. 
        assertEquals(5, pageResult.getOffset());
        assertEquals(5, pageResult.getLimit());
        assertEquals(5, pageResult.getSize());
        assertEquals(10, pageResult.getTotalSize());

        // Also, check the first product item in the result collection.
        itemModel = pageResult.iterator().next();
        assertEquals("AUTO_DRIVE", itemModel.getItemId().getId());
        assertEquals("11610", itemModel.getItemId().getCode());
        assertEquals("AutoPilot", itemModel.getDisplayName());
        assertTrue(itemModel.getDescription().startsWith("All Vestri vehicles produced, have the ability for full self-driving"));
        assertEquals(new BigDecimal("775.0"), itemModel.getListPrice().getMoneyAmounts().get(0).getAmount());
        assertEquals(new BigDecimal("775.0"), itemModel.getPurchasePrice().getMoneyAmounts().get(0).getAmount());
        assertEquals("https://s3-us-west-2.amazonaws.com/elasticpath-demo-images/VESTRI_VIRTUAL/11610.png",
                itemModel.getImageSet().getThumbnail().getSelfLink().getHref());
    }


    @Test
    public void testFindOne() throws Exception {
        final CommerceConnector mockConnector = createMockCommerceConnector("mydemoSpace");

        QuerySpec querySpec = new QuerySpec();
        ItemId itemId = new SimpleItemId("WOMENS_M-Class_TEE","97115");
        
        ItemModel itemModel = productRepository.findOne(mockConnector, itemId, querySpec);

        assertEquals("WOMENS_M-Class_TEE", itemModel.getItemId().getId());
        assertEquals("97115", itemModel.getItemId().getCode());
        assertEquals("Women's M-Class Tee", itemModel.getDisplayName());
        assertTrue(itemModel.getDescription().startsWith("Vestri M-Class logo anchors the signature web stripes racing"));
        assertEquals(new BigDecimal("49.99"), itemModel.getListPrice().getMoneyAmounts().get(0).getAmount());
        assertEquals(new BigDecimal("49.99"), itemModel.getPurchasePrice().getMoneyAmounts().get(0).getAmount());
        assertEquals("https://s3-us-west-2.amazonaws.com/elasticpath-demo-images/VESTRI_VIRTUAL/97115.png",
                itemModel.getImageSet().getThumbnail().getSelfLink().getHref());

        itemId = new SimpleItemId("AUTO_DRIVE", "11610");
        itemModel = productRepository.findOne(mockConnector, itemId, querySpec);

        assertEquals("AUTO_DRIVE", itemModel.getItemId().getId());
        assertEquals("11610", itemModel.getItemId().getCode());
        assertEquals("AutoPilot", itemModel.getDisplayName());
        assertTrue(itemModel.getDescription().startsWith("All Vestri vehicles produced, have the ability for full self-driving"));
        assertEquals(new BigDecimal("775.0"), itemModel.getListPrice().getMoneyAmounts().get(0).getAmount());
        assertEquals(new BigDecimal("775.0"), itemModel.getPurchasePrice().getMoneyAmounts().get(0).getAmount());
        assertEquals("https://s3-us-west-2.amazonaws.com/elasticpath-demo-images/VESTRI_VIRTUAL/11610.png",
                itemModel.getImageSet().getThumbnail().getSelfLink().getHref());

        itemId = new SimpleItemId("non_existing", "non_existing");
        itemModel = productRepository.findOne(mockConnector, itemId, querySpec);
        assertNull(itemModel);
    }

    @Test
    public void testFindAllByCategory() throws Exception {
        final CommerceConnector mockConnector = createMockCommerceConnector("mydemoSpace");

        QuerySpec querySpec = new QuerySpec();
        PageResult<ItemModel> pageResult = productRepository.findAllByCategory(mockConnector,
                new SimpleCategoryForm("VPA_T_MCLASS"), querySpec);

        assertEquals(0, pageResult.getOffset());
        assertEquals(MyDemoConstants.DEFAULT_PAGE_LIMIT, pageResult.getLimit());
        assertEquals(6, pageResult.getSize());
        assertEquals(6, pageResult.getTotalSize());

        ItemModel itemModel = pageResult.iterator().next();
        assertEquals("WOMENS_M-Class_TEE", itemModel.getItemId().getId());
        assertEquals("97115", itemModel.getItemId().getCode());
        assertEquals("Women's M-Class Tee", itemModel.getDisplayName());
        assertTrue(itemModel.getDescription().startsWith("Vestri M-Class logo anchors the signature web stripes racing"));
        assertEquals(new BigDecimal("49.99"), itemModel.getListPrice().getMoneyAmounts().get(0).getAmount());
        assertEquals(new BigDecimal("49.99"), itemModel.getPurchasePrice().getMoneyAmounts().get(0).getAmount());
        assertEquals("https://s3-us-west-2.amazonaws.com/elasticpath-demo-images/VESTRI_VIRTUAL/97115.png",
                itemModel.getImageSet().getThumbnail().getSelfLink().getHref());

        pageResult = productRepository.findAllByCategory(mockConnector,
                new SimpleCategoryForm("VPA_TIRES"), querySpec);

        assertEquals(0, pageResult.getOffset());
        assertEquals(MyDemoConstants.DEFAULT_PAGE_LIMIT, pageResult.getLimit());
        assertEquals(1, pageResult.getSize());
        assertEquals(1, pageResult.getTotalSize());

        itemModel = pageResult.iterator().next();
        assertEquals("BLUEARTH_V905", itemModel.getItemId().getId());
        assertEquals("43449", itemModel.getItemId().getCode());
        assertEquals("Bluearth V905", itemModel.getDisplayName());
        assertTrue(itemModel.getDescription().startsWith("The BluEarth Winter V905 is Yokohama's environmentally conscious Performance Winter / Snow tire"));
        assertEquals(new BigDecimal("134.2"), itemModel.getListPrice().getMoneyAmounts().get(0).getAmount());
        assertEquals(new BigDecimal("134.2"), itemModel.getPurchasePrice().getMoneyAmounts().get(0).getAmount());
        assertEquals("https://s3-us-west-2.amazonaws.com/elasticpath-demo-images/VESTRI_VIRTUAL/43449.png",
                itemModel.getImageSet().getThumbnail().getSelfLink().getHref());

        pageResult = productRepository.findAllByCategory(mockConnector,
                new SimpleCategoryForm("NON_EXISTING"), querySpec);

        assertEquals(0, pageResult.getOffset());
        assertEquals(MyDemoConstants.DEFAULT_PAGE_LIMIT, pageResult.getLimit());
        assertEquals(0, pageResult.getSize());
        assertEquals(0, pageResult.getTotalSize());
    }
}
