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

import java.util.Objects;

import com.bloomreach.commercedxp.api.v2.connector.model.MoneyAmount;
import com.bloomreach.commercedxp.api.v2.connector.model.OrderItemModel;
import com.bloomreach.commercedxp.b2b.api.v2.connector.model.BizInvoiceItemModel;

public class MyDemoBizInvoiceItemModel implements BizInvoiceItemModel {

    private final String id;
    private String externalId;
    private String type;
    private String status;
    private String comments;
    private MoneyAmount originalAmount;
    private OrderItemModel orderItem;

    public MyDemoBizInvoiceItemModel(final String id) {
        this.id = id;
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
    public MoneyAmount getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(MoneyAmount originalAmount) {
        this.originalAmount = originalAmount;
    }

    @Override
    public OrderItemModel getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItemModel orderItem) {
        this.orderItem = orderItem;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MyDemoBizInvoiceItemModel)) {
            return false;
        }

        final MyDemoBizInvoiceItemModel that = (MyDemoBizInvoiceItemModel) o;

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

        if (!Objects.equals(originalAmount, that.originalAmount)) {
            return false;
        }

        if (!Objects.equals(orderItem, that.orderItem)) {
            return false;
        }

        return true;
    }
}
