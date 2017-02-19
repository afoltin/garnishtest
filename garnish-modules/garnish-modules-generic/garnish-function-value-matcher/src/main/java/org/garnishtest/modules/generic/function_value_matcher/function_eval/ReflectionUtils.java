package org.garnishtest.modules.generic.function_value_matcher.function_eval;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public final class ReflectionUtils {

    private ReflectionUtils() { }

    @Nullable
    public static Method getFirstMethodByName(@NonNull final Class<?> theClass,
                                               @NonNull final String methodName) {
        final Method[] declaredMethods = theClass.getDeclaredMethods();
        for (final Method declaredMethod : declaredMethods) {
            if (declaredMethod.getName().equals(methodName)) {
                return declaredMethod;
            }
        }

        return null;
    }

    @NonNull
    public static String getPackageName(@NonNull final Class<?> type) {
        final Package typePackage = type.getPackage();
        if (typePackage == null) {
            return "";
        }

        return typePackage.getName();
    }

    @NonNull
    public static ImmutableList<Method> findFunctionMethods(@NonNull final Class<?> functionObjectClass) {
        final Method[] declaredMethods = functionObjectClass.getDeclaredMethods();

        final List<Method> methods = new ArrayList<>();

        for (final Method method : declaredMethods) {
            if (isMatchFunction(method)) {
                methods.add(method);
            }
        }

        return ImmutableList.copyOf(methods);
    }

    private static boolean isMatchFunction(@NonNull final Method method) {
        return isPublic(method)
               && !isAbstract(method)
               && firstParameterIsOfTypeString(method);
    }

    private static boolean isPublic(@NonNull final Method method) {
        return Modifier.isPublic(
                method.getModifiers()
        );
    }

    private static boolean isAbstract(@NonNull final Method method) {
        return Modifier.isAbstract(
                method.getModifiers()
        );
    }

    private static boolean firstParameterIsOfTypeString(@NonNull final Method method) {
        final Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length == 0) {
            return false;
        }

        return parameterTypes[0] == String.class;
    }
}
