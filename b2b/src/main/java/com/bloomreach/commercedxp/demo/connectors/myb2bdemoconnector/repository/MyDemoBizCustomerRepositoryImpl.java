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
import com.bloomreach.commercedxp.api.v2.connector.form.CustomerForm;
import com.bloomreach.commercedxp.api.v2.connector.model.AccountModel;
import com.bloomreach.commercedxp.api.v2.connector.model.CustomerModel;
import com.bloomreach.commercedxp.api.v2.connector.repository.AccountRepository;
import com.bloomreach.commercedxp.api.v2.connector.repository.CustomerRepository;
import com.bloomreach.commercedxp.api.v2.connector.repository.QuerySpec;
import com.bloomreach.commercedxp.b2b.api.v2.connector.repository.BizCustomerRepository;
import com.bloomreach.commercedxp.demo.connectors.mydemoconnector.model.MyDemoCustomerModel;
import com.bloomreach.commercedxp.demo.connectors.mydemoconnector.repository.MyDemoCustomerRepositoryImpl;
import com.bloomreach.commercedxp.starterstore.connectors.CommerceConnector;

/**
 * Simple, demonstration-purpose {@link BizCustomerRepository} implementation, which extends the existing B2C
 * {@link CustomerRepository} implementation with an addition to set the {@link AccountModel} of the the specific
 * {@link CustomerModel} when the customer checks in (sign-in) or is retrieved.
 */
public class MyDemoBizCustomerRepositoryImpl extends MyDemoCustomerRepositoryImpl implements BizCustomerRepository {

    private AccountRepository accountRepository;

    public AccountRepository getAccountRepository() {
        return accountRepository;
    }

    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public CustomerModel findOne(CommerceConnector connector, String id, QuerySpec querySpec)
            throws ConnectorException {
        final CustomerModel customerModel = super.findOne(connector, id, querySpec);

        if (customerModel != null) {
            ((MyDemoCustomerModel) customerModel).setAccount(getAccountModelByCustomerModel(connector, customerModel));
        }

        return customerModel;
    }

    @Override
    public CustomerModel checkIn(CommerceConnector connector, CustomerForm resourceForm) throws ConnectorException {
        final CustomerModel customerModel = super.checkIn(connector, resourceForm);

        if (customerModel != null) {
            ((MyDemoCustomerModel) customerModel).setAccount(getAccountModelByCustomerModel(connector, customerModel));
        }

        return customerModel;
    }

    private AccountModel getAccountModelByCustomerModel(final CommerceConnector connector,
            final CustomerModel customerModel) {
        AccountModel accountModel = null;

        if (customerModel != null) {
            // For simplicity and demonstration purpose, let's assume the accountId is the same as domain name of customer's e-mail address.
            final String accountId = StringUtils.substringBefore(customerModel.getEmail(), "@");

            if (StringUtils.isNotBlank(accountId)) {
                accountModel = getAccountRepository().findOne(connector, accountId, new QuerySpec());
            }
        }

        return accountModel;
    }
}
