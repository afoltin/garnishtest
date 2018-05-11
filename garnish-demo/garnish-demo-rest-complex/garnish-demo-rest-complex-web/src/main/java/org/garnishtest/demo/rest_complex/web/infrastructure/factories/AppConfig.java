/*
 * Copyright 2016-2018, Garnish.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.garnishtest.demo.rest_complex.web.infrastructure.factories;

import org.garnishtest.demo.rest_complex.web.infrastructure.factories.dao.DaoConfig;
import org.garnishtest.demo.rest_complex.web.infrastructure.factories.infrastructure.InfrastructureConfig;
import org.garnishtest.demo.rest_complex.web.infrastructure.factories.service.ServiceConfig;
import org.garnishtest.demo.rest_complex.web.infrastructure.factories.web.WebConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@Import({
        InfrastructureConfig.class,
        DaoConfig.class,
        ServiceConfig.class,
        WebConfig.class,
})
public class AppConfig { }
