/**
 * Copyright 2020 BloomReach Inc. (https://www.bloomreach.com/)
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hippoecm.hst.container.ModifiableRequestContextProvider;
import org.hippoecm.hst.mock.core.request.MockHstRequestContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bloomreach.commercedxp.api.v2.connector.form.ItemListEntryForm.ACTION;
import com.bloomreach.commercedxp.api.v2.connector.model.ItemListEntryModel;
import com.bloomreach.commercedxp.api.v2.connector.model.PageResult;
import com.bloomreach.commercedxp.api.v2.connector.model.SimpleItemId;
import com.bloomreach.commercedxp.api.v2.connector.model.WishListModel;
import com.bloomreach.commercedxp.api.v2.connector.repository.QuerySpec;
import com.bloomreach.commercedxp.api.v2.connector.visitor.TransientVisitorAccessToken;
import com.bloomreach.commercedxp.api.v2.connector.visitor.TransientVisitorContext;
import com.bloomreach.commercedxp.api.v2.connector.visitor.VisitorContext;
import com.bloomreach.commercedxp.api.v2.connector.visitor.VisitorContextAccess;
import com.bloomreach.commercedxp.b2b.api.v2.connector.repository.BizWishListRepository;
import com.bloomreach.commercedxp.common.v2.connector.form.SimpleItemListEntryForm;
import com.bloomreach.commercedxp.common.v2.connector.form.SimpleWishListForm;
import com.bloomreach.commercedxp.starterstore.StarterStoreConstants;
import com.bloomreach.commercedxp.starterstore.connectors.CommerceConnector;

import static org.junit.Assert.assertEquals;

public class MyDemoBizWishListRepositoryImplTest extends AbstractMyDemoBizRepositoryTest {

    private BizWishListRepository wishListRepository;
    private List<WishListModel> createdWishListModels;

    @Before
    public void setUp() throws Exception {
        ModifiableRequestContextProvider.set(new MockHstRequestContext());
        final VisitorContext visitorContext = new TransientVisitorContext("user001", "john@example.com",
                new TransientVisitorAccessToken("token"), false);
        visitorContext.setAttribute(StarterStoreConstants.ATTRIBUTE_ACCOUNT_ID, "account001");
        VisitorContextAccess.setCurrentVisitorContext(visitorContext);

        wishListRepository = new MyDemoBizWishListRepositoryImpl();
        createdWishListModels = new ArrayList<>();

        final CommerceConnector mockConnector = createMockCommerceConnector("mydemo", "mydemoSpace");

        SimpleWishListForm wishListForm = new SimpleWishListForm();
        wishListForm.setName("My wish list 1");

        SimpleItemListEntryForm entryForm = new SimpleItemListEntryForm();
        entryForm.setAction(ACTION.CREATE);
        entryForm.setEntryItemId(new SimpleItemId("WOMENS_M-Class_TEE", "97115"));
        entryForm.setQuantity(1);
        wishListForm.addEntry(entryForm);

        entryForm = new SimpleItemListEntryForm();
        entryForm.setAction(ACTION.CREATE);
        entryForm.setEntryItemId(new SimpleItemId("X-Class-S", "10002"));
        entryForm.setQuantity(2);
        wishListForm.addEntry(entryForm);

        entryForm = new SimpleItemListEntryForm();
        entryForm.setAction(ACTION.CREATE);
        entryForm.setEntryItemId(new SimpleItemId("X-ICE_X13", "24288"));
        entryForm.setQuantity(3);
        wishListForm.addEntry(entryForm);

        createdWishListModels.add(wishListRepository.create(mockConnector, wishListForm));

        wishListForm = new SimpleWishListForm();
        wishListForm.setName("My wish list 2");

        entryForm = new SimpleItemListEntryForm();
        entryForm.setAction(ACTION.CREATE);
        entryForm.setEntryItemId(new SimpleItemId("ADAPTIVE_CRUISE_CTRL", "97723"));
        entryForm.setQuantity(10);
        wishListForm.addEntry(entryForm);

        entryForm = new SimpleItemListEntryForm();
        entryForm.setAction(ACTION.CREATE);
        entryForm.setEntryItemId(new SimpleItemId("ALL_SEASON_FLOOR_MATS", "61398"));
        entryForm.setQuantity(20);
        wishListForm.addEntry(entryForm);

        createdWishListModels.add(wishListRepository.create(mockConnector, wishListForm));
    }

    @After
    public void tearDown() throws Exception {
        VisitorContextAccess.removeCurrentVisitorContext();
        ModifiableRequestContextProvider.clear();
    }

    @Test
    public void testFindAll() throws Exception {
        final CommerceConnector mockConnector = createMockCommerceConnector("mydemo", "mydemoSpace");

        final PageResult<WishListModel> result = wishListRepository.findAll(mockConnector, new QuerySpec());
        assertEquals(0, result.getOffset());
        assertEquals(2, result.getSize());
        assertEquals(2, result.getTotalSize());

        final Iterator<WishListModel> wishListIt = result.iterator();

        WishListModel wishList = wishListIt.next();
        assertEquals("My wish list 1", wishList.getName());
        List<ItemListEntryModel> entries = wishList.getEntries();
        assertEquals(3, entries.size());
        assertEquals(createdWishListModels.get(0).getEntries(), entries);

        wishList = wishListIt.next();
        assertEquals("My wish list 2", wishList.getName());
        entries = wishList.getEntries();
        assertEquals(2, entries.size());
        assertEquals(createdWishListModels.get(1).getEntries(), entries);
    }

    @Test
    public void testFindOne() throws Exception {
        final CommerceConnector mockConnector = createMockCommerceConnector("mydemo", "mydemoSpace");

        WishListModel wishList = wishListRepository.findOne(mockConnector, createdWishListModels.get(0).getId(),
                new QuerySpec());
        assertEquals("My wish list 1", wishList.getName());
        List<ItemListEntryModel> entries = wishList.getEntries();
        assertEquals(3, entries.size());
        assertEquals(createdWishListModels.get(0).getEntries(), entries);

        wishList = wishListRepository.findOne(mockConnector, createdWishListModels.get(1).getId(), new QuerySpec());
        assertEquals("My wish list 2", wishList.getName());
        entries = wishList.getEntries();
        assertEquals(2, entries.size());
        assertEquals(createdWishListModels.get(1).getEntries(), entries);
    }
}