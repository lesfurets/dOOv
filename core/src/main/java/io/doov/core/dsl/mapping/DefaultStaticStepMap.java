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

public class DefaultStaticStepMap<I, S extends FieldId & DslId> implements StaticStepMap<I, S> {

    private final Supplier<I> inputObject;

    public DefaultStaticStepMap(Supplier<I> input) {
        this.inputObject = input;
    }

    @Override
    public <T extends FieldId & DslId> SimpleMappingRule<I, I, S, T> to(BaseFieldInfo<I, T> outFieldInfo) {
        return new StaticMappingRule<>(inputObject, outFieldInfo, DefaultStaticTypeConverter.identity());
    }

    @Override
    public <O> StepMapping<I, O, S> using(StaticTypeConverter<I, O> typeConverter) {
        return new StaticSimpleStepMapping<>(inputObject, typeConverter);
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
