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

package org.garnishtest.steps.restclient.spring;

import org.garnishtest.modules.generic.httpclient.SimpleHttpClient;
import org.garnishtest.modules.generic.httpclient.request_preparer.HttpRequestPreparer;
import org.garnishtest.modules.generic.httpclient.request_preparer.impl.CompositeHttpRequestPreparer;
import org.garnishtest.steps.restclient.auth.preparer.AuthenticationHttpRequestPreparer;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.garnishtest.steps.restclient.spring.RestClientBeanDefinitionParser.ROOT_BEAN_ID;
import static org.joor.Reflect.on;

public class RestClientBeanDefinitionParserTest {

    // NOTE: this test is really fragile, since it uses reflection, but I have no idea how else to test

    @Test
    public void without_request_preparers() throws Exception {
        final ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                "/META-INF/spring/test-garnish-rest-client-beanDefinitionParser-no-request-preparers.xml"
        );

        final SimpleHttpClient simpleHttpClient = applicationContext.getBean(ROOT_BEAN_ID, SimpleHttpClient.class);

        final String baseUrl = on(simpleHttpClient).field("baseUrl").get().toString();
        assertThat(baseUrl).isEqualTo("http://www.example.com/no-request-parsers");

        final CompositeHttpRequestPreparer httpRequestPreparer = on(simpleHttpClient)
                                                                        .field("requestExecutor")     // HttpRequestExecutor
                                                                        .field("httpRequestPreparer") // CompositeHttpRequestPreparer
                                                                        .get();
        final List<HttpRequestPreparer> preparers = on(httpRequestPreparer)
                                                                        .field("preparers")           // List<HttpRequestPreparer>
                                                                        .get();
        assertThat(preparers).hasSize(1);
        assertThat(preparers.get(0)).isInstanceOf(AuthenticationHttpRequestPreparer.class);
    }

    @Test
    public void with_request_preparers() throws Exception {
        final ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                "/META-INF/spring/test-garnish-rest-client-beanDefinitionParser-with-request-preparers.xml"
        );

        final SimpleHttpClient simpleHttpClient = applicationContext.getBean(ROOT_BEAN_ID, SimpleHttpClient.class);

        final String baseUrl = on(simpleHttpClient).field("baseUrl").get().toString();
        assertThat(baseUrl).isEqualTo("http://www.example.com/with-request-parsers");

        final CompositeHttpRequestPreparer httpRequestPreparer = on(simpleHttpClient)
                                                                        .field("requestExecutor")     // HttpRequestExecutor
                                                                        .field("httpRequestPreparer") // CompositeHttpRequestPreparer
                                                                        .get();
        final List<HttpRequestPreparer> preparers = on(httpRequestPreparer)
                                                                        .field("preparers")           // List<HttpRequestPreparer>
                                                                        .get();
        assertThat(preparers).hasSize(4);
        assertThat(preparers.get(0)).isInstanceOf(AuthenticationHttpRequestPreparer.class);
        assertThat("preparer_1".equals(on(preparers.get(1)).call("getName").get()));
        assertThat("preparer_2".equals(on(preparers.get(1)).call("getName").get()));
        assertThat("preparer_3".equals(on(preparers.get(1)).call("getName").get()));
    }

    @Test
    public void placeholdersReplaced() throws Exception {
        final ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                "/META-INF/spring/test-garnish-rest-client-beanDefinitionParser-placeholdersReplaced.xml"
        );

        final SimpleHttpClient simpleHttpClient = applicationContext.getBean(ROOT_BEAN_ID, SimpleHttpClient.class);

        final String baseUrl = on(simpleHttpClient).field("baseUrl").get().toString();
        assertThat(baseUrl).isEqualTo("http://www.example.com/from-placeholder");

        final CompositeHttpRequestPreparer httpRequestPreparer = on(simpleHttpClient)
                                                                        .field("requestExecutor")     // HttpRequestExecutor
                                                                        .field("httpRequestPreparer") // CompositeHttpRequestPreparer
                                                                        .get();
        final List<HttpRequestPreparer> preparers = on(httpRequestPreparer)
                                                                        .field("preparers")           // List<HttpRequestPreparer>
                                                                        .get();
        assertThat(preparers).hasSize(2);
        assertThat(preparers.get(0)).isInstanceOf(AuthenticationHttpRequestPreparer.class);
        assertThat("preparer_1".equals(on(preparers.get(1)).call("getName").get()));
    }
}