package io.doov.gen;

import static java.util.Collections.addAll;

import java.io.File;
import java.io.FileWriter;
import java.util.*;

import io.doov.core.FieldId;

class FieldCsvGen {

    public static void write(File output, Map<FieldId, VisitorPath> fieldPaths) {
        try (FileWriter writter = new FileWriter(output)) {
            writter.write("Path;Id;Type\n");
            for (VisitorPath path : fieldPaths.values()) {
                writter.write(toCsv(path));
            }
            for (FieldId field : fieldsWithoutPath(fieldPaths.values())) {
                writter.write("NO_PATH;");
                writter.write(field.toString());
                writter.write('\n');
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private static String toCsv(VisitorPath path) {
        return path.getBaseClass().getSimpleName().toLowerCase() + "." + path.displayPath() + ';'
                        + path.getFieldId() + ';'
                        + path.getGetMethod().getReturnType().getSimpleName() + '\n';
    }

    private static Collection<FieldId> fieldsWithoutPath(Collection<VisitorPath> collected) {
        final Set<FieldId> fields = new HashSet<>();
        collected.forEach(path -> addAll(fields, path.getFieldId().getClass().getEnumConstants()));
        collected.forEach(path -> fields.remove(path.getFieldId()));
        return fields;
    }
}
