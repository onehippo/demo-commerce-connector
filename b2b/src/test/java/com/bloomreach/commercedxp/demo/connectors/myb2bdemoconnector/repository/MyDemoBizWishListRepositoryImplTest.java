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

import org.hippoecm.hst.container.ModifiableRequestContextProvider;
import org.hippoecm.hst.mock.core.request.MockHstRequestContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bloomreach.commercedxp.api.v2.connector.visitor.TransientVisitorAccessToken;
import com.bloomreach.commercedxp.api.v2.connector.visitor.TransientVisitorContext;
import com.bloomreach.commercedxp.api.v2.connector.visitor.VisitorContext;
import com.bloomreach.commercedxp.api.v2.connector.visitor.VisitorContextAccess;
import com.bloomreach.commercedxp.starterstore.StarterStoreConstants;

public class MyDemoBizWishListRepositoryImplTest extends AbstractMyDemoBizRepositoryTest {

    @Before
    public void setUp() throws Exception {
        ModifiableRequestContextProvider.set(new MockHstRequestContext());
        final VisitorContext visitorContext = new TransientVisitorContext("user001", "john@example.com",
                new TransientVisitorAccessToken("token"), false);
        visitorContext.setAttribute(StarterStoreConstants.ATTRIBUTE_ACCOUNT_ID, "account001");
        VisitorContextAccess.setCurrentVisitorContext(visitorContext);
    }

    @After
    public void tearDown() throws Exception {
        VisitorContextAccess.removeCurrentVisitorContext();
        ModifiableRequestContextProvider.clear();
    }

    @Test
    public void testFindAll() throws Exception {
    }
}
