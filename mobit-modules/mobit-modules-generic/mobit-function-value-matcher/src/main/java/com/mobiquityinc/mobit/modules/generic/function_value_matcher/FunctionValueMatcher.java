package com.mobiquityinc.mobit.modules.generic.function_value_matcher;

import com.google.common.collect.ImmutableList;
import com.mobiquityinc.mobit.modules.generic.function_value_matcher.function_eval.FunctionCallerClassBodyBuilder;
import com.mobiquityinc.mobit.modules.generic.function_value_matcher.function_eval.ValueMatcher;
import lombok.NonNull;
import org.codehaus.commons.compiler.CompileException;
import org.codehaus.commons.compiler.CompilerFactoryFactory;
import org.codehaus.commons.compiler.IClassBodyEvaluator;

import javax.annotation.Nullable;
import java.util.List;

public final class FunctionValueMatcher {

    @NonNull private final FunctionCallerClassBodyBuilder builder;

    @NonNull private final ImmutableList<?> functionObjects;
    @NonNull private final String bodyWithoutTheMatchesMethod;

    public FunctionValueMatcher(@NonNull final List<?> functionObjects) {
        this.builder = new FunctionCallerClassBodyBuilder();

        this.functionObjects = ImmutableList.copyOf(functionObjects);
        this.bodyWithoutTheMatchesMethod = this.builder.buildBodyWithoutTheMatchesMethod(functionObjects);
    }

    public boolean matches(@Nullable final String value,
                           @NonNull final String functionCall) throws FunctionCallerException {
        try {
            return tryToCheckIfMatches(value, functionCall);
        } catch (final Exception e) {
            throw new FunctionCallerException("failed to check if [" + functionCall + "] matches [" + value + "]", e);
        }
    }

    // can't fix "squid:S00112" - "newClassBodyEvaluator()" throws Exception
    @SuppressWarnings({"squid:S00112", "ProhibitedExceptionDeclared"})
    private boolean tryToCheckIfMatches(@Nullable final String actualValue,
                                        @NonNull final String functionCall) throws Exception {
        final ValueMatcher valueMatcher = createValueMatcher(functionCall);

        // todo: cache this array?
        valueMatcher.setFunctionObjects(
                this.functionObjects.toArray(new Object[this.functionObjects.size()])
        );

        valueMatcher.setValue(actualValue);

        return valueMatcher.matches();
    }

    // can't fix "squid:S00112" - "newClassBodyEvaluator()" throws Exception
    @SuppressWarnings({"squid:S00112", "ProhibitedExceptionDeclared"})
    private ValueMatcher createValueMatcher(@NonNull final String functionCall) throws Exception {
        final IClassBodyEvaluator classBodyEvaluator = CompilerFactoryFactory.getDefaultCompilerFactory()
                                                                             .newClassBodyEvaluator();
        classBodyEvaluator.setImplementedInterfaces(new Class<?>[]{ValueMatcher.class});

        final String matchesMethod = this.builder.buildMatchesMethod(functionCall);

        final String classBody = this.bodyWithoutTheMatchesMethod + "\n" + matchesMethod;
        try {
            classBodyEvaluator.cook(classBody);
        } catch (final CompileException e) {
            // todo: only show the function call and column number as the first exception, with the original as nested
            throw new FunctionCallerException("failed to compile [\n" + printWithLineNumbers(classBody) + "\n]", e);
        }

        return (ValueMatcher) classBodyEvaluator.getClazz().newInstance();
    }

    // todo: move to utility class
    @Nullable
    private String printWithLineNumbers(@Nullable final String text) {
        if (text == null) {
            return null;
        }

        final StringBuilder result = new StringBuilder();
        final String[] lines = text.split("\n");

        final long lineNumberDigitsCount = numberOfDigits(lines.length) + 1;

        for (int i = 0; i < lines.length; i++) {
            final String line = lines[i];


            // todo: use space instead of -
            result.append(
                    String.format("%" + lineNumberDigitsCount + "d", i + 1)
            );
            result.append("   ");
            result.append(line);
            result.append('\n');
        }

        return result.toString();
    }

    private long numberOfDigits(final int number) {
        return Math.round(
                Math.log10(number)
        );
    }

}
