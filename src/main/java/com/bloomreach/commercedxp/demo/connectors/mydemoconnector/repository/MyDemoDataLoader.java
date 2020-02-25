/**
 * Copyright 2019-2020 BloomReach Inc. (https://www.bloomreach.com/)
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

import java.io.InputStream;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;

import com.bloomreach.commercedxp.demo.connectors.mydemoconnector.model.MyDemoCategoryModel;
import com.bloomreach.commercedxp.demo.connectors.mydemoconnector.model.MyDemoData;
import com.bloomreach.commercedxp.demo.connectors.mydemoconnector.model.MyDemoProductItem;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

/**
 * Demo Data Loader Utility.
 */
final class MyDemoDataLoader {

    /**
     * The name of the optional system property which specifies demo products data in CSV format.
     */
    static final String SYS_PROP_DEMO_PRODUCTS_CSV = "demo.products.csv";

    /**
     * The name of the optional system property which specifies demo categories data in CSV format.
     */
    static final String SYS_PROP_DEMO_CATEGORIES_CSV = "demo.categories.csv";

    /**
     * Default demo product data JSON resource path in BrSM product response data format.
     */
    private static final String DEFAULT_PRODUCT_DATA_JSON_RESOURCE = "com/bloomreach/commercedxp/demo/connectors/mydemoconnector/demoproducts.json";

    private static MyDemoData loadMyDemoData() {
        MyDemoData demoData = null;

        // First, load product/catalog data from the default built-in json file.
        try (InputStream input = MyDemoDataLoader.class.getClassLoader()
                .getResourceAsStream(DEFAULT_PRODUCT_DATA_JSON_RESOURCE)) {
            final ObjectMapper objectMapper = new ObjectMapper();
            demoData = objectMapper.readValue(input, MyDemoData.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (demoData != null) {
            // If custom product data file is provided in csv format, replace product data by the data in the CSV.
            final String productsCsvProp = System.getProperty(SYS_PROP_DEMO_PRODUCTS_CSV);

            if (StringUtils.isNotBlank(productsCsvProp)) {
                final URL productsCsvUrl = MyDemoDataResourceUtils.getResource(productsCsvProp);

                if (productsCsvUrl != null) {
                    try (InputStream input = productsCsvUrl.openStream()) {
                        final CsvSchema csvSchema = CsvSchema.emptySchema().withHeader();
                        final ObjectMapper csvMapper = new CsvMapper();
                        final MappingIterator<MyDemoProductItem> mappingIt = csvMapper
                                .readerFor(MyDemoProductItem.class).with(csvSchema).readValues(input);
                        demoData.getResponse().setProductItems(mappingIt.readAll());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            // If custom category data file is provided in csv format, replace category data by the data in the CSV.
            final String categoriesCsvProp = System.getProperty(SYS_PROP_DEMO_CATEGORIES_CSV);

            if (StringUtils.isNotBlank(categoriesCsvProp)) {
                final URL categoriesCsvUrl = MyDemoDataResourceUtils.getResource(categoriesCsvProp);

                if (categoriesCsvUrl != null) {
                    try (InputStream input = categoriesCsvUrl.openStream()) {
                        final CsvSchema csvSchema = CsvSchema.emptySchema().withHeader();
                        final ObjectMapper csvMapper = new CsvMapper();
                        final MappingIterator<MyDemoCategoryModel> mappingIt = csvMapper
                                .readerFor(MyDemoCategoryModel.class).with(csvSchema).readValues(input);
                        demoData.setCategoryModels(mappingIt.readAll());
                        demoData.resetCategoryModelHierarchy();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return demoData;
    }

    static MyDemoData myDemoData;

    private MyDemoDataLoader() {
    }

    /**
     * Return the static MyDemoData instance which is loaded from the static JSON resource.
     */
    static synchronized MyDemoData getMyDemoData() {
        MyDemoData data = myDemoData;

        if (data == null) {
            data = loadMyDemoData();
            myDemoData = data;
        }

        return data;
    }

    /**
     * Clear the existing static MyDemoData instance. e.g, from unit test to reset the data.
     */
    static synchronized void clearMyDemoData() {
        myDemoData = null;
    }
}
