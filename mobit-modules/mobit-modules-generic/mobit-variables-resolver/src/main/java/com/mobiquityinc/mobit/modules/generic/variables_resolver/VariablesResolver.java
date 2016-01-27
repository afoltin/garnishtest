package com.mobiquityinc.mobit.modules.generic.variables_resolver;

import com.mobiquityinc.mobit.modules.generic.variables_resolver.exceptions.UnknownVariableException;
import com.mobiquityinc.mobit.modules.generic.variables_resolver.exceptions.UnterminatedVariableException;
import com.mobiquityinc.mobit.modules.generic.variables_resolver.impl.escape.ValueEscaper;
import lombok.NonNull;

import javax.annotation.Nonnull;

public interface VariablesResolver {

    @NonNull
    String resolveVariablesInText(@Nonnull  String textWithVariables,
                                  @Nonnull final ValueEscaper valueEscaper) throws UnknownVariableException,
                                                                                   UnterminatedVariableException;

}
