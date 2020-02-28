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

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bloomreach.commercedxp.api.v2.connector.ConnectorException;
import com.bloomreach.commercedxp.api.v2.connector.form.ItemListEntryForm;
import com.bloomreach.commercedxp.api.v2.connector.form.ItemListEntryForm.ACTION;
import com.bloomreach.commercedxp.api.v2.connector.form.WishListForm;
import com.bloomreach.commercedxp.api.v2.connector.model.PageResult;
import com.bloomreach.commercedxp.api.v2.connector.model.SimplePageResult;
import com.bloomreach.commercedxp.api.v2.connector.model.WishListModel;
import com.bloomreach.commercedxp.api.v2.connector.repository.QuerySpec;
import com.bloomreach.commercedxp.api.v2.connector.visitor.VisitorContext;
import com.bloomreach.commercedxp.api.v2.connector.visitor.VisitorContextAccess;
import com.bloomreach.commercedxp.b2b.api.v2.connector.repository.BizWishListRepository;
import com.bloomreach.commercedxp.demo.connectors.myb2bdemoconnector.model.MyDemoWishListModel;
import com.bloomreach.commercedxp.demo.connectors.mydemoconnector.model.MyDemoItemListEntryModel;
import com.bloomreach.commercedxp.demo.connectors.mydemoconnector.model.MyDemoProductItem;
import com.bloomreach.commercedxp.demo.connectors.mydemoconnector.repository.MyDemoDataLoader;
import com.bloomreach.commercedxp.starterstore.StarterStoreConstants;
import com.bloomreach.commercedxp.starterstore.connectors.CommerceConnector;

/**
 * Simple, demonstration-purpose {@link BizWishListRepository} implementation, maintaining the stored payment
 * data per account in in-memory map.
 */
public class MyDemoBizWishListRepositoryImpl implements BizWishListRepository {

    private static Logger log = LoggerFactory.getLogger(MyDemoBizWishListRepositoryImpl.class);

    private Map<String, Map<String, MyDemoWishListModel>> accountWishListsMap = Collections
            .synchronizedMap(new LinkedHashMap<>());

    @Override
    public WishListModel findOne(CommerceConnector connector, String id, QuerySpec querySpec)
            throws ConnectorException {
        if (!VisitorContextAccess.hasCurrentVisitorContext()) {
            throw new ConnectorException("401", "Expecting a visitor context");
        }

        final VisitorContext visitorContext = VisitorContextAccess.getCurrentVisitorContext();
        final String accountId = (String) visitorContext.getAttribute(StarterStoreConstants.ATTRIBUTE_ACCOUNT_ID);

        if (StringUtils.isBlank(accountId)) {
            throw new ConnectorException("403", "No account info found.");
        }

        final Map<String, MyDemoWishListModel> wishListsMap = accountWishListsMap.get(accountId);
        return (wishListsMap != null) ? wishListsMap.get(id) : null;
    }

    @Override
    public PageResult<WishListModel> findAll(CommerceConnector connector, QuerySpec querySpec)
            throws ConnectorException {
        if (!VisitorContextAccess.hasCurrentVisitorContext()) {
            throw new ConnectorException("401", "Expecting a visitor context");
        }

        final VisitorContext visitorContext = VisitorContextAccess.getCurrentVisitorContext();
        final String accountId = (String) visitorContext.getAttribute(StarterStoreConstants.ATTRIBUTE_ACCOUNT_ID);

        if (StringUtils.isBlank(accountId)) {
            throw new ConnectorException("403", "No account info found.");
        }

        final Map<String, MyDemoWishListModel> wishListsMap = accountWishListsMap.get(accountId);

        if (wishListsMap == null) {
            return SimplePageResult.emptyResult();
        }

        return new SimplePageResult<>(wishListsMap.values(), 0, wishListsMap.size(), wishListsMap.size());
    }

    @Override
    public WishListModel save(CommerceConnector connector, WishListForm resourceForm) throws ConnectorException {
        if (!VisitorContextAccess.hasCurrentVisitorContext()) {
            throw new ConnectorException("401", "Expecting a visitor context");
        }

        final VisitorContext visitorContext = VisitorContextAccess.getCurrentVisitorContext();
        final String accountId = (String) visitorContext.getAttribute(StarterStoreConstants.ATTRIBUTE_ACCOUNT_ID);

        if (StringUtils.isBlank(accountId)) {
            throw new ConnectorException("403", "No account info found.");
        }

        final Map<String, MyDemoWishListModel> wishListsMap = accountWishListsMap.get(accountId);
        final MyDemoWishListModel wishList = (wishListsMap != null) ? wishListsMap.get(resourceForm.getId()) : null;

        if (wishList == null) {
            throw new ConnectorException("404", "Wish list not found by ID, '" + resourceForm.getId() + "'.");
        }

        if (StringUtils.isNotBlank(resourceForm.getName())) {
            wishList.setName(resourceForm.getName());
        }

        updateEntriesByFormData(wishList, resourceForm);

        return wishList;
    }

