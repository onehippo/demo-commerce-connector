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

import org.apache.commons.lang3.StringUtils;

import com.bloomreach.commercedxp.api.v2.connector.ConnectorException;
import com.bloomreach.commercedxp.api.v2.connector.model.PageResult;
import com.bloomreach.commercedxp.api.v2.connector.repository.QuerySpec;
import com.bloomreach.commercedxp.api.v2.connector.visitor.VisitorContext;
import com.bloomreach.commercedxp.api.v2.connector.visitor.VisitorContextAccess;
import com.bloomreach.commercedxp.b2b.api.v2.connector.model.BizInvoiceModel;
import com.bloomreach.commercedxp.b2b.api.v2.connector.repository.BizInvoiceRepository;
import com.bloomreach.commercedxp.starterstore.StarterStoreConstants;
import com.bloomreach.commercedxp.starterstore.connectors.CommerceConnector;

/**
 * Simple, demonstration-purpose {@link BizInvoiceRepository} implementation, maintaining the stored payment
 * data per account in in-memory map.
 */
public class MyDemoBizInvoiceRepositoryImpl implements BizInvoiceRepository {

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

        // TODO Auto-generated method stub
        return null;
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

        // TODO Auto-generated method stub
        return null;
    }

}
