/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.gen.path;

import static java.util.stream.Collectors.joining;

import java.beans.Introspector;
import java.lang.reflect.Method;
import java.util.*;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.CaseFormat;

public class Visitor {

    private final Class<?> baseClass;
    private final List<VisitorPath> collected;

    Visitor(Class<?> baseClass, List<VisitorPath> collected) {
        this.baseClass = baseClass;
        this.collected = collected;
    }

    void visit(Method getMethod, Method setMethod, List<Method> paths) {
        VisitorFieldId fieldId = new VisitorFieldId(toFieldCode(paths), 0);
        final VisitorPath path = new VisitorPath(baseClass, paths, fieldId, toReadable(paths),
                        getMethod, setMethod);
        if (contains(path)) {
            return;
        }
        collected.add(path);
    }

    private String toReadable(List<Method> paths) {
        return paths.stream().map(this::toFieldName).collect(joining(" "));
    }

    private String toFieldCode(List<Method> paths) {
        return paths.stream().map(this::toFieldIdPart).collect(joining("_"));
    }

    private String toFieldName(Method name) {
        String fieldName = stripPropertyPrefix(name);
        if (fieldName.length() >= 4) {
            return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fieldName).replace("_", " ");
        } else {
            return fieldName.toLowerCase();
        }
    }

    private String toFieldIdPart(Method name) {
        String fieldName = stripPropertyPrefix(name);
        if (fieldName.length() >= 4) {
            return CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, fieldName);
        } else {
            return fieldName.toUpperCase();
        }
    }

    private String stripPropertyPrefix(Method name) {
        return name.getName().substring(name.getName().startsWith("is") ? 2 : 3);
    }

    private boolean contains(VisitorPath vPath) {
        final String displayPath = vPath.toString();
        return collected.stream().anyMatch(path -> path.toString().equals(displayPath));
    }

}
