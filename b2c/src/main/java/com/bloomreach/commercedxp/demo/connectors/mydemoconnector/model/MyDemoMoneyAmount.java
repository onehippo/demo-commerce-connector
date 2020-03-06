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
package com.bloomreach.commercedxp.demo.connectors.mydemoconnector.model;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import com.bloomreach.commercedxp.api.v2.connector.model.MoneyAmount;

public class MyDemoMoneyAmount implements MoneyAmount {

    private final Currency currency;
    private final BigDecimal amount;
    private final String displayValue;

    public MyDemoMoneyAmount(final Currency currency, final BigDecimal amount) {
        this(currency, amount, null);
    }

    public MyDemoMoneyAmount(final Currency currency, final BigDecimal amount, final String displayValue) {
        this.currency = currency;
        this.amount = amount;
        this.displayValue = displayValue;
    }

    @Override
    public Currency getCurrency() {
        return currency;
    }

    @Override
    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public String getDisplayValue() {
        if (displayValue == null) {
            return currency.getSymbol(Locale.getDefault()) + amount;
        }

        return displayValue;
    }

}
