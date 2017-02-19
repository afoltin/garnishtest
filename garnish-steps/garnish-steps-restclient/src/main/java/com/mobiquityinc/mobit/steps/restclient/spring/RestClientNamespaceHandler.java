package com.mobiquityinc.mobit.steps.restclient.spring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public final class RestClientNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser(RestClientBeanDefinitionParser.ROOT_ELEMENT_NAME, new RestClientBeanDefinitionParser());
    }

}
