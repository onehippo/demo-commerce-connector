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
import java.util.Objects;

import com.bloomreach.commercedxp.api.v2.connector.model.ItemLike;
import com.bloomreach.commercedxp.api.v2.connector.model.ItemListEntryModel;
import com.bloomreach.commercedxp.api.v2.connector.model.ItemModel;

public class MyDemoItemListEntryModel implements ItemListEntryModel {

    private final String id;
    private int quantity;
    private List<ItemModel> items;

    public MyDemoItemListEntryModel(final String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public List<ItemLike> getItems() {
        if (items == null) {
            return Collections.emptyList();
        }

        return Collections.unmodifiableList(items);
    }

    public void setItems(List<ItemModel> items) {
        this.items = items;
    }

    public ItemModel addItem(ItemModel item) {
        if (items == null) {
            items = new LinkedList<>();
        }

        if (items.add(item)) {
            return item;
        }

        return null;
    }

    public ItemModel removeItem(ItemModel item) {
        if (items == null) {
            return null;
        }

        ItemModel toRemove = null;

        for (ItemModel itemModel : items) {
            if (itemModel.getItemId().equals(item.getItemId())) {
                toRemove = itemModel;
                break;
            }
        }

        if (toRemove != null) {
            if (items.remove(toRemove)) {
                return toRemove;
            }
        }

        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MyDemoItemListEntryModel)) {
            return false;
        }

        final MyDemoItemListEntryModel that = (MyDemoItemListEntryModel) o;

        if (!Objects.equals(id, that.id)) {
            return false;
        }

        if (quantity != that.quantity) {
            return false;
        }

        if (!Objects.equals(getItems(), that.getItems())) {
            return false;
        }

        return true;
    }
}
