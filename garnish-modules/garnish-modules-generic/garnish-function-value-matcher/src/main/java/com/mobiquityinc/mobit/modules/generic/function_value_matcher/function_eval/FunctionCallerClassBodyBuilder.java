package com.mobiquityinc.mobit.modules.generic.function_value_matcher.function_eval;

import lombok.NonNull;

import java.lang.reflect.Method;
import java.util.List;

public final class FunctionCallerClassBodyBuilder {

    private static final String PARAM_NAME_PREFIX = "p_";

    public String buildBodyWithoutTheMatchesMethod(@NonNull final List<?> functionObjects) {
        final StringBuilder result = new StringBuilder();

        result.append(
                "private String value;\n"
                + "private Object[] functionObjects;\n"
                + "\n"
                + "public void setValue(String value) {\n"
                + "    this.value = value;\n"
                + "}\n"
                + "\n"
                + "public void setFunctionObjects(Object[] functionObjects) {\n"
                + "    this.functionObjects = functionObjects;\n"
                + "}\n"
                + "\n"
        );

        for (int objectIndex = 0; objectIndex < functionObjects.size(); objectIndex++) {
            final Object object = functionObjects.get(objectIndex);
            final Class<?> objectClass = object.getClass();

            appendFunctionsForObject(result, objectIndex, objectClass);
        }

        return result.toString();
    }

    private void appendFunctionsForObject(@NonNull final StringBuilder result,
                                          final int objectIndex,
                                          @NonNull final Class<?> objectClass) {
        final List<Method> functionMethods = ReflectionUtils.findFunctionMethods(objectClass);
        if (functionMethods.isEmpty()) {
            throw new IllegalArgumentException(
                    "class [" + objectClass.getName() + "] does not contain any function methods"
            );
        }

        for (final Method functionMethod : functionMethods) {
            appendFunctionForObjectMethod(result, objectIndex, objectClass, functionMethod);
        }
    }

    private void appendFunctionForObjectMethod(@NonNull final StringBuilder result,
                                               final int objectIndex,
                                               @NonNull final Class<?> objectClass,
                                               @NonNull final Method objectMethod) {
        appendFunctionDeclaration(result, objectMethod);
        appendFunctionBody(result, objectClass, objectIndex, objectMethod);
        result.append("\n");
    }

    private void appendFunctionDeclaration(@NonNull final StringBuilder result,
                                           @NonNull final Method method) {
        final String returnType = method.getReturnType().getName();
        final String name = method.getName();

        result.append("private ").append(returnType).append(" ").append(name);
        result.append("(");
        appendMethodParametersDeclaration(result, method);
        result.append(") {\n");
    }

    private void appendMethodParametersDeclaration(@NonNull final StringBuilder result,
                                                   @NonNull final Method method) {
        final Class<?>[] parameterTypes = method.getParameterTypes();

        boolean first = true;
        // start from 1 to skip the first parameter (the "value" parameter)
        for (int paramIndex = 1; paramIndex < parameterTypes.length; paramIndex++) {
            final Class<?> parameterType = parameterTypes[paramIndex];

            if (first) {
                first = false;
            } else {
                result.append(", ");
            }

            result.append(parameterType.getName()).append(" ").append(PARAM_NAME_PREFIX).append(paramIndex);
        }
    }

    private void appendFunctionBody(@NonNull final StringBuilder result,
                                    @NonNull final Class<?> objectClass, final int objectIndex,
                                    @NonNull final Method objectMethod) {
        final String functionName = objectMethod.getName();

        result.append("    return ");

        // cast functionObjects[i] to correct type
        result.append("((").append(objectClass.getName()).append(")")
              .append(" ").append("functionObjects[").append(objectIndex).append("])");

        // method call
        result.append(".").append(functionName).append("(");

        // parameters; first parameter is always "value"
        result.append("value");

        final Class<?>[] parameterTypes = objectMethod.getParameterTypes();
        for (int i = 1; i < parameterTypes.length; i++) {
            result.append(", ");
            result.append(PARAM_NAME_PREFIX).append(i);
        }
        result.append(");\n");
        result.append("}\n");
    }

    public String buildMatchesMethod(@NonNull final String functionCall) {
        return "public boolean matches() {\n"
               + "    return " + functionCall + ";\n"
               + "}\n";
    }

}
