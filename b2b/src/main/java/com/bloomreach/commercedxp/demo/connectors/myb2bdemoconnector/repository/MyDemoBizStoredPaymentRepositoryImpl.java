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
import com.bloomreach.commercedxp.api.v2.connector.model.PageResult;
import com.bloomreach.commercedxp.api.v2.connector.repository.QuerySpec;
import com.bloomreach.commercedxp.b2b.api.v2.connector.form.BizStoredPaymentForm;
import com.bloomreach.commercedxp.b2b.api.v2.connector.model.BizStoredPaymentModel;
import com.bloomreach.commercedxp.b2b.api.v2.connector.repository.BizStoredPaymentRepository;
import com.bloomreach.commercedxp.starterstore.connectors.CommerceConnector;

public class MyDemoBizStoredPaymentRepositoryImpl implements BizStoredPaymentRepository {

    @Override
    public BizStoredPaymentModel findOne(CommerceConnector connector, String id, QuerySpec querySpec)
            throws ConnectorException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PageResult<BizStoredPaymentModel> findAll(CommerceConnector connector, QuerySpec querySpec)
            throws ConnectorException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public BizStoredPaymentModel save(CommerceConnector connector, BizStoredPaymentForm resourceForm)
            throws ConnectorException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public BizStoredPaymentModel create(CommerceConnector connector, BizStoredPaymentForm resourceForm)
            throws ConnectorException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public BizStoredPaymentModel delete(CommerceConnector connector, String resourceId) throws ConnectorException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public BizStoredPaymentModel checkIn(CommerceConnector connector, BizStoredPaymentForm resourceForm)
            throws ConnectorException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public BizStoredPaymentModel checkOut(CommerceConnector connector, BizStoredPaymentForm resourceForm)
            throws ConnectorException {
        // TODO Auto-generated method stub
        return null;
    }

}
