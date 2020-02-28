/**
 * Copyright 2020 BloomReach Inc. (https://www.bloomreach.com/)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bloomreach.commercedxp.demo.connectors.myb2bdemoconnector.repository;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.bloomreach.commercedxp.api.v2.connector.ConnectorException;
import com.bloomreach.commercedxp.api.v2.connector.model.PageResult;
import com.bloomreach.commercedxp.api.v2.connector.model.SimplePageResult;
import com.bloomreach.commercedxp.api.v2.connector.repository.QuerySpec;
import com.bloomreach.commercedxp.api.v2.connector.visitor.VisitorContext;
import com.bloomreach.commercedxp.api.v2.connector.visitor.VisitorContextAccess;
import com.bloomreach.commercedxp.b2b.api.v2.connector.model.BizInvoiceModel;
import com.bloomreach.commercedxp.b2b.api.v2.connector.repository.BizInvoiceRepository;
import com.bloomreach.commercedxp.demo.connectors.myb2bdemoconnector.model.MyDemoBizInvoiceModel;
import com.bloomreach.commercedxp.starterstore.StarterStoreConstants;
import com.bloomreach.commercedxp.starterstore.connectors.CommerceConnector;

/**
 * Simple, demonstration-purpose {@link BizInvoiceRepository} implementation, maintaining the stored payment
 * data per account in in-memory map.
 * <P>
 * Note that this implementation is just for demonstration-purpose with a simple unit test.
 * In reality, the internal invoice data is generated differently when a customer places an order.
 * But in this demo implementation, it simply returns data from the package-private map, which can be updated
 * only from unit tests in the same package.
 */
public class MyDemoBizInvoiceRepositoryImpl implements BizInvoiceRepository {

    Map<String, Map<String, MyDemoBizInvoiceModel>> accountInvoicesMap = Collections
            .synchronizedMap(new LinkedHashMap<>());

    @Override
    public BizInvoiceModel findOne(CommerceConnector connector, String id, QuerySpec querySpec)
            throws ConnectorException {
        if (!VisitorContextAccess.hasCurrentVisitorContext()) {
            throw new ConnectorException("401", "Expecting a visitor context");
        }

        final VisitorContext visitorContext = VisitorContextAccess.getCurrentVisitorContext();
        final String accountId = (String) visitorContext.getAttribute(StarterStoreConstants.ATTRIBUTE_ACCOUNT_ID);

        if (StringUtils.isBlank(accountId)) {
            throw new ConnectorException("403", "No account info found.");
        }

        final Map<String, MyDemoBizInvoiceModel> invoicesMap = accountInvoicesMap.get(accountId);
        return (invoicesMap != null) ? invoicesMap.get(id) : null;
    }

    @Override
    public PageResult<BizInvoiceModel> findAll(CommerceConnector connector, QuerySpec querySpec)
            throws ConnectorException {
        if (!VisitorContextAccess.hasCurrentVisitorContext()) {
            throw new ConnectorException("401", "Expecting a visitor context");
        }

        final VisitorContext visitorContext = VisitorContextAccess.getCurrentVisitorContext();
        final String accountId = (String) visitorContext.getAttribute(StarterStoreConstants.ATTRIBUTE_ACCOUNT_ID);

        if (StringUtils.isBlank(accountId)) {
            throw new ConnectorException("403", "No account info found.");
        }

        final Map<String, MyDemoBizInvoiceModel> invoicesMap = accountInvoicesMap.get(accountId);

        if (invoicesMap == null) {
            return SimplePageResult.emptyResult();
        }

        return new SimplePageResult<>(invoicesMap.values(), 0, invoicesMap.size(), invoicesMap.size());
    }
}
