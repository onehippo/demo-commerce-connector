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
import com.bloomreach.commercedxp.api.v2.connector.form.AccountForm;
import com.bloomreach.commercedxp.api.v2.connector.model.AccountModel;
import com.bloomreach.commercedxp.api.v2.connector.model.PageResult;
import com.bloomreach.commercedxp.api.v2.connector.model.SimplePageResult;
import com.bloomreach.commercedxp.api.v2.connector.repository.AccountRepository;
import com.bloomreach.commercedxp.api.v2.connector.repository.QuerySpec;
import com.bloomreach.commercedxp.demo.connectors.myb2bdemoconnector.model.MyDemoAccountModel;
import com.bloomreach.commercedxp.starterstore.connectors.CommerceConnector;

public class MyDemoBizAccountRepositoryImpl implements AccountRepository {

    private Map<String, MyDemoAccountModel> accountsMap = Collections.synchronizedMap(new LinkedHashMap<>());

    @Override
    public AccountModel findOne(CommerceConnector connector, String id, QuerySpec querySpec) throws ConnectorException {
        MyDemoAccountModel account = accountsMap.get(id);

        // For simplicity and demonstration purpose, let's just create a new accountModel if not existing yet.
        if (account == null) {
            account = new MyDemoAccountModel(id, id);
            accountsMap.put(id, account);
        }

        return account;
    }

    @Override
    public PageResult<AccountModel> findAll(CommerceConnector connector, QuerySpec querySpec)
            throws ConnectorException {
        return new SimplePageResult<>(accountsMap.values(), 0, accountsMap.size(), accountsMap.size());
    }

    @Override
    public AccountModel save(CommerceConnector connector, AccountForm resourceForm) throws ConnectorException {
        if (StringUtils.isBlank(resourceForm.getId())) {
            throw new IllegalArgumentException("account id must not be blank.");
        }

        final MyDemoAccountModel account = accountsMap.get(resourceForm.getId());

        if (account == null) {
            throw new ConnectorException("404", "Account not found by the id, '" + resourceForm.getId() + "'.");
        }

        if (StringUtils.isBlank(resourceForm.getName())) {
            throw new IllegalArgumentException("account name must not be blank.");
        }

        account.setName(resourceForm.getName());

        return account;
    }

    @Override
    public AccountModel create(CommerceConnector connector, AccountForm resourceForm) throws ConnectorException {
        if (StringUtils.isBlank(resourceForm.getId())) {
            throw new IllegalArgumentException("account id must not be blank.");
        }

        if (StringUtils.isBlank(resourceForm.getName())) {
            throw new IllegalArgumentException("account name must not be blank.");
        }

        final MyDemoAccountModel account = new MyDemoAccountModel(resourceForm.getId(), resourceForm.getName());
        accountsMap.put(resourceForm.getId(), account);

        return account;
    }

    @Override
    public AccountModel delete(CommerceConnector connector, String resourceId) throws ConnectorException {
        if (StringUtils.isBlank(resourceId)) {
            throw new IllegalArgumentException("account id must not be blank.");
        }

        final MyDemoAccountModel account = accountsMap.get(resourceId);

        if (account == null) {
            throw new ConnectorException("404", "Account not found by the id, '" + resourceId + "'.");
        }

        accountsMap.remove(resourceId);

        return account;
    }

    @Override
    public AccountModel checkIn(CommerceConnector connector, AccountForm resourceForm) throws ConnectorException {
        throw new UnsupportedOperationException();
    }

    @Override
    public AccountModel checkOut(CommerceConnector connector, AccountForm resourceForm) throws ConnectorException {
        throw new UnsupportedOperationException();
    }

}
