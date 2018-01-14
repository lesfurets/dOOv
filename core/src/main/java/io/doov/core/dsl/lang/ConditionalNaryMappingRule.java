package io.doov.core.dsl.lang;

import io.doov.core.FieldId;
import io.doov.core.dsl.DslId;

public interface ConditionalNaryMappingRule<O, S extends FieldId & DslId, T extends FieldId & DslId>
                extends NaryMappingRule<O, S, T> {
    ValidationRule<S> validation();
}
