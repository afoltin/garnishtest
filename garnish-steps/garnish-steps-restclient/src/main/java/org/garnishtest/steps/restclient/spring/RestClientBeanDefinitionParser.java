package org.garnishtest.steps.restclient.spring;

import com.google.common.collect.ImmutableList;
import org.garnishtest.modules.generic.httpclient.RestApiClientFactory;
import org.garnishtest.modules.generic.httpclient.SimpleHttpClient;
import org.garnishtest.modules.generic.httpclient.executor.HttpRequestExecutor;
import org.garnishtest.modules.generic.httpclient.request_preparer.impl.CompositeHttpRequestPreparer;
import org.garnishtest.modules.generic.jaxb.JaxbParser;
import org.garnishtest.steps.restclient.auth.preparer.AuthenticationHttpRequestPreparer;
import org.garnishtest.steps.restclient.auth.provider.impl.NoOpRestClientAuthenticationProvider;
import org.garnishtest.steps.restclient.spring.model.RequestPreparerElement;
import org.garnishtest.steps.restclient.spring.model.RestClientElement;
import lombok.NonNull;
import org.springframework.beans.BeanMetadataElement;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public final class RestClientBeanDefinitionParser implements BeanDefinitionParser {

    public static final String ROOT_ELEMENT_NAME = "rest-client";

    public static final String ROOT_BEAN_ID = "garnishStepsRestClient_httpClient";
    public static final String AUTHENTICATION_HTTP_REQUEST_PREPARER_BEAN_ID = "garnishStepsRestClient_authenticationHttpRequestPreparer";

    @NonNull private final JaxbParser<RestClientElement> jaxbParser = new JaxbParser<>(RestClientElement.class);

    @Override
    public BeanDefinition parse(final Element restClientDomElement, final ParserContext parserContext) {
        final RestClientElement restClientElement = jaxbParser.parse(restClientDomElement);

        final BeanDefinition simpleHttpClient = createSimpleHttpClient(restClientElement, parserContext);

        parserContext.getRegistry()
                     .registerBeanDefinition(ROOT_BEAN_ID, simpleHttpClient);

        return simpleHttpClient;
    }

    private BeanDefinition createSimpleHttpClient(@NonNull final RestClientElement restClientElement,
                                                  @NonNull final ParserContext parserContext) {
        final BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(SimpleHttpClient.class);

        builder.addConstructorArgValue(
                restClientElement.baseUrl
        );
        builder.addConstructorArgValue(
                createHttpRequestExecutor(restClientElement, parserContext)
        );

        return builder.getBeanDefinition();
    }

    private BeanDefinition createHttpRequestExecutor(@NonNull final RestClientElement restClientElement,
                                                     @NonNull final ParserContext parserContext) {
        final BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(HttpRequestExecutor.class);

        builder.addConstructorArgValue(
                createHttpClient(restClientElement)
        );
        builder.addConstructorArgValue(
                createCompositeHttpRequestPreparer(restClientElement, parserContext)
        );

        return builder.getBeanDefinition();
    }

    private BeanDefinition createHttpClient(@NonNull final RestClientElement restClientElement) {
        final BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(RestApiClientFactory.class);

        if (restClientElement.maxConnections != null) {
            builder.addPropertyValue("maxConnectionsPerRoute", restClientElement.maxConnections);
            builder.addPropertyValue("maxTotalConnections", restClientElement.maxConnections);
        }

        if (restClientElement.connectionTimeoutMillis != null) {
            builder.addPropertyValue("connectionTimeoutMillis", restClientElement.connectionTimeoutMillis);
        }

        if (restClientElement.socketTimeoutMillis != null) {
            builder.addPropertyValue("socketTimeoutMillis", restClientElement.socketTimeoutMillis);
        }

        return builder.getBeanDefinition();
    }

    private BeanDefinition createCompositeHttpRequestPreparer(@NonNull final RestClientElement restClientElement,
                                                              @NonNull final ParserContext parserContext) {
        final BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(CompositeHttpRequestPreparer.class);

        builder.addConstructorArgValue(
                createListOfHttpRequestPreparerReferences(restClientElement, parserContext)
        );

        return builder.getBeanDefinition();
    }

    private ManagedList<BeanMetadataElement> createListOfHttpRequestPreparerReferences(@NonNull final RestClientElement restClientElement,
                                                                                       @NonNull final ParserContext parserContext) {
        final ManagedList<BeanMetadataElement> list = new ManagedList<>();

        // authentication provider
        registerAuthenticationHttpRequestPreparer(restClientElement, parserContext);
        list.add(
                new RuntimeBeanReference(AUTHENTICATION_HTTP_REQUEST_PREPARER_BEAN_ID)
        );

        // "request-preparer"s
        final ImmutableList<BeanMetadataElement> httpRequestPreparerBeanReferences = createHttpRequestPreparerBeanReferences(restClientElement);
        list.addAll(httpRequestPreparerBeanReferences);

        return list;
    }

    private void registerAuthenticationHttpRequestPreparer(@NonNull final RestClientElement restClientElement,
                                                           @NonNull final ParserContext parserContext) {
        final BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(AuthenticationHttpRequestPreparer.class);

        final String authenticationProviderRefAttribute = restClientElement.authenticationProviderRef;
        if (authenticationProviderRefAttribute == null) {
            builder.addConstructorArgValue(new NoOpRestClientAuthenticationProvider());
        } else {
            builder.addConstructorArgReference(authenticationProviderRefAttribute);
        }

        parserContext.getRegistry().registerBeanDefinition(AUTHENTICATION_HTTP_REQUEST_PREPARER_BEAN_ID, builder.getBeanDefinition());
    }

    @NonNull
    private ImmutableList<BeanMetadataElement> createHttpRequestPreparerBeanReferences(@NonNull final RestClientElement restClientElement) {
        final ManagedList<BeanMetadataElement> list = new ManagedList<>();

        final ImmutableList<String> httpRequestPreparerBeanReferences = parseHttpRequestPreparerBeanReferences(restClientElement);

        for (final String beanReference : httpRequestPreparerBeanReferences) {
            final RuntimeBeanReference runtimeBeanReference = new RuntimeBeanReference(beanReference);

            list.add(runtimeBeanReference);
        }

        return ImmutableList.copyOf(list);
    }

    @NonNull
    private ImmutableList<String> parseHttpRequestPreparerBeanReferences(@NonNull final RestClientElement restClientElement) {
        final ImmutableList.Builder<String> beanNameReferences = ImmutableList.builder();
        for (final RequestPreparerElement requestPreparerElement : restClientElement.requestPreparerElements) {
            beanNameReferences.add(
                    requestPreparerElement.ref
            );
        }

        return beanNameReferences.build();
    }
}
