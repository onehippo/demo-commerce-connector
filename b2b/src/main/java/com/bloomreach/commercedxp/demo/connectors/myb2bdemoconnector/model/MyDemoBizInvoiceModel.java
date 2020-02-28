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

import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import com.bloomreach.commercedxp.api.v2.connector.model.AccountModel;
import com.bloomreach.commercedxp.api.v2.connector.model.AddressModel;
import com.bloomreach.commercedxp.api.v2.connector.model.MoneyAmount;
import com.bloomreach.commercedxp.b2b.api.v2.connector.model.BizInvoiceItemModel;
import com.bloomreach.commercedxp.b2b.api.v2.connector.model.BizInvoiceModel;
import com.bloomreach.commercedxp.b2b.api.v2.connector.model.BizOrderModel;

public class MyDemoBizInvoiceModel implements BizInvoiceModel {

    private final String id;
    private String externalId;
    private String type;
    private String status;
    private String comments;
    private Calendar creationDate;
    private Calendar dueDate;
    private MoneyAmount originalAmount;
    private MoneyAmount paidAmount;
    private BizOrderModel order;
    private AccountModel soldToAccount;
    private AccountModel billToAccount;
    private AddressModel billAddress;

    private List<BizInvoiceItemModel> invoiceItems;

    public MyDemoBizInvoiceModel(final String id) {
        this.id = id;
        this.creationDate = Calendar.getInstance();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    @Override
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public Calendar getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Calendar creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public Calendar getDueDate() {
        return dueDate;
    }

    public void setDueDate(Calendar dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public MoneyAmount getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(MoneyAmount originalAmount) {
        this.originalAmount = originalAmount;
    }

    @Override
    public MoneyAmount getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(MoneyAmount paidAmount) {
        this.paidAmount = paidAmount;
    }

    @Override
    public Collection<BizInvoiceItemModel> getInvoiceItems() {
        return (invoiceItems != null) ? Collections.unmodifiableList(invoiceItems) : Collections.emptyList();
    }

    public void setInvoiceItems(List<BizInvoiceItemModel> invoiceItems) {
        this.invoiceItems = invoiceItems;
    }

    public void addInvoiceItem(BizInvoiceItemModel invoiceItem) {
        if (invoiceItems == null) {
            invoiceItems = new LinkedList<>();
        }

        invoiceItems.add(invoiceItem);
    }

    @Override
    public BizOrderModel getOrder() {
        return order;
    }

    public void setOrder(BizOrderModel order) {
        this.order = order;
    }

    @Override
    public AccountModel getSoldToAccount() {
        return soldToAccount;
    }

    public void setSoldToAccount(AccountModel soldToAccount) {
        this.soldToAccount = soldToAccount;
    }

    @Override
    public AccountModel getBillToAccount() {
        return billToAccount;
    }

    public void setBillToAccount(AccountModel billToAccount) {
        this.billToAccount = billToAccount;
    }

    @Override
    public AddressModel getBillAddress() {
        return billAddress;
    }

    public void setBillAddress(AddressModel billAddress) {
        this.billAddress = billAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MyDemoBizInvoiceModel)) {
            return false;
        }

        final MyDemoBizInvoiceModel that = (MyDemoBizInvoiceModel) o;

        if (!Objects.equals(id, that.id)) {
            return false;
        }

        if (!Objects.equals(externalId, that.externalId)) {
            return false;
        }

        if (!Objects.equals(type, that.type)) {
            return false;
        }

        if (!Objects.equals(status, that.status)) {
            return false;
        }

        if (!Objects.equals(comments, that.comments)) {
            return false;
        }

        if (!Objects.equals(creationDate, that.creationDate)) {
            return false;
        }

        if (!Objects.equals(dueDate, that.dueDate)) {
            return false;
        }

        if (!Objects.equals(originalAmount, that.originalAmount)) {
            return false;
        }

        if (!Objects.equals(paidAmount, that.paidAmount)) {
            return false;
        }

        if (!Objects.equals(order, that.order)) {
            return false;
        }

        if (!Objects.equals(soldToAccount, that.soldToAccount)) {
            return false;
        }

        if (!Objects.equals(billToAccount, that.billToAccount)) {
            return false;
        }

        if (!Objects.equals(billAddress, that.billAddress)) {
            return false;
        }

        if (!Objects.equals(invoiceItems, that.invoiceItems)) {
            return false;
        }

        return true;
    }
}
