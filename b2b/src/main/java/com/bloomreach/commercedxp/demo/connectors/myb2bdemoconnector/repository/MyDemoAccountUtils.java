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

import com.bloomreach.commercedxp.api.v2.connector.visitor.VisitorContext;
import com.bloomreach.commercedxp.starterstore.StarterStoreConstants;

/**
 * Utility for demo environment just to make it work without having to have a persistent storage for account data.
 */
final class MyDemoAccountUtils {

    private MyDemoAccountUtils() {
    }

    /**
     * Find the visitor's accountId from the {@link VisitorContext} attribute if found.
     * Otherwise, let's simply use the domain name of the user's e-mail address, which is the same as the username
     * in this specific (or presumed) demo environment.
     * @param visitorContext visitor context
     * @return the visitor's accountId from the {@link VisitorContext} attribute if found, or the domain name of
     *         the visitor's e-mail address.
     */
    public static String getVisitorAccountId(final VisitorContext visitorContext) {
        if (visitorContext == null) {
            return null;
        }

        String accountId = (String) visitorContext.getAttribute(StarterStoreConstants.ATTRIBUTE_ACCOUNT_ID);

        if (accountId == null) {
            return StringUtils.substringAfter(visitorContext.getUsername(), "@");
        }

        return accountId;
    }
}
