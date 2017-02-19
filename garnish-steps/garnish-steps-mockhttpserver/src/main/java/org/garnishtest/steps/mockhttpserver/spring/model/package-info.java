@XmlSchema(
        namespace = "http://www.garnish-test.org/schema/steps/mockhttpserver",
        attributeFormDefault = XmlNsForm.UNQUALIFIED,
        elementFormDefault = XmlNsForm.QUALIFIED,
        xmlns = {
                @XmlNs(prefix = "mhs", namespaceURI = "http://www.garnish-test.org/schema/steps/mockhttpserver")
        }
)
@XmlAccessorType(XmlAccessType.NONE)
package org.garnishtest.steps.mockhttpserver.spring.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlNs;
import javax.xml.bind.annotation.XmlNsForm;
import javax.xml.bind.annotation.XmlSchema;