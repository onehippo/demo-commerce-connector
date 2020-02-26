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
package com.bloomreach.commercedxp.demo.connectors.myb2bdemoconnector.model;

import com.bloomreach.commercedxp.b2b.api.v2.connector.model.BizStoredPaymentModel;

public class MyDemoBizStoredPaymentModel implements BizStoredPaymentModel {

    /**
     * Example stored payment type for purchase order payments.
     */
    public static final String ACCOUNT_TYPE_PURCHASE_ORDER = "po";

    /**
     * Example stored payment type for credit card payments.
     */
    public static final String ACCOUNT_TYPE_CREDIT_CARD = "card";

    private final String id;
    private String name;
    private String displayName;
    private String accountType;
    private String accountNumber;
    private boolean enabled = true;
    private boolean readOnly;
    private boolean defaultStoredPayment;

    public MyDemoBizStoredPaymentModel(final String id, final String name,
            final String accountType, final String accountNumber) {
        this.id = id;
        this.name = name;
        this.accountType = accountType;
        this.accountNumber = accountNumber;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    @Override
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    @Override
    public boolean isDefaultStoredPayment() {
        return defaultStoredPayment;
    }

    public void setDefaultStoredPayment(boolean defaultStoredPayment) {
        this.defaultStoredPayment = defaultStoredPayment;
    }
}
