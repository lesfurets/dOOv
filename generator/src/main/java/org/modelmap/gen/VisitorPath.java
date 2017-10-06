package org.modelmap.gen;

import java.lang.reflect.Method;
import java.util.*;

import org.modelmap.core.FieldId;

final class VisitorPath {

    private final Class<?> baseClass;
    private final List<Method> path;
    private final FieldId fieldId;
    private final Method getMethod;
    private final Method setMethod;

    public VisitorPath(Class<?> baseClass, List<Method> getPath, FieldId fieldId, Method getMethod, Method setMethod) {
        this.baseClass = baseClass;
        this.path = new ArrayList<>(getPath);
        this.fieldId = fieldId;
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

    public Method getGetMethod() {
        return getMethod;
    }

    public Method getSetMethod() {
        return setMethod;
    }

    public boolean containsList() {
        return path.stream().anyMatch(p -> List.class.isAssignableFrom(p.getReturnType()));
    }

    public boolean containsGenerics() {
        return path.stream().anyMatch(p -> p.getGenericReturnType().toString().contains("<"));
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

    static Map<FieldId, List<VisitorPath>> pathByFieldId(List<VisitorPath> paths) {
        final Map<FieldId, List<VisitorPath>> textPaths = new HashMap<>();
        paths.forEach(path -> textPaths.computeIfAbsent(path.getFieldId(), f -> new ArrayList<>()).add(path));
        // remove duplicate paths
        textPaths.forEach((fieldId, visitorPaths) -> {
            Map<String, VisitorPath> pathMap = new HashMap<>();
            visitorPaths.forEach(p -> pathMap.put(p.toString(), p));
            textPaths.put(fieldId, new ArrayList<>(pathMap.values()));
        });
        // sort paths
        textPaths.values().forEach(mappedPaths -> mappedPaths.sort((o1, o2) -> o1.toString().compareTo(o2.toString())));
        return textPaths;
    }

}
