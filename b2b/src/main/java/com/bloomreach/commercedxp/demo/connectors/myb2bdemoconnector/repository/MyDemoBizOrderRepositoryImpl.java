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

import com.bloomreach.commercedxp.api.v2.connector.repository.OrderRepository;
import com.bloomreach.commercedxp.b2b.api.v2.connector.repository.BizOrderRepository;
import com.bloomreach.commercedxp.demo.connectors.mydemoconnector.repository.MyDemoOrderRepositoryImpl;

/**
 * Simple, demonstration-purpose {@link BizOrderRepository} implementation, which extends the existing B2C
 * {@link OrderRepository} implementation as there's no big difference between B2C and B2B yet.
 */
public class MyDemoBizOrderRepositoryImpl extends MyDemoOrderRepositoryImpl implements BizOrderRepository {

}
