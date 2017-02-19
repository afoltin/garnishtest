package org.garnishtest.steps.restclient.spring.model;


import lombok.ToString;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "rest-client")
@ToString
public final class RestClientElement {

    @XmlAttribute(name = "baseUrl")
    public String baseUrl;

    @XmlAttribute(name = "connectionTimeoutMillis")
    public String connectionTimeoutMillis;

    @XmlAttribute(name = "socketTimeoutMillis")
    public String socketTimeoutMillis;

    @XmlAttribute(name = "maxConnections")
    public String maxConnections;

    @XmlAttribute(name = "authenticationProviderRef")
    public String authenticationProviderRef;

    @XmlElementWrapper(name="request-preparers")
    @XmlElement(name = "request-preparer")
    public List<RequestPreparerElement> requestPreparerElements = new ArrayList<>();

}
