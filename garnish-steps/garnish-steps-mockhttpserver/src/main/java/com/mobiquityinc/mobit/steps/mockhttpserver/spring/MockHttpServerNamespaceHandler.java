package com.mobiquityinc.mobit.steps.mockhttpserver.spring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public final class MockHttpServerNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser(MockHttpServerBeanDefinitionParser.ROOT_ELEMENT_NAME, new MockHttpServerBeanDefinitionParser());
    }

}
