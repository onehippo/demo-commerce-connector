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
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.bloomreach.commercedxp.api.v2.connector.ConnectorException;
import com.bloomreach.commercedxp.api.v2.connector.model.PageResult;
import com.bloomreach.commercedxp.api.v2.connector.model.SimplePageResult;
import com.bloomreach.commercedxp.api.v2.connector.repository.QuerySpec;
import com.bloomreach.commercedxp.api.v2.connector.visitor.VisitorContext;
import com.bloomreach.commercedxp.api.v2.connector.visitor.VisitorContextAccess;
import com.bloomreach.commercedxp.b2b.api.v2.connector.form.BizStoredPaymentForm;
import com.bloomreach.commercedxp.b2b.api.v2.connector.model.BizStoredPaymentModel;
import com.bloomreach.commercedxp.b2b.api.v2.connector.repository.BizStoredPaymentRepository;
import com.bloomreach.commercedxp.demo.connectors.myb2bdemoconnector.model.MyDemoBizStoredPaymentModel;
import com.bloomreach.commercedxp.starterstore.connectors.CommerceConnector;

/**
 * Simple, demonstration-purpose {@link BizStoredPaymentRepository} implementation, maintaining the stored payment
 * data per account in in-memory map.
 */
public class MyDemoBizStoredPaymentRepositoryImpl implements BizStoredPaymentRepository {

    private Map<String, Map<String, MyDemoBizStoredPaymentModel>> accountStoredPaymentsMap = Collections
            .synchronizedMap(new LinkedHashMap<>());

    @Override
    public BizStoredPaymentModel findOne(CommerceConnector connector, String id, QuerySpec querySpec)
            throws ConnectorException {
        if (!VisitorContextAccess.hasCurrentVisitorContext()) {
            throw new ConnectorException("401", "Expecting a visitor context");
        }

        final VisitorContext visitorContext = VisitorContextAccess.getCurrentVisitorContext();
        final String accountId = MyDemoAccountUtils.getVisitorAccountId(visitorContext);

        if (StringUtils.isBlank(accountId)) {
            throw new ConnectorException("403", "No account info found.");
        }

        final Map<String, MyDemoBizStoredPaymentModel> storedPaymentsMap = accountStoredPaymentsMap.get(accountId);
        return (storedPaymentsMap != null) ? storedPaymentsMap.get(id) : null;
    }

    @Override
    public PageResult<BizStoredPaymentModel> findAll(CommerceConnector connector, QuerySpec querySpec)
            throws ConnectorException {
        if (!VisitorContextAccess.hasCurrentVisitorContext()) {
            throw new ConnectorException("401", "Expecting a visitor context");
        }

        final VisitorContext visitorContext = VisitorContextAccess.getCurrentVisitorContext();
        final String accountId = MyDemoAccountUtils.getVisitorAccountId(visitorContext);

        if (StringUtils.isBlank(accountId)) {
            throw new ConnectorException("403", "No account info found.");
        }

        final Map<String, MyDemoBizStoredPaymentModel> storedPaymentsMap = accountStoredPaymentsMap.get(accountId);

        if (storedPaymentsMap == null) {
            return SimplePageResult.emptyResult();
        }

        return new SimplePageResult<>(storedPaymentsMap.values(), 0, storedPaymentsMap.size(),
                storedPaymentsMap.size());
    }

