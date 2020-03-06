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
package com.bloomreach.commercedxp.demo.connectors.myb2bdemoconnector.repository;

import org.junit.Before;

import com.bloomreach.commercedxp.api.v2.connector.ConnectorException;
import com.bloomreach.commercedxp.api.v2.connector.model.AccountModel;
import com.bloomreach.commercedxp.api.v2.connector.model.CustomerModel;
import com.bloomreach.commercedxp.api.v2.connector.repository.AccountRepository;
import com.bloomreach.commercedxp.b2b.api.v2.connector.repository.BizCustomerRepository;
import com.bloomreach.commercedxp.common.v2.connector.form.SimpleAccountForm;
import com.bloomreach.commercedxp.common.v2.connector.form.SimpleCustomerForm;
import com.bloomreach.commercedxp.starterstore.connectors.CommerceConnector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * Unit tests for my CustomerRepository implementation.
 */
public class MyDemoBizCustomerRepositoryImplTest extends AbstractMyDemoBizRepositoryTest {

    private AccountRepository accountRepository;
    private BizCustomerRepository customerRepository;

    @Before
    public void setUp() throws Exception {
        accountRepository = new MyDemoBizAccountRepositoryImpl();

        final CommerceConnector mockConnector = createMockCommerceConnector("mydemo", "mydemoSpace");
        accountRepository.create(mockConnector, new SimpleAccountForm("example.com", "Example, Inc."));

        customerRepository = new MyDemoBizCustomerRepositoryImpl();
        ((MyDemoBizCustomerRepositoryImpl) customerRepository).setAccountRepository(accountRepository);
    }

    @Before
    public void testCheckInWithoutSignup() throws Exception {
        // Create a mock CommerceConnector instance which simply sets the default (CRISP) resource space name
        // even if CRISP is not used in our demo module. See AbstractMyDemoRepositoryTest#createMockCommerceConnector()
        // for detail on how it can create a mock CommerceConnector and CommerceConnectorComponent instances using EasyMock. 
        final CommerceConnector mockConnector = createMockCommerceConnector("mydemo", "mydemoSpace");

        // Create a simple CustomerForm which is passed along from an application to the CustomerRepository,
        // with customer sign-in information.
        SimpleCustomerForm resourceForm = new SimpleCustomerForm("john@example.com", "password", "mystore");

        try {
            // This sign-in attempt should fail because there's no signed-up customer initially.
            customerRepository.checkIn(mockConnector, resourceForm);
            fail("Not supposed to sign-in by non-registered user.");
        } catch (ConnectorException expected) {
        }
    }

    @Before
    public void testCheckInOutAfterSignup() throws Exception {
        // Create a mock CommerceConnector instance which simply sets the default (CRISP) resource space name
        // even if CRISP is not used in our demo module. See AbstractMyDemoRepositoryTest#createMockCommerceConnector()
        // for detail on how it can create a mock CommerceConnector and CommerceConnectorComponent instances using EasyMock.
        final CommerceConnector mockConnector = createMockCommerceConnector("mydemo", "mydemoSpace");

        // Create a simple CustomerForm which is passed along from an application to the CustomerRepository,
        // with customer sign-up information.
        SimpleCustomerForm resourceForm = new SimpleCustomerForm("John", "", "Doe", "john@example.com", "password", "password", null);
        // When a customer signing up, StarterStore Application invokes CustomerRepository#create(...) operation.
        CustomerModel customerModel = customerRepository.create(mockConnector, resourceForm);

        // Let's validate the sign-up outcome, which should be a valid CustomerModel object
        // with the same values given by the CustomerForm object.
        assertEquals("John", customerModel.getFirstName());
        assertEquals("Doe", customerModel.getLastName());
        assertEquals("john@example.com", customerModel.getEmail());

        // All right. Let's try to sign-in with the customer.
        resourceForm = new SimpleCustomerForm("john@example.com", "password", "mystore");
        // When a customer signing in, StarterStore Application invokes CustomerRepository#checkIn(...) operation.
        customerModel = customerRepository.checkIn(mockConnector, resourceForm);

        // Let's validate the sign-in outcome, which should be a valid CustomerModel object.
        assertEquals("John", customerModel.getFirstName());
        assertEquals("Doe", customerModel.getLastName());
        assertEquals("john@example.com", customerModel.getEmail());

        AccountModel accountModel = customerModel.getAccount();
        assertNotNull(accountModel);
        assertEquals("example.com", accountModel.getId());
        assertEquals("Example, Inc.", accountModel.getName());

        // Now, let's sign-out.
        // When a customer signing out, StarterStore Application invokes CustomerRepository#checkOut(...) operation.
        customerModel = customerRepository.checkOut(mockConnector, resourceForm);

        // Let's validate the sign-out outcome, which should be the same valid CustomerModel object again.
        assertEquals("John", customerModel.getFirstName());
        assertEquals("Doe", customerModel.getLastName());
        assertEquals("john@example.com", customerModel.getEmail());
    }
}
