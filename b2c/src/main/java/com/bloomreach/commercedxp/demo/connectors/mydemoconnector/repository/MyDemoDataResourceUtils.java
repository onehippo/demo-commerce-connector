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
package com.bloomreach.commercedxp.demo.connectors.mydemoconnector.repository;

import java.io.File;
import java.net.URI;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;

/**
 * Simple utility to resolve resource URLs. e.g, /path/a/b/c.csv, classpath:/a/b/c.csv, etc.
 */
final class MyDemoDataResourceUtils {

    private MyDemoDataResourceUtils() {
    }

    static URL getResource(final String location) {
        if (StringUtils.isBlank(location)) {
            return null;
        }

        try {
            if (StringUtils.startsWith(location, "classpath:")) {
                return MyDemoDataResourceUtils.class.getResource(StringUtils.substringAfter(location, "classpath:"));
            } else if (StringUtils.startsWith(location, "file:") || StringUtils.startsWith(location, "http:")
                    || StringUtils.startsWith(location, "https:")) {
                return URI.create(location).toURL();
            } else {
                return new File(location).toURI().toURL();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
