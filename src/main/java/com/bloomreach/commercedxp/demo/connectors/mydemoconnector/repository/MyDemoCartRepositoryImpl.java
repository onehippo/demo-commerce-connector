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
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;

import com.bloomreach.commercedxp.api.v2.connector.ConnectorException;
import com.bloomreach.commercedxp.api.v2.connector.form.CartEntryForm;
import com.bloomreach.commercedxp.api.v2.connector.form.CartEntryForm.ACTION;
import com.bloomreach.commercedxp.api.v2.connector.form.CartForm;
import com.bloomreach.commercedxp.api.v2.connector.model.CartModel;
import com.bloomreach.commercedxp.api.v2.connector.repository.AbstractCartRepository;
import com.bloomreach.commercedxp.api.v2.connector.visitor.VisitorContext;
import com.bloomreach.commercedxp.api.v2.connector.visitor.VisitorContextAccess;
import com.bloomreach.commercedxp.demo.connectors.mydemoconnector.model.MyDemoCartEntryModel;
import com.bloomreach.commercedxp.demo.connectors.mydemoconnector.model.MyDemoCartModel;
import com.bloomreach.commercedxp.demo.connectors.mydemoconnector.model.MyDemoProductItem;
import com.bloomreach.commercedxp.starterstore.connectors.CommerceConnector;

public class MyDemoCartRepositoryImpl extends AbstractCartRepository {

    private Map<String, MyDemoCartModel> cartModels = new ConcurrentHashMap<>();

    @Override
    public CartModel create(CommerceConnector connector, CartForm resourceForm) throws ConnectorException {
        final VisitorContext visitorContext = VisitorContextAccess.getCurrentVisitorContext();

        if (visitorContext == null) {
            throw new ConnectorException("401", "No cart for anonymous user at the moment.");
        }

        final String username = visitorContext.getUsername();
        String cartId = resourceForm.getId();

        if (StringUtils.isBlank(cartId)) {
            cartId = UUID.randomUUID().toString();
        }

        final MyDemoCartModel cartModel = new MyDemoCartModel(cartId, username);

        for (CartEntryForm entryForm : resourceForm.getEntries()) {
            cartModel.addEntry(createCartEntryModel(entryForm));
        }

        cartModels.put(username, cartModel);

        return cartModel;
    }

    @Override
    public CartModel save(CommerceConnector connector, CartForm resourceForm) throws ConnectorException {
        final VisitorContext visitorContext = VisitorContextAccess.getCurrentVisitorContext();

        if (visitorContext == null) {
            throw new ConnectorException("401", "No cart for anonymous user at the moment.");
        }

        final String username = visitorContext.getUsername();
        final MyDemoCartModel cartModel = cartModels.get(username);

        if (cartModel == null) {
            throw new ConnectorException("404", "Cart not found.");
        }

        for (CartEntryForm entryForm : resourceForm.getEntries()) {
            final ACTION action = entryForm.getAction();

            switch (action) {
            case CREATE:
                cartModel.addEntry(createCartEntryModel(entryForm));
                break;
            case UPDATE:
                final MyDemoCartEntryModel entryModel = (MyDemoCartEntryModel) cartModel.getEntryById(entryForm.getEntryItemId().getId());
                if (entryModel != null) {
                    entryModel.setQuantity(entryForm.getQuantity());
                }
                break;
            case DELETE:
                cartModel.removeEntryById(entryForm.getEntryItemId().getId());
                break;
            default:
                break;
            }
        }

        return cartModel;
    }

    @Override
    public CartModel delete(CommerceConnector connector, String resourceId) throws ConnectorException {
        throw new UnsupportedOperationException();
    }

    @Override
    public CartModel checkIn(CommerceConnector connector, CartForm resourceForm) throws ConnectorException {
        final VisitorContext visitorContext = VisitorContextAccess.getCurrentVisitorContext();

        if (visitorContext == null) {
            throw new ConnectorException("401", "No cart for anonymous user at the moment.");
        }

        final String username = visitorContext.getUsername();
        MyDemoCartModel cartModel = cartModels.get(username);

        if (cartModel == null) {
            cartModel = (MyDemoCartModel) create(connector, resourceForm);
        }

        return cartModel;
    }

    @Override
    public CartModel checkOut(CommerceConnector connector, CartForm resourceForm) throws ConnectorException {
        // TODO Auto-generated method stub
        return null;
    }

    private MyDemoCartEntryModel createCartEntryModel(final CartEntryForm entryForm) {
        final String productItemId = entryForm.getEntryItemId().getId();
        final MyDemoCartEntryModel entryModel = new MyDemoCartEntryModel(productItemId);
        entryModel.setQuantity(entryForm.getQuantity());

        final MyDemoProductItem productItem = MyDemoDataLoader.getMyDemoData().getResponse()
                .getProductItemById(productItemId);

        if (productItem != null) {
            entryModel.setItems(Arrays.asList(productItem));
        }

        return entryModel;
    }
}
