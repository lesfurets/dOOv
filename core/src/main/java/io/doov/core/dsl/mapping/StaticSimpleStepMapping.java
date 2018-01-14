/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.core.dsl.mapping;

import java.util.function.Supplier;

import io.doov.core.FieldId;
import io.doov.core.dsl.DslId;
import io.doov.core.dsl.field.BaseFieldInfo;
import io.doov.core.dsl.lang.*;
import io.doov.core.dsl.meta.MetadataVisitor;

public class StaticSimpleStepMapping<I, O, S extends FieldId & DslId> implements StepMapping<I, O, S> {

    private final Supplier<I> inputObject;
    private final StaticTypeConverter<I, O> typeConverter;

    public StaticSimpleStepMapping(Supplier<I> inputObject, StaticTypeConverter<I, O> typeConverter) {
        this.inputObject = inputObject;
        this.typeConverter = typeConverter;
    }

    @Override
    public <T extends FieldId & DslId> SimpleMappingRule<I, O, S, T> to(BaseFieldInfo<O, T> outFieldInfo) {
        return new StaticMappingRule<>(inputObject, outFieldInfo, typeConverter);
    }

    @Override
    public void accept(MetadataVisitor visitor, int depth) {
        // TODO
    }

    @Override
    public String readable() {
        return null;
    }
}
