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

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.bloomreach.commercedxp.api.v2.connector.model.CartEntryModel;
import com.bloomreach.commercedxp.api.v2.connector.model.CartModel;

public class MyDemoCartModel implements CartModel {

    private final String id;
    private final String username;
    private int totalQuantity = -1;
    private List<CartEntryModel> cartEntries;
    private String orderId;
    private Long revision;

    public MyDemoCartModel(final String id, final String username) {
        this.id = id;
        this.username = username;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public int getTotalQuantity() {
        if (totalQuantity == -1) {
            if (cartEntries == null || cartEntries.isEmpty()) {
                return 0;
            }

            int sum = 0;
            for (CartEntryModel entry : cartEntries) {
                sum += entry.getQuantity();
            }
            return sum;
        }

        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    @Override
    public List<CartEntryModel> getEntries() {
        if (cartEntries == null) {
            return Collections.emptyList();
        }

        return Collections.unmodifiableList(cartEntries);
    }

    public CartEntryModel addEntry(final CartEntryModel entry) {
        if (cartEntries == null) {
            cartEntries = new LinkedList<>();
        }

        if (cartEntries.add(entry)) {
            return entry;
        }

        return null;
    }

    public CartEntryModel removeEntryById(final String id) {
        if (cartEntries == null) {
            return null;
        }

        CartEntryModel toRemove = null;

        for (CartEntryModel entryModel : cartEntries) {
            if (entryModel.getId().equals(id)) {
                toRemove = entryModel;
                break;
            }
        }

        if (toRemove != null) {
            if (cartEntries.remove(toRemove)) {
                return toRemove;
            }
        }

        return null;
    }

    public CartEntryModel getEntryById(final String id) {
        if (cartEntries == null) {
            return null;
        }

        for (CartEntryModel entryModel : cartEntries) {
            if (entryModel.getId().equals(id)) {
                return entryModel;
            }
        }

        return null;
    }

    @Override
    public String getOrderId() {
        return orderId;
    }

    @Override
    public Long getRevision() {
        return revision;
    }

    public void setRevision(Long revision) {
        this.revision = revision;
    }
}
