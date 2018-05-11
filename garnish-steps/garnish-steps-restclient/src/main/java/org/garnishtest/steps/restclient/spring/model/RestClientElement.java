/*
 * Copyright 2016-2018, Garnish.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

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
