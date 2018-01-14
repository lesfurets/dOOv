package io.doov.core.dsl.mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import io.doov.core.FieldId;
import io.doov.core.dsl.DslId;
import io.doov.core.dsl.lang.MappingRegistry;
import io.doov.core.dsl.lang.MappingRule;

public class DefaultMappingRegistry<S extends FieldId & DslId, T extends FieldId & DslId>
                implements MappingRegistry<S, T> {

    private final List<MappingRule<S, T>> mappingRules;

    private DefaultMappingRegistry() {
        mappingRules = new ArrayList<>();
    }

    public static <S extends FieldId & DslId,
                    T extends FieldId & DslId> DefaultMappingRegistry<S, T> createMappingRegistry() {
        return new DefaultMappingRegistry<>();
    }

    @SafeVarargs
    public final MappingRegistry<S, T> mappings(MappingRule<S, T>... mappingRules) {
        for (MappingRule<S, T> mappingRule : mappingRules) {
            this.register(mappingRule);
        }
        return this;
    }

    @Override
    public void register(MappingRule<S, T> rule) {
        mappingRules.add(rule);
    }

    @Override
    public Stream<MappingRule<S, T>> stream() {
        return mappingRules.stream();
    }
}
