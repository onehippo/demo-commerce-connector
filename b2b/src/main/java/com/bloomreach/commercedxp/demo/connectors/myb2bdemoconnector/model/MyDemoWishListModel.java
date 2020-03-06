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

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.bloomreach.commercedxp.api.v2.connector.model.ItemId;
import com.bloomreach.commercedxp.api.v2.connector.model.ItemLike;
import com.bloomreach.commercedxp.api.v2.connector.model.ItemListEntryModel;
import com.bloomreach.commercedxp.api.v2.connector.model.WishListModel;

public class MyDemoWishListModel implements WishListModel {

    private final String id;
    private String name;
    private List<ItemListEntryModel> entries;

    public MyDemoWishListModel(final String id, final String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public List<ItemListEntryModel> getEntries() {
        return (entries != null) ? Collections.unmodifiableList(entries) : Collections.emptyList();
    }

    public void addEntry(final ItemListEntryModel entry) {
        if (entries == null) {
            entries = new LinkedList<>();
        }

        entries.add(entry);
    }

    public ItemListEntryModel findEntryByItemId(final ItemId entryItemId) {
        if (entries == null) {
            return null;
        }

        for (ItemListEntryModel entry : entries) {
            for (ItemLike item : entry.getItems()) {
                if (entryItemId.equals(item.getItemId())) {
                    return entry;
                }
            }
        }

        return null;
    }

    public boolean removeEntryById(final String entryId) {
        if (entries == null) {
            return false;
        }

        for (Iterator<ItemListEntryModel> it = entries.iterator(); it.hasNext(); ) {
            final ItemListEntryModel entry = it.next();

            if (StringUtils.equals(entryId, entry.getId())) {
                it.remove();
                return true;
            }
        }

        return false;
    }
}