    @Override
    public WishListModel create(CommerceConnector connector, WishListForm resourceForm) throws ConnectorException {
        if (!VisitorContextAccess.hasCurrentVisitorContext()) {
            throw new ConnectorException("401", "Expecting a visitor context");
        }

        final VisitorContext visitorContext = VisitorContextAccess.getCurrentVisitorContext();
        final String accountId = (String) visitorContext.getAttribute(StarterStoreConstants.ATTRIBUTE_ACCOUNT_ID);

        if (StringUtils.isBlank(accountId)) {
            throw new ConnectorException("403", "No account info found.");
        }

        final MyDemoWishListModel wishList = new MyDemoWishListModel(UUID.randomUUID().toString(),
                resourceForm.getName());
        updateEntriesByFormData(wishList, resourceForm);

        Map<String, MyDemoWishListModel> wishListsMap = accountWishListsMap.get(accountId);

        if (wishListsMap == null) {
            wishListsMap = Collections.synchronizedMap(new LinkedHashMap<>());
            accountWishListsMap.put(accountId, wishListsMap);
        }

        wishListsMap.put(wishList.getId(), wishList);

        return wishList;
    }

    @Override
    public WishListModel delete(CommerceConnector connector, String resourceId) throws ConnectorException {
        if (!VisitorContextAccess.hasCurrentVisitorContext()) {
            throw new ConnectorException("401", "Expecting a visitor context");
        }

        final VisitorContext visitorContext = VisitorContextAccess.getCurrentVisitorContext();
        final String accountId = (String) visitorContext.getAttribute(StarterStoreConstants.ATTRIBUTE_ACCOUNT_ID);

        if (StringUtils.isBlank(accountId)) {
            throw new ConnectorException("403", "No account info found.");
        }

        if (StringUtils.isBlank(resourceId)) {
            throw new IllegalArgumentException("Wish list ID must not be null.");
        }

        final Map<String, MyDemoWishListModel> wishListsMap = accountWishListsMap.get(accountId);

        if (wishListsMap == null || !wishListsMap.containsKey(resourceId)) {
            throw new ConnectorException("404", "Wish list not found by ID, '" + resourceId + "'.");
        }

        return wishListsMap.remove(resourceId);
    }

    @Override
    public WishListModel checkIn(CommerceConnector connector, WishListForm resourceForm) throws ConnectorException {
        throw new UnsupportedOperationException();
    }

    @Override
    public WishListModel checkOut(CommerceConnector connector, WishListForm resourceForm) throws ConnectorException {
        throw new UnsupportedOperationException();
    }

    private void updateEntriesByFormData(final MyDemoWishListModel wishList, final WishListForm resourceForm)
            throws ConnectorException {
        for (ItemListEntryForm entryForm : resourceForm.getEntries()) {
            final ACTION action = entryForm.getAction();
            MyDemoItemListEntryModel entry;

            switch (action) {
            case CREATE:
                entry = new MyDemoItemListEntryModel(UUID.randomUUID().toString());
                final String productId = entryForm.getEntryItemId().getId();
                final MyDemoProductItem productItem = MyDemoDataLoader.getMyDemoData().getResponse()
                        .getProductItemById(productId);
                if (productItem == null) {
                    throw new ConnectorException("400", "No product item found by ID, '" + productId + "'.");
                }
                entry.addItem(productItem);
                wishList.addEntry(entry);
                break;
            case UPDATE:
                entry = (MyDemoItemListEntryModel) wishList.findEntryByItemId(entryForm.getEntryItemId());
                if (entry == null) {
                    throw new ConnectorException("400", "No entry found by ItemID, '" + entryForm.getEntryItemId() + "'.");
                }
                entry.setQuantity(entryForm.getQuantity());
                break;
            case DELETE:
                entry = (MyDemoItemListEntryModel) wishList.findEntryByItemId(entryForm.getEntryItemId());
                if (entry == null) {
                    throw new ConnectorException("400", "No entry found by ItemID, '" + entryForm.getEntryItemId() + "'.");
                }
                wishList.removeEntryById(entry.getId());
                break;
            default:
                break;
            }
        }
    }
}