    @Override
    public BizStoredPaymentModel save(CommerceConnector connector, BizStoredPaymentForm resourceForm)
            throws ConnectorException {
        if (!VisitorContextAccess.hasCurrentVisitorContext()) {
            throw new ConnectorException("401", "Expecting a visitor context");
        }

        final VisitorContext visitorContext = VisitorContextAccess.getCurrentVisitorContext();
        final String accountId = MyDemoAccountUtils.getVisitorAccountId(visitorContext);

        if (StringUtils.isBlank(accountId)) {
            throw new ConnectorException("403", "No account info found.");
        }

        if (StringUtils.isBlank(resourceForm.getId())) {
            throw new IllegalArgumentException("Stored payment ID must not be null.");
        }

        if (StringUtils.isBlank(resourceForm.getAccountType())) {
            throw new IllegalArgumentException("Stored payment ID must not be null.");
        }

        if (StringUtils.isBlank(resourceForm.getDisplayName())) {
            throw new IllegalArgumentException("Stored payment ID must not be null.");
        }

        final Map<String, MyDemoBizStoredPaymentModel> storedPaymentsMap = accountStoredPaymentsMap.get(accountId);
        MyDemoBizStoredPaymentModel storedPayment = (storedPaymentsMap != null)
                ? storedPaymentsMap.get(resourceForm.getId())
                : null;

        if (storedPayment == null) {
            throw new ConnectorException("404", "Stored payment not found by ID, '" + resourceForm.getId() + "'.");
        }

        storedPayment.setDisplayName(resourceForm.getDisplayName());
        storedPayment.setAccountType(resourceForm.getAccountType());
        storedPayment.setAccountNumber(resourceForm.getAccountNumber());
        storedPayment.setEnabled(resourceForm.isEnabled());

        return storedPayment;
    }

    @Override
    public BizStoredPaymentModel create(CommerceConnector connector, BizStoredPaymentForm resourceForm)
            throws ConnectorException {
        if (!VisitorContextAccess.hasCurrentVisitorContext()) {
            throw new ConnectorException("401", "Expecting a visitor context");
        }

        final VisitorContext visitorContext = VisitorContextAccess.getCurrentVisitorContext();
        final String accountId = MyDemoAccountUtils.getVisitorAccountId(visitorContext);

        if (StringUtils.isBlank(accountId)) {
            throw new ConnectorException("403", "No account info found.");
        }

        if (StringUtils.isBlank(resourceForm.getAccountType())) {
            throw new IllegalArgumentException("Stored payment ID must not be null.");
        }

        if (StringUtils.isBlank(resourceForm.getDisplayName())) {
            throw new IllegalArgumentException("Stored payment ID must not be null.");
        }

        final MyDemoBizStoredPaymentModel storedPayment = new MyDemoBizStoredPaymentModel(UUID.randomUUID().toString(),
                resourceForm.getDisplayName(), resourceForm.getAccountType(), resourceForm.getAccountNumber());
        storedPayment.setDisplayName(resourceForm.getDisplayName());
        storedPayment.setEnabled(resourceForm.isEnabled());

        Map<String, MyDemoBizStoredPaymentModel> storedPaymentsMap = accountStoredPaymentsMap.get(accountId);

        if (storedPaymentsMap == null) {
            storedPaymentsMap = Collections.synchronizedMap(new LinkedHashMap<>());
            accountStoredPaymentsMap.put(accountId, storedPaymentsMap);
        }

        storedPaymentsMap.put(storedPayment.getId(), storedPayment);

        return storedPayment;
    }

    @Override
    public BizStoredPaymentModel delete(CommerceConnector connector, String resourceId) throws ConnectorException {
        if (!VisitorContextAccess.hasCurrentVisitorContext()) {
            throw new ConnectorException("401", "Expecting a visitor context");
        }

        final VisitorContext visitorContext = VisitorContextAccess.getCurrentVisitorContext();
        final String accountId = MyDemoAccountUtils.getVisitorAccountId(visitorContext);

        if (StringUtils.isBlank(accountId)) {
            throw new ConnectorException("403", "No account info found.");
        }

        if (StringUtils.isBlank(resourceId)) {
            throw new IllegalArgumentException("Stored payment ID must not be null.");
        }

        Map<String, MyDemoBizStoredPaymentModel> storedPaymentsMap = accountStoredPaymentsMap.get(accountId);

        if (storedPaymentsMap == null || !storedPaymentsMap.containsKey(resourceId)) {
            throw new ConnectorException("404", "Stored payment not found by ID, '" + resourceId + "'.");
        }

        return storedPaymentsMap.remove(resourceId);
    }

    @Override
    public BizStoredPaymentModel checkIn(CommerceConnector connector, BizStoredPaymentForm resourceForm)
            throws ConnectorException {
        throw new UnsupportedOperationException();
    }

    @Override
    public BizStoredPaymentModel checkOut(CommerceConnector connector, BizStoredPaymentForm resourceForm)
            throws ConnectorException {
        throw new UnsupportedOperationException();
    }

}
