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

package org.garnishtest.modules.generic.jaxb;

import lombok.NonNull;
import org.w3c.dom.Node;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Source;

public final class JaxbParser<T> {

    @NonNull private final Class<T> classParse;
    @NonNull private final Unmarshaller unmarshaller;

    public JaxbParser(@NonNull final Class<T> classParse) {
        this.classParse = classParse;
        this.unmarshaller = createUnMarshaller(
                createJaxbContext(classParse),
                classParse
        );
    }

    public T parse(@NonNull final Node node) {
        try {
            final JAXBElement<T> jaxbElement = unmarshaller.unmarshal(node, classParse);

            return jaxbElement.getValue();
        } catch (final Exception e) {
            throw new JaxbParserException("failed to parse" , e);
        }
    }

    public T parse(@NonNull final Source source) {
        try {
            final JAXBElement<T> jaxbElement = unmarshaller.unmarshal(source, classParse);

            return jaxbElement.getValue();
        } catch (final Exception e) {
            throw new JaxbParserException("failed to parse" , e);
        }
    }

    public T parse(@NonNull final XMLStreamReader reader) {
        try {
            final JAXBElement<T> jaxbElement = unmarshaller.unmarshal(reader, classParse);

            return jaxbElement.getValue();
        } catch (final Exception e) {
            throw new JaxbParserException("failed to parse" , e);
        }
    }

    public T parse(@NonNull final XMLEventReader reader) {
        try {
            final JAXBElement<T> jaxbElement = unmarshaller.unmarshal(reader, classParse);

            return jaxbElement.getValue();
        } catch (final Exception e) {
            throw new JaxbParserException("failed to parse" , e);
        }
    }

    private Unmarshaller createUnMarshaller(@NonNull final JAXBContext jaxbContext,
                                            @NonNull final Class<T> classParse) {
        try {
            return jaxbContext.createUnmarshaller();
        } catch (final Exception e) {
            throw new JaxbParserException("failed to create JAXB unmarshaller for class [" + classParse + "]", e);
        }
    }

    private JAXBContext createJaxbContext(final @NonNull Class<T> classParse) {
        try {
            return JAXBContext.newInstance(classParse);
        } catch (final Exception e) {
            throw new JaxbParserException("failed to create JAXB context for class [" + classParse + "]", e);
        }
    }
}
