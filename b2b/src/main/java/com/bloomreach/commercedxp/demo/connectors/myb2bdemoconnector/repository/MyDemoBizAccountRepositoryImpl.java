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

import com.bloomreach.commercedxp.api.v2.connector.ConnectorException;
import com.bloomreach.commercedxp.api.v2.connector.form.AccountForm;
import com.bloomreach.commercedxp.api.v2.connector.model.AccountModel;
import com.bloomreach.commercedxp.api.v2.connector.model.PageResult;
import com.bloomreach.commercedxp.api.v2.connector.repository.AccountRepository;
import com.bloomreach.commercedxp.api.v2.connector.repository.QuerySpec;
import com.bloomreach.commercedxp.starterstore.connectors.CommerceConnector;

public class MyDemoBizAccountRepositoryImpl implements AccountRepository {

    @Override
    public AccountModel findOne(CommerceConnector connector, String id, QuerySpec querySpec) throws ConnectorException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PageResult<AccountModel> findAll(CommerceConnector connector, QuerySpec querySpec)
            throws ConnectorException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AccountModel save(CommerceConnector connector, AccountForm resourceForm) throws ConnectorException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AccountModel create(CommerceConnector connector, AccountForm resourceForm) throws ConnectorException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AccountModel delete(CommerceConnector connector, String resourceId) throws ConnectorException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AccountModel checkIn(CommerceConnector connector, AccountForm resourceForm) throws ConnectorException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AccountModel checkOut(CommerceConnector connector, AccountForm resourceForm) throws ConnectorException {
        // TODO Auto-generated method stub
        return null;
    }

}
