package com.mobiquityinc.mobit.steps.mockhttpserver.spring.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "mock-http-server")
public final class HttpMockServerElement {

    @XmlAttribute(name = "port")
    public String port;

}
