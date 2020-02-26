/**
 * Copyright 2020 BloomReach Inc. (https://www.bloomreach.com/)
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

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import com.bloomreach.commercedxp.api.v2.connector.model.AccountModel;
import com.bloomreach.commercedxp.api.v2.connector.model.PageResult;
import com.bloomreach.commercedxp.api.v2.connector.repository.AccountRepository;
import com.bloomreach.commercedxp.api.v2.connector.repository.QuerySpec;
import com.bloomreach.commercedxp.common.v2.connector.form.SimpleAccountForm;
import com.bloomreach.commercedxp.starterstore.connectors.CommerceConnector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MyDemoBizAccountRepositoryImplTest extends AbstractMyDemoBizRepositoryTest {

    private AccountRepository accountRepository;

    @Before
    public void setUp() throws Exception {
        accountRepository = new MyDemoBizAccountRepositoryImpl();

        final CommerceConnector mockConnector = createMockCommerceConnector("mydemo", "mydemoSpace");
        accountRepository.create(mockConnector, new SimpleAccountForm("example1.com", "Example1, Inc."));
        accountRepository.create(mockConnector, new SimpleAccountForm("example2.com", "Example2, Inc."));
        accountRepository.create(mockConnector, new SimpleAccountForm("example3.com", "Example3, Inc."));
    }

    @Test
    public void testFindAll() throws Exception {
        final CommerceConnector mockConnector = createMockCommerceConnector("mydemo", "mydemoSpace");

        final PageResult<AccountModel> result = accountRepository.findAll(mockConnector, new QuerySpec());
        assertEquals(0, result.getOffset());
        assertEquals(3, result.getSize());
        assertEquals(3, result.getTotalSize());

        final Iterator<AccountModel> accountIt = result.iterator();

        AccountModel account = accountIt.next();
        assertNotNull(account);
        assertEquals("example1.com", account.getId());
        assertEquals("Example1, Inc.", account.getName());

        account = accountIt.next();
        account = accountRepository.findOne(mockConnector, "example2.com", new QuerySpec());
        assertNotNull(account);
        assertEquals("example2.com", account.getId());
        assertEquals("Example2, Inc.", account.getName());

        account = accountIt.next();
        account = accountRepository.findOne(mockConnector, "example3.com", new QuerySpec());
        assertNotNull(account);
        assertEquals("example3.com", account.getId());
        assertEquals("Example3, Inc.", account.getName());
    }

    @Test
    public void testFindOne() throws Exception {
        final CommerceConnector mockConnector = createMockCommerceConnector("mydemo", "mydemoSpace");

        AccountModel account = accountRepository.findOne(mockConnector, "example1.com", new QuerySpec());
        assertNotNull(account);
        assertEquals("example1.com", account.getId());
        assertEquals("Example1, Inc.", account.getName());

        account = accountRepository.findOne(mockConnector, "example2.com", new QuerySpec());
        assertNotNull(account);
        assertEquals("example2.com", account.getId());
        assertEquals("Example2, Inc.", account.getName());

        account = accountRepository.findOne(mockConnector, "example3.com", new QuerySpec());
        assertNotNull(account);
        assertEquals("example3.com", account.getId());
        assertEquals("Example3, Inc.", account.getName());
    }
}
