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

import java.util.Arrays;
import java.util.LinkedList;

import com.bloomreach.commercedxp.api.v2.connector.visitor.TransientVisitorAccessToken;
import org.hippoecm.hst.container.ModifiableRequestContextProvider;
import org.hippoecm.hst.mock.core.request.MockHstRequestContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bloomreach.commercedxp.api.v2.connector.form.CartEntryForm.ACTION;
import com.bloomreach.commercedxp.api.v2.connector.model.CartEntryModel;
import com.bloomreach.commercedxp.api.v2.connector.model.CartModel;
import com.bloomreach.commercedxp.api.v2.connector.repository.CartRepository;
import com.bloomreach.commercedxp.api.v2.connector.visitor.TransientVisitorContext;
import com.bloomreach.commercedxp.api.v2.connector.visitor.VisitorContextAccess;
import com.bloomreach.commercedxp.common.v2.connector.form.SimpleCartEntryForm;
import com.bloomreach.commercedxp.common.v2.connector.form.SimpleCartForm;
import com.bloomreach.commercedxp.demo.connectors.mydemoconnector.model.MyDemoCartModel;
import com.bloomreach.commercedxp.starterstore.connectors.CommerceConnector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MyDemoCartRepositoryImplTest extends AbstractMyDemoRepositoryTest {

    private CartRepository cartRepository;

    @Before
    public void setUp() throws Exception {
        cartRepository = new MyDemoCartRepositoryImpl();

        ModifiableRequestContextProvider.set(new MockHstRequestContext());
        final TransientVisitorContext visitorContext = new TransientVisitorContext("john@example.com", new TransientVisitorAccessToken("token"));
        VisitorContextAccess.setCurrentVisitorContext(visitorContext);
    }

    @After
    public void tearDown() throws Exception {
        VisitorContextAccess.removeCurrentVisitorContext();
        ModifiableRequestContextProvider.clear();
    }

    @Test
    public void testBasicFlow() throws Exception {
        final CommerceConnector mockConnector = createMockCommerceConnector("mydemoSpace");

        // 1. get my current cart.
        SimpleCartForm cartForm = new SimpleCartForm("", new LinkedList<>());
        CartModel cartModel = cartRepository.checkIn(mockConnector, cartForm);

        assertNotNull(cartModel);
        assertEquals("john@example.com", ((MyDemoCartModel) cartModel).getUsername());
        assertEquals(0, cartModel.getEntries().size());

        // 2. add one item to cart.
        SimpleCartEntryForm entryForm = new SimpleCartEntryForm("WOMENS_M-Class_TEE", 1, ACTION.CREATE);
        cartForm = new SimpleCartForm("", Arrays.asList(entryForm));
        cartModel = cartRepository.save(mockConnector, cartForm);

        assertNotNull(cartModel);
        assertEquals("john@example.com", ((MyDemoCartModel) cartModel).getUsername());
        assertEquals(1, cartModel.getEntries().size());

        CartEntryModel entryModel = cartModel.getEntries().get(0);
        assertEquals("WOMENS_M-Class_TEE", entryModel.getId());
        assertEquals(1, entryModel.getQuantity());

        // 3. add two more items to cart.
        cartForm = new SimpleCartForm("", Arrays.asList(new SimpleCartEntryForm("AUTO_DRIVE", 1, ACTION.CREATE),
                new SimpleCartEntryForm("HEATED_SEATS", 1, ACTION.CREATE)));
        cartModel = cartRepository.save(mockConnector, cartForm);

        assertNotNull(cartModel);
        assertEquals("john@example.com", ((MyDemoCartModel) cartModel).getUsername());
        assertEquals(3, cartModel.getEntries().size());

        entryModel = cartModel.getEntries().get(0);
        assertEquals("WOMENS_M-Class_TEE", entryModel.getId());
        assertEquals(1, entryModel.getQuantity());

        entryModel = cartModel.getEntries().get(1);
        assertEquals("AUTO_DRIVE", entryModel.getId());
        assertEquals(1, entryModel.getQuantity());

        entryModel = cartModel.getEntries().get(2);
        assertEquals("HEATED_SEATS", entryModel.getId());
        assertEquals(1, entryModel.getQuantity());

        // 4. update the quantity of the second entry.
        entryForm = new SimpleCartEntryForm("AUTO_DRIVE", 2, ACTION.UPDATE);
        cartForm = new SimpleCartForm("", Arrays.asList(entryForm));
        cartModel = cartRepository.save(mockConnector, cartForm);

        assertNotNull(cartModel);
        assertEquals("john@example.com", ((MyDemoCartModel) cartModel).getUsername());
        assertEquals(3, cartModel.getEntries().size());

        entryModel = cartModel.getEntries().get(0);
        assertEquals("WOMENS_M-Class_TEE", entryModel.getId());
        assertEquals(1, entryModel.getQuantity());

        entryModel = cartModel.getEntries().get(1);
        assertEquals("AUTO_DRIVE", entryModel.getId());
        assertEquals(2, entryModel.getQuantity());

        entryModel = cartModel.getEntries().get(2);
        assertEquals("HEATED_SEATS", entryModel.getId());
        assertEquals(1, entryModel.getQuantity());

        // 5. delete the third entry.
        entryForm = new SimpleCartEntryForm("HEATED_SEATS", 0, ACTION.DELETE);
        cartForm = new SimpleCartForm("", Arrays.asList(entryForm));
        cartModel = cartRepository.save(mockConnector, cartForm);

        assertNotNull(cartModel);
        assertEquals("john@example.com", ((MyDemoCartModel) cartModel).getUsername());
        assertEquals(2, cartModel.getEntries().size());

        entryModel = cartModel.getEntries().get(0);
        assertEquals("WOMENS_M-Class_TEE", entryModel.getId());
        assertEquals(1, entryModel.getQuantity());

        entryModel = cartModel.getEntries().get(1);
        assertEquals("AUTO_DRIVE", entryModel.getId());
        assertEquals(2, entryModel.getQuantity());
    }
}
