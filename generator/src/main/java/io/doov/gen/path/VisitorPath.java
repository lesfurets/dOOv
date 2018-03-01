/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.gen.path;

import java.lang.reflect.Method;
import java.util.*;

import io.doov.core.FieldId;

public class VisitorPath {

    private final Class<?> baseClass;
    private final List<Method> path;
    private final FieldId fieldId;
    private final String readable;
    private final Method getMethod;
    private final Method setMethod;

    public VisitorPath(Class<?> baseClass, List<Method> getPath, FieldId fieldId, String readable,
                    Method getMethod, Method setMethod) {
        this.baseClass = baseClass;
        this.path = new ArrayList<>(getPath);
        this.fieldId = fieldId;
        this.readable = readable;
        this.getMethod = getMethod;
        this.setMethod = setMethod;
    }

    public Class<?> getBaseClass() {
        return baseClass;
    }

    public List<Method> getPath() {
        return path;
    }

    public FieldId getFieldId() {
        return fieldId;
    }

    public String getReadable() {
        return readable;
    }

    public Method getGetMethod() {
        return getMethod;
    }

    public Method getSetMethod() {
        return setMethod;
    }

    public boolean containsList() {
        return path.stream().anyMatch(p -> List.class.isAssignableFrom(p.getReturnType()));
    }

    public String displayPath() {
        return getterPath(path, fieldId.position());
    }

    @Override
    public String toString() {
        return baseClass.getSimpleName().toLowerCase() + "." + displayPath() + ":" + fieldId;
    }

    static String getterPath(List<Method> path) {
        return getterPath(path, -1);
    }

    static String getterPath(List<Method> path, int index) {
        final StringBuilder buffer = new StringBuilder();
        for (Method method : path) {
            if (List.class.isAssignableFrom(method.getReturnType()) && index >= 0) {
                buffer.append(method.getName());
                buffer.append("().get(");
                buffer.append(index - 1);
                buffer.append(")");
            } else {
                buffer.append(method.getName());
                buffer.append("()");
            }
            if (path.indexOf(method) < path.size() - 1) {
                buffer.append('.');
            }
        }
        return buffer.toString();
    }
}
