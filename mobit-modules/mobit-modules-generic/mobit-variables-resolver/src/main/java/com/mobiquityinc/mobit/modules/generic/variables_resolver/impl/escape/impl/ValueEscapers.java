package com.mobiquityinc.mobit.modules.generic.variables_resolver.impl.escape.impl;

import com.mobiquityinc.mobit.modules.generic.variables_resolver.impl.escape.ValueEscaper;

public final class ValueEscapers {

    private static final ValueEscaper NONE = new NoValueEscaper();
    private static final ValueEscaper XML = new XmlValueEscaper();
    private static final ValueEscaper JSON = new JsonValueEscaper();

    private ValueEscapers() { }

    public static ValueEscaper none() {
        return NONE;
    }

    public static ValueEscaper xml() {
        return XML;
    }

    public static ValueEscaper json() {
        return JSON;
    }

}
