package org.garnishtest.steps.restclient.spring;

import org.garnishtest.modules.generic.httpclient.SimpleHttpClient;
import org.garnishtest.modules.generic.httpclient.request_preparer.HttpRequestPreparer;
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

        final List<HttpRequestPreparer> preparers = on(simpleHttpClient).field("requestExecutor")     // HttpRequestExecutor
                                                                        .field("httpRequestPreparer") // CompositeHttpRequestPreparer
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

        final List<TestRequestPreparer> preparers = on(simpleHttpClient).field("requestExecutor")     // HttpRequestExecutor
                                                                        .field("httpRequestPreparer") // CompositeHttpRequestPreparer
                                                                        .field("preparers")           // List<HttpRequestPreparer>
                                                                        .get();
        assertThat(preparers).hasSize(4);
        assertThat(preparers.get(0)).isInstanceOf(AuthenticationHttpRequestPreparer.class);
        assertThat(preparers.get(1).getName()).isEqualTo("preparer_1");
        assertThat(preparers.get(2).getName()).isEqualTo("preparer_2");
        assertThat(preparers.get(3).getName()).isEqualTo("preparer_3");
    }

    @Test
    public void placeholdersReplaced() throws Exception {
        final ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                "/META-INF/spring/test-garnish-rest-client-beanDefinitionParser-placeholdersReplaced.xml"
        );

        final SimpleHttpClient simpleHttpClient = applicationContext.getBean(ROOT_BEAN_ID, SimpleHttpClient.class);

        final String baseUrl = on(simpleHttpClient).field("baseUrl").get().toString();
        assertThat(baseUrl).isEqualTo("http://www.example.com/from-placeholder");

        final List<TestRequestPreparer> preparers = on(simpleHttpClient).field("requestExecutor")     // HttpRequestExecutor
                                                                        .field("httpRequestPreparer") // CompositeHttpRequestPreparer
                                                                        .field("preparers")           // List<HttpRequestPreparer>
                                                                        .get();
        assertThat(preparers).hasSize(2);
        assertThat(preparers.get(0)).isInstanceOf(AuthenticationHttpRequestPreparer.class);
        assertThat(preparers.get(1).getName()).isEqualTo("preparer_1");
    }
}