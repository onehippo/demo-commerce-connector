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

import com.bloomreach.commercedxp.api.v2.connector.model.PageResult;
import com.bloomreach.commercedxp.api.v2.connector.repository.QuerySpec;
import com.bloomreach.commercedxp.api.v2.connector.visitor.TransientVisitorAccessToken;
import com.bloomreach.commercedxp.api.v2.connector.visitor.TransientVisitorContext;
import com.bloomreach.commercedxp.api.v2.connector.visitor.VisitorContext;
import com.bloomreach.commercedxp.api.v2.connector.visitor.VisitorContextAccess;
import com.bloomreach.commercedxp.b2b.api.v2.connector.model.BizStoredPaymentModel;
import com.bloomreach.commercedxp.b2b.api.v2.connector.repository.BizStoredPaymentRepository;
import com.bloomreach.commercedxp.b2b.common.v2.connector.form.SimpleBizStoredPaymentForm;
import com.bloomreach.commercedxp.demo.connectors.myb2bdemoconnector.model.MyDemoBizStoredPaymentModel;
import com.bloomreach.commercedxp.starterstore.StarterStoreConstants;
import com.bloomreach.commercedxp.starterstore.connectors.CommerceConnector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class MyDemoBizStoredPaymentRepositoryImplTest extends AbstractMyDemoBizRepositoryTest {

    private BizStoredPaymentRepository storedPaymentRepository;
    private List<BizStoredPaymentModel> createdStoredPaymentModels;

    @Before
    public void setUp() throws Exception {
        ModifiableRequestContextProvider.set(new MockHstRequestContext());
        final VisitorContext visitorContext = new TransientVisitorContext("user001", "john@example.com",
                new TransientVisitorAccessToken("token"), false);
        visitorContext.setAttribute(StarterStoreConstants.ATTRIBUTE_ACCOUNT_ID, "account001");
        VisitorContextAccess.setCurrentVisitorContext(visitorContext);

        storedPaymentRepository = new MyDemoBizStoredPaymentRepositoryImpl();
        createdStoredPaymentModels = new ArrayList<>();

        final CommerceConnector mockConnector = createMockCommerceConnector("mydemo", "mydemoSpace");

        SimpleBizStoredPaymentForm form = new SimpleBizStoredPaymentForm();
        form.setAccountType(MyDemoBizStoredPaymentModel.ACCOUNT_TYPE_PURCHASE_ORDER);
        form.setDisplayName("My purchase order 1");
        form.setAccountNumber("PO-001");
        form.setEnabled(true);
        createdStoredPaymentModels.add(storedPaymentRepository.create(mockConnector, form));

        form = new SimpleBizStoredPaymentForm();
        form.setAccountType(MyDemoBizStoredPaymentModel.ACCOUNT_TYPE_PURCHASE_ORDER);
        form.setDisplayName("My purchase order 2");
        form.setAccountNumber("PO-002");
        form.setEnabled(true);
        createdStoredPaymentModels.add(storedPaymentRepository.create(mockConnector, form));

        form = new SimpleBizStoredPaymentForm();
        form.setAccountType(MyDemoBizStoredPaymentModel.ACCOUNT_TYPE_PURCHASE_ORDER);
        form.setDisplayName("My purchase order 3");
        form.setAccountNumber("PO-003");
        form.setEnabled(false);
        createdStoredPaymentModels.add(storedPaymentRepository.create(mockConnector, form));
    }

    @After
    public void tearDown() throws Exception {
        VisitorContextAccess.removeCurrentVisitorContext();
        ModifiableRequestContextProvider.clear();
    }

    @Test
    public void testFindAll() throws Exception {
        final CommerceConnector mockConnector = createMockCommerceConnector("mydemo", "mydemoSpace");

        final PageResult<BizStoredPaymentModel> result = storedPaymentRepository.findAll(mockConnector,
                new QuerySpec());
        assertEquals(0, result.getOffset());
        assertEquals(3, result.getSize());
        assertEquals(3, result.getTotalSize());

        final Iterator<BizStoredPaymentModel> storedPaymentIt = result.iterator();

        BizStoredPaymentModel storedPayment = storedPaymentIt.next();
        assertNotNull(storedPayment);
        assertEquals("My purchase order 1", storedPayment.getName());
        assertEquals("My purchase order 1", storedPayment.getDisplayName());
        assertEquals(MyDemoBizStoredPaymentModel.ACCOUNT_TYPE_PURCHASE_ORDER, storedPayment.getAccountType());
        assertEquals("PO-001", storedPayment.getAccountNumber());
        assertTrue(storedPayment.isEnabled());

        storedPayment = storedPaymentIt.next();
        assertNotNull(storedPayment);
        assertEquals("My purchase order 2", storedPayment.getName());
        assertEquals("My purchase order 2", storedPayment.getDisplayName());
        assertEquals(MyDemoBizStoredPaymentModel.ACCOUNT_TYPE_PURCHASE_ORDER, storedPayment.getAccountType());
        assertEquals("PO-002", storedPayment.getAccountNumber());
        assertTrue(storedPayment.isEnabled());

        storedPayment = storedPaymentIt.next();
        assertNotNull(storedPayment);
        assertEquals("My purchase order 3", storedPayment.getName());
        assertEquals("My purchase order 3", storedPayment.getDisplayName());
        assertEquals(MyDemoBizStoredPaymentModel.ACCOUNT_TYPE_PURCHASE_ORDER, storedPayment.getAccountType());
        assertEquals("PO-003", storedPayment.getAccountNumber());
        assertFalse(storedPayment.isEnabled());
    }

    @Test
    public void testFindOne() throws Exception {
        final CommerceConnector mockConnector = createMockCommerceConnector("mydemo", "mydemoSpace");

        String storedPaymentId = createdStoredPaymentModels.get(0).getId();
        BizStoredPaymentModel storedPayment = storedPaymentRepository.findOne(mockConnector, storedPaymentId, new QuerySpec());
        assertNotNull(storedPayment);
        assertEquals("My purchase order 1", storedPayment.getName());
        assertEquals("My purchase order 1", storedPayment.getDisplayName());
        assertEquals(MyDemoBizStoredPaymentModel.ACCOUNT_TYPE_PURCHASE_ORDER, storedPayment.getAccountType());
        assertEquals("PO-001", storedPayment.getAccountNumber());
        assertTrue(storedPayment.isEnabled());

        storedPaymentId = createdStoredPaymentModels.get(1).getId();
        storedPayment = storedPaymentRepository.findOne(mockConnector, storedPaymentId, new QuerySpec());
        assertNotNull(storedPayment);
        assertEquals("My purchase order 2", storedPayment.getName());
        assertEquals("My purchase order 2", storedPayment.getDisplayName());
        assertEquals(MyDemoBizStoredPaymentModel.ACCOUNT_TYPE_PURCHASE_ORDER, storedPayment.getAccountType());
        assertEquals("PO-002", storedPayment.getAccountNumber());
        assertTrue(storedPayment.isEnabled());

        storedPaymentId = createdStoredPaymentModels.get(2).getId();
        storedPayment = storedPaymentRepository.findOne(mockConnector, storedPaymentId, new QuerySpec());
        assertNotNull(storedPayment);
        assertEquals("My purchase order 3", storedPayment.getName());
        assertEquals("My purchase order 3", storedPayment.getDisplayName());
        assertEquals(MyDemoBizStoredPaymentModel.ACCOUNT_TYPE_PURCHASE_ORDER, storedPayment.getAccountType());
        assertEquals("PO-003", storedPayment.getAccountNumber());
        assertFalse(storedPayment.isEnabled());
    }
}