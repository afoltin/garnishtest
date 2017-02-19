@XmlSchema(
        namespace = "http://www.garnish-test.org/schema/steps/restclient",
        attributeFormDefault = XmlNsForm.UNQUALIFIED,
        elementFormDefault = XmlNsForm.QUALIFIED,
        xmlns = {
                @XmlNs(prefix = "mrc", namespaceURI = "http://www.garnish-test.org/schema/steps/restclient")
        }
)
@XmlAccessorType(XmlAccessType.NONE)
package org.garnishtest.steps.restclient.spring.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlNs;
import javax.xml.bind.annotation.XmlNsForm;
import javax.xml.bind.annotation.XmlSchema;