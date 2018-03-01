/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.gen.path;

import static java.util.Arrays.stream;
import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toList;

import java.beans.*;
import java.lang.reflect.*;
import java.util.*;

import org.apache.maven.plugin.logging.Log;

public class ModelVisitor {

    private final Log log;

    public ModelVisitor(Log log) {
        this.log = log;
    }

    public void visitModel(Class<?> clazz, Visitor visitor, String packageFilter) throws IntrospectionException {
        log.debug("starting visiting class " + clazz.getName());
        visitModel(clazz, visitor, new LinkedList<>(), packageFilter, 0);
    }


    private void visitModel(Class<?> clazz, Visitor visitor, LinkedList<Method> path, String packageFilter, int deep)
                    throws IntrospectionException {
        if (clazz == null) {
            return;
        }
        if (clazz.getPackage() == null) {
            return;
        }
        if (!clazz.getPackage().getName().startsWith(packageFilter)) {
            return;
        }
        if (clazz.isEnum()) {
            return;
        }
        if (deep > 8) {
            return;
        }

        log.debug("class " + clazz.getName());
        final BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
        final PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor desc : propertyDescriptors) {
            log.debug("property " + desc.getName() + " : "
                            + (desc.getPropertyType() != null ? desc.getPropertyType().getSimpleName() : null)
                            + " from " + clazz.getName());
            if (desc.getPropertyType() == Class.class) {
                continue;
            }
            path.addLast(desc.getReadMethod());
            try {
                visitor.visit(desc.getReadMethod(), desc.getWriteMethod(), path);
            } finally {
                path.removeLast();
            }
        }
        final Collection<Method> otherMethods = methods(clazz, packageFilter);
        for (Method method : otherMethods) {
            path.addLast(method);
            try {
                visitModel(returnType(method, packageFilter), visitor, path, packageFilter, deep + 1);
            } finally {
                path.removeLast();
            }
        }
        visitModel(clazz.getSuperclass(), visitor, path, packageFilter, deep + 1);
    }

    private Collection<Method> methods(Class<?> clazz, String packageFilter) {
        if (clazz == null) {
            return emptySet();
        }
        if (clazz.getPackage() == null) {
            return emptySet();
        }
        if (!clazz.getPackage().getName().startsWith(packageFilter)) {
            return emptySet();
        }
        return filter(clazz.getMethods(), packageFilter);
    }

    private Collection<Method> filter(Method[] methods, String packageFilter) {
        final List<Method> filtered = stream(methods)
                        .filter(m -> !Modifier.isStatic(m.getModifiers()))
                        .filter(m -> !Modifier.isNative(m.getModifiers()))
                        .filter(m -> Modifier.isPublic(m.getModifiers()))
                        .filter(m -> m.getReturnType() != null)
                        .filter(m -> !m.getReturnType().equals(Void.TYPE))
                        .filter(m -> m.getParameterTypes().length == 0)
                        .filter(m -> m.getDeclaringClass().getPackage().getName().startsWith(packageFilter))
                        .filter(m -> !m.getName().toLowerCase().contains("clone"))
                        .collect(toList());

        final List<Method> overrided = new ArrayList<>();
        for (Method method : filtered) {
            for (Method method2 : filtered) {
                if (method == method2) {
                    continue;
                }
                if (!method.getName().equals(method2.getName())) {
                    continue;
                }
                if (method.getReturnType().isAssignableFrom(method2.getReturnType())) {
                    overrided.add(method);
                }
            }
        }
        filtered.removeAll(overrided);
        return filtered;
    }

    private Class<?> returnType(Method method, String packageFilter) {
        if (method.getGenericReturnType() == null) {
            return method.getReturnType();
        }
        if (method.getGenericReturnType().getTypeName().equals(method.getReturnType().getTypeName())) {
            return method.getReturnType();
        }
        if (method.getGenericReturnType() instanceof Class) {
            return (Class<?>) method.getGenericReturnType();
        }
        if (List.class.isAssignableFrom(method.getReturnType())
                        && method.getGenericReturnType() instanceof ParameterizedType) {
            final ParameterizedType returnType = (ParameterizedType) method.getGenericReturnType();
            if (returnType == null) {
                return null;
            }
            if (returnType.getActualTypeArguments() == null) {
                return null;
            }
            if (returnType.getActualTypeArguments().length != 1) {
                return null;
            }
            if (returnType.getActualTypeArguments()[0].toString().startsWith(packageFilter)) {
                return null;
            }
            return (Class<?>) returnType.getActualTypeArguments()[0];
        }
        return method.getReturnType();
    }
}
