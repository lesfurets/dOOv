package io.doov.core.dsl.lang;

import io.doov.core.FieldId;
import io.doov.core.dsl.DslId;

public interface ConditionalMappingRule<I, O, S extends FieldId & DslId, T extends FieldId & DslId>
                extends SimpleMappingRule<I, O, S, T> {
    ValidationRule<S> validation();
}
