package com.mobiquityinc.mobit.steps.restclient.spring.model;


import lombok.ToString;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "request-preparer")
@ToString
public final class RequestPreparerElement {

    @XmlAttribute(name = "ref")
    public String ref;

}
