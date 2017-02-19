package com.mobiquityinc.mobit.steps.mockhttpserver.spring;

import com.mobiquityinc.mobit.modules.generic.jaxb.JaxbParser;
import com.mobiquityinc.mobit.steps.mockhttpserver.server.HttpMockServerManager;
import com.mobiquityinc.mobit.steps.mockhttpserver.spring.model.HttpMockServerElement;
import lombok.NonNull;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public final class MockHttpServerBeanDefinitionParser implements BeanDefinitionParser {

    public static final String ROOT_ELEMENT_NAME = "mock-http-server";
    public static final String ROOT_BEAN_ID = "mobitStepsMockHttpServer_httpMocksManager";

    @NonNull private final JaxbParser<HttpMockServerElement> jaxbParser = new JaxbParser<>(HttpMockServerElement.class);

    @Override
    public BeanDefinition parse(final Element httpMockServerDomElement, final ParserContext parserContext) {
        final HttpMockServerElement httpMockServerElement = jaxbParser.parse(httpMockServerDomElement);

        final BeanDefinition httpMockServerManager = createHttpMockServerManager(httpMockServerElement);

        parserContext.getRegistry()
                     .registerBeanDefinition(ROOT_BEAN_ID, httpMockServerManager);

        return httpMockServerManager;
    }

    private BeanDefinition createHttpMockServerManager(@NonNull final HttpMockServerElement httpMockServerElement) {
        final BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(HttpMockServerManager.class);

        builder.setInitMethodName("startServer");
        builder.setDestroyMethodName("stopServer");

        builder.addConstructorArgValue(httpMockServerElement.port);

        return builder.getBeanDefinition();
    }
}
