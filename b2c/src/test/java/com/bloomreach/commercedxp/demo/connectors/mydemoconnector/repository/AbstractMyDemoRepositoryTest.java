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

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import com.bloomreach.commercedxp.starterstore.connectors.CommerceConnector;
import com.bloomreach.commercedxp.starterstore.connectors.CommerceConnectorComponent;
import com.bloomreach.commercedxp.starterstore.mock.connectors.MockCommerceConnector;
import com.bloomreach.commercedxp.starterstore.mock.connectors.MockCommerceConnectorComponent;

public abstract class AbstractMyDemoRepositoryTest {

    protected CommerceConnectorComponent createMockCommerceConnectorComponent(final String id,
            final String serviceBaseUrl, final String methodName, final Map<String, String> requestHeaders,
            final String requestBody) {
        final MockCommerceConnectorComponent component = new MockCommerceConnectorComponent(id);
        component.setServiceBaseUrl(serviceBaseUrl);
        component.setMethodType(methodName);
        component.setHeaders(requestHeaders);
        component.setRequestBody(requestBody);
        return component;
    }

    protected CommerceConnector createMockCommerceConnector(final String connectorId, final String resourceSpace,
            CommerceConnectorComponent... components) {
        return createMockCommerceConnector(connectorId, resourceSpace,
                (components != null) ? Arrays.asList(components) : Collections.emptyList());
    }

    protected CommerceConnector createMockCommerceConnector(final String connectorId, final String resourceSpace,
            final Collection<CommerceConnectorComponent> components) {
        MockCommerceConnector connector = new MockCommerceConnector(connectorId);
        connector.setResourceSpace(resourceSpace);
        connector.setComponents(components);
        return connector;
    }
}
