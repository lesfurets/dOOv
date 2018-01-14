package io.doov.core.dsl.lang;

import io.doov.core.FieldId;
import io.doov.core.dsl.DslId;

public interface ConditionalBiMappingRule<I, J, O, S extends FieldId & DslId, T extends FieldId & DslId>
                extends BiMappingRule<I, J, O, S, T> {
    ValidationRule<S> validation();
}
