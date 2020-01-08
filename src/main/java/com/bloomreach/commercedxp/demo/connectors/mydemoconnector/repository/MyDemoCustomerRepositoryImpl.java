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

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.bloomreach.commercedxp.api.v2.connector.model.PageResult;
import com.bloomreach.commercedxp.api.v2.connector.repository.QuerySpec;
import org.apache.commons.lang3.StringUtils;

import com.bloomreach.commercedxp.api.v2.connector.ConnectorException;
import com.bloomreach.commercedxp.api.v2.connector.form.CustomerForm;
import com.bloomreach.commercedxp.api.v2.connector.model.CustomerModel;
import com.bloomreach.commercedxp.api.v2.connector.repository.AbstractCustomerRepository;
import com.bloomreach.commercedxp.demo.connectors.mydemoconnector.model.MyDemoCustomerModel;
import com.bloomreach.commercedxp.starterstore.connectors.CommerceConnector;

/**
 * Demo CustomerRepository Implementation.
 */
public class MyDemoCustomerRepositoryImpl extends AbstractCustomerRepository {

    /**
     * Let's keep the customer data in-memory here, initially empty.
     * So you need to sign-up first whenever once restarted.
     * Simple enough for the demo.
     */
    private Map<String, MyDemoCustomerModel> customerModels = new ConcurrentHashMap<>();

    @Override
    public CustomerModel findOne(CommerceConnector connector, String id, QuerySpec querySpec) throws ConnectorException {
        // For demo purpose, let's disallow to find customer profile if id is blank.
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException("Blank customer id.");
        }

        CustomerModel customerModel = null;
        for(final CustomerModel customer: customerModels.values()){
            if(customer.getId().equals(id)){
                customerModel = customer;
                break;
            }
        }

        if (customerModel == null) {
            throw new ConnectorException("401", "Customer not authenticated.");
        }

        return customerModel;
    }

    @Override
    public PageResult<CustomerModel> findAll(CommerceConnector connector, QuerySpec querySpec) throws ConnectorException {
        throw new UnsupportedOperationException();
    }

    @Override
    public CustomerModel save(CommerceConnector connector, CustomerForm resourceForm) throws ConnectorException {
        // For demo purpose, let's disallow to save customer profile if e-mail address is blank.
        if (StringUtils.isBlank(resourceForm.getEmail())) {
            throw new IllegalArgumentException("Blank customer E-Mail address.");
        }

        // Retrieve an existing customerModel from the in-memory map.
        final MyDemoCustomerModel customerModel = customerModels.get(resourceForm.getEmail());

        // If not existing, no customer exists in our demo.
        if (customerModel == null) {
            throw new ConnectorException("404", "Customer not found.");
        }

        // Let's update the model directly in the in-memory map.
        customerModel.setFirstName(resourceForm.getFirstName());
        customerModel.setLastName(resourceForm.getLastName());

        return customerModel;
    }

    @Override
    public CustomerModel create(CommerceConnector connector, CustomerForm resourceForm) throws ConnectorException {
        // For demo purpose, let's disallow to sign up customer if e-mail address is blank.
        if (StringUtils.isBlank(resourceForm.getEmail())) {
            throw new IllegalArgumentException("Blank customer's E-Mail address.");
        }

        // Let's create a customer model with a random ID and setting the other properties by the input.
        final MyDemoCustomerModel customerModel = new MyDemoCustomerModel(UUID.randomUUID().toString());
        customerModel.setEmail(resourceForm.getEmail());
        customerModel.setFirstName(resourceForm.getFirstName());
        customerModel.setLastName(resourceForm.getLastName());
        // setting a visitor specific access token, just for demonstration purpose, but will not be used in this demo.
        customerModel.setAccessToken(UUID.randomUUID().toString());

        // OK, let's register the new customer model in the in-memory map.
        customerModels.put(resourceForm.getEmail(), customerModel);

        return customerModel;
    }

    @Override
    public CustomerModel delete(CommerceConnector connector, String resourceId) throws ConnectorException {
        // We don't support customer removal in this demo.
        throw new UnsupportedOperationException();
    }

    @Override
    public CustomerModel checkIn(CommerceConnector connector, CustomerForm resourceForm) throws ConnectorException {
        // For demo purpose, let's disallow to sign in customer if e-mail address is blank.
        if (StringUtils.isBlank(resourceForm.getEmail())) {
            throw new IllegalArgumentException("Blank customer's E-Mail address.");
        }

        // CustomerRepository#checkIn(...) is invoked when StarterStore Application wants the customer to sign in.
        // For simplicity in our demo, let's just the customer signed in without having to check the password
        // if the customer model is found in the in-memory map.
        final MyDemoCustomerModel customerModel = customerModels.get(resourceForm.getEmail());

        if (customerModel == null) {
            throw new ConnectorException("401", "Customer not authenticated.");
        }

        return customerModel;
    }

    @Override
    public CustomerModel checkOut(CommerceConnector connector, CustomerForm resourceForm) throws ConnectorException {
        // For demo purpose, let's disallow to sign out customer if e-mail address is blank.
        if (StringUtils.isBlank(resourceForm.getEmail())) {
            throw new IllegalArgumentException("Blank customer's E-Mail address.");
        }

        // More advanced Commerce Connector Module might want to update the customer states in the backend
        // when a customer wants to sign out.
        // But in our demo, let's just return the customer model for simplicity.
        return customerModels.get(resourceForm.getEmail());
    }
}
