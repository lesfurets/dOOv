package io.doov.core.dsl.lang;

import java.util.stream.Stream;

import io.doov.core.FieldId;
import io.doov.core.dsl.DslId;

public interface MappingRegistry<S extends FieldId & DslId, T extends FieldId & DslId> {

    void register(MappingRule<S, T> rule);

    Stream<MappingRule<S, T>> stream();
}
