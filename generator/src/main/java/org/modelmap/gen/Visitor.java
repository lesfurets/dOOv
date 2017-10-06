package org.modelmap.gen;

import static com.google.common.base.Strings.isNullOrEmpty;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.modelmap.core.FieldId;
import org.modelmap.core.PathConstraint;

final class Visitor {

    private final Class<?> baseClass;
    private final List<VisitorPath> collected;

    public Visitor(Class<?> baseClass, List<VisitorPath> collected) {
        this.baseClass = baseClass;
        this.collected = collected;
    }

    void visit(Map<FieldId, PathConstraint> fieldTarget, Method getMethod, Method setMethod, List<Method> paths) {
        fieldTarget.forEach((fieldId, constraint) -> {
            if (!checkFieldTargetConstraint(paths, fieldId, constraint)) {
                return;
            }
            final VisitorPath path = new VisitorPath(baseClass, paths, fieldId, getMethod, setMethod);
            if (contains(path)) {
                return;
            }
            collected.add(path);
        });
    }

    private static boolean checkFieldTargetConstraint(List<Method> paths, FieldId FieldId, PathConstraint constraint) {
        return isNullOrEmpty(constraint.includePath())
                        || VisitorPath.getterPath(paths, FieldId.position()).contains(constraint.includePath());
    }

    private boolean contains(VisitorPath vPath) {
        final String displayPath = vPath.toString();
        return collected.stream().anyMatch(path -> path.toString().equals(displayPath));
    }
}
