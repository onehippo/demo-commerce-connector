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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.hippoecm.hst.container.ModifiableRequestContextProvider;
import org.hippoecm.hst.mock.core.request.MockHstRequestContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bloomreach.commercedxp.api.v2.connector.model.PageResult;
import com.bloomreach.commercedxp.api.v2.connector.model.SimpleMoneyAmount;
import com.bloomreach.commercedxp.api.v2.connector.repository.QuerySpec;
import com.bloomreach.commercedxp.api.v2.connector.visitor.TransientVisitorAccessToken;
import com.bloomreach.commercedxp.api.v2.connector.visitor.TransientVisitorContext;
import com.bloomreach.commercedxp.api.v2.connector.visitor.VisitorContext;
import com.bloomreach.commercedxp.api.v2.connector.visitor.VisitorContextAccess;
import com.bloomreach.commercedxp.b2b.api.v2.connector.model.BizInvoiceModel;
import com.bloomreach.commercedxp.demo.connectors.myb2bdemoconnector.model.MyDemoBizInvoiceItemModel;
import com.bloomreach.commercedxp.demo.connectors.myb2bdemoconnector.model.MyDemoBizInvoiceModel;
import com.bloomreach.commercedxp.starterstore.StarterStoreConstants;
import com.bloomreach.commercedxp.starterstore.connectors.CommerceConnector;

import static org.junit.Assert.assertEquals;

public class MyDemoBizInvoiceRepositoryImplTest extends AbstractMyDemoBizRepositoryTest {

    private static final String DEFAULT_CREATION_DATE = "2020-02-28";
    private static final String DEFAULT_DUE_DATE = "2039-12-31";

    private Calendar defaultCreationDate;
    private Calendar defaultDueDate;

    private MyDemoBizInvoiceRepositoryImpl invoiceRepository;
    private List<MyDemoBizInvoiceModel> createdInvoices;

    @Before
    public void setUp() throws Exception {
        ModifiableRequestContextProvider.set(new MockHstRequestContext());
        final VisitorContext visitorContext = new TransientVisitorContext("user001", "john@example.com",
                new TransientVisitorAccessToken("token"), false);
        visitorContext.setAttribute(StarterStoreConstants.ATTRIBUTE_ACCOUNT_ID, "account001");
        VisitorContextAccess.setCurrentVisitorContext(visitorContext);

        invoiceRepository = new MyDemoBizInvoiceRepositoryImpl();

        defaultCreationDate = DateUtils.toCalendar(DateUtils.parseDate(DEFAULT_CREATION_DATE, "yyyy-MM-dd"));
        defaultDueDate = DateUtils.toCalendar(DateUtils.parseDate(DEFAULT_DUE_DATE, "yyyy-MM-dd"));

        Map<String, MyDemoBizInvoiceModel> invoicesMap = new LinkedHashMap<>();
        invoiceRepository.accountInvoicesMap.put("account001", invoicesMap);

        createdInvoices = new ArrayList<>();

        MyDemoBizInvoiceModel invoice = new MyDemoBizInvoiceModel("001");
        invoice.setExternalId("I-001");
        invoice.setType("Debit");
        invoice.setCreationDate(defaultCreationDate);
        invoice.setDueDate(defaultDueDate);
        invoice.setOriginalAmount(new SimpleMoneyAmount(new BigDecimal("106.25")));
        invoice.setPaidAmount(new SimpleMoneyAmount(new BigDecimal("0.0")));
        invoice.setStatus("Debit");

        MyDemoBizInvoiceItemModel invoiceItem = new MyDemoBizInvoiceItemModel("001-001");
        invoiceItem.setExternalId("I-001-001");
        invoiceItem.setType("Item");
        invoiceItem.setStatus("Open");
        invoiceItem.setOriginalAmount(new SimpleMoneyAmount(new BigDecimal("100.0")));
        invoice.addInvoiceItem(invoiceItem);

        invoiceItem = new MyDemoBizInvoiceItemModel("001-001");
        invoiceItem.setExternalId("I-001-001");
        invoiceItem.setType("Tax");
        invoiceItem.setStatus("Open");
        invoiceItem.setOriginalAmount(new SimpleMoneyAmount(new BigDecimal("6.25")));
        invoice.addInvoiceItem(invoiceItem);

        invoicesMap.put(invoice.getId(), invoice);
        createdInvoices.add(invoice);

        invoice = new MyDemoBizInvoiceModel("002");
        invoice.setExternalId("I-002");
        invoice.setType("Debit");
        invoice.setCreationDate(defaultCreationDate);
        invoice.setDueDate(defaultDueDate);
        invoice.setOriginalAmount(new SimpleMoneyAmount(new BigDecimal("1062.5")));
        invoice.setPaidAmount(new SimpleMoneyAmount(new BigDecimal("500.0")));
        invoice.setStatus("Debit");

        invoiceItem = new MyDemoBizInvoiceItemModel("001-001");
        invoiceItem.setExternalId("I-001-001");
        invoiceItem.setType("Item");
        invoiceItem.setStatus("Open");
        invoiceItem.setOriginalAmount(new SimpleMoneyAmount(new BigDecimal("1000.0")));
        invoice.addInvoiceItem(invoiceItem);

        invoiceItem = new MyDemoBizInvoiceItemModel("001-001");
        invoiceItem.setExternalId("I-001-001");
        invoiceItem.setType("Tax");
        invoiceItem.setStatus("Open");
        invoiceItem.setOriginalAmount(new SimpleMoneyAmount(new BigDecimal("62.5")));
        invoice.addInvoiceItem(invoiceItem);

        invoicesMap.put(invoice.getId(), invoice);
        createdInvoices.add(invoice);
    }

    @After
    public void tearDown() throws Exception {
        VisitorContextAccess.removeCurrentVisitorContext();
        ModifiableRequestContextProvider.clear();
    }

    @Test
    public void testFindAll() throws Exception {
        final CommerceConnector mockConnector = createMockCommerceConnector("mydemo", "mydemoSpace");

        final PageResult<BizInvoiceModel> result = invoiceRepository.findAll(mockConnector, new QuerySpec());
        assertEquals(0, result.getOffset());
        assertEquals(2, result.getSize());
        assertEquals(2, result.getTotalSize());

        final Iterator<BizInvoiceModel> invoiceIt = result.iterator();

        BizInvoiceModel invoice = invoiceIt.next();
        String expectedInvoiceId = createdInvoices.get(0).getId();
        assertEquals(invoiceRepository.accountInvoicesMap.get("account001").get(expectedInvoiceId), invoice);

        invoice = invoiceIt.next();
        expectedInvoiceId = createdInvoices.get(1).getId();
        assertEquals(invoiceRepository.accountInvoicesMap.get("account001").get(expectedInvoiceId), invoice);
    }

    @Test
    public void testFindOne() throws Exception {
        final CommerceConnector mockConnector = createMockCommerceConnector("mydemo", "mydemoSpace");

        String expectedInvoiceId = createdInvoices.get(0).getId();
        BizInvoiceModel invoice = invoiceRepository.findOne(mockConnector, expectedInvoiceId, new QuerySpec());
        assertEquals(invoiceRepository.accountInvoicesMap.get("account001").get(expectedInvoiceId), invoice);

        expectedInvoiceId = createdInvoices.get(1).getId();
        invoice = invoiceRepository.findOne(mockConnector, expectedInvoiceId, new QuerySpec());
        assertEquals(invoiceRepository.accountInvoicesMap.get("account001").get(expectedInvoiceId), invoice);
    }
}