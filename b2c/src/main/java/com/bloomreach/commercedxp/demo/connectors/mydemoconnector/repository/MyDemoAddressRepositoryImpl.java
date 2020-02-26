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

import com.bloomreach.commercedxp.api.v2.connector.ConnectorException;
import com.bloomreach.commercedxp.api.v2.connector.form.CustomerForm;
import com.bloomreach.commercedxp.api.v2.connector.model.AddressModel;
import com.bloomreach.commercedxp.api.v2.connector.repository.AbstractAddressRepository;
import com.bloomreach.commercedxp.starterstore.connectors.CommerceConnector;

public class MyDemoAddressRepositoryImpl extends AbstractAddressRepository {

    @Override
    public AddressModel save(CommerceConnector connector, CustomerForm resourceForm) throws ConnectorException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AddressModel create(CommerceConnector connector, CustomerForm resourceForm) throws ConnectorException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AddressModel delete(CommerceConnector connector, String resourceId) throws ConnectorException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AddressModel checkIn(CommerceConnector connector, CustomerForm resourceForm) throws ConnectorException {
        throw new UnsupportedOperationException();
    }

    @Override
    public AddressModel checkOut(CommerceConnector connector, CustomerForm resourceForm) throws ConnectorException {
        throw new UnsupportedOperationException();
    }

}
