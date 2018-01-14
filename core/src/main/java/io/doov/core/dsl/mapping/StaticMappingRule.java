/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.core.dsl.mapping;

import java.util.function.Supplier;

import io.doov.core.FieldId;
import io.doov.core.dsl.DslId;
import io.doov.core.dsl.DslModel;
import io.doov.core.dsl.field.BaseFieldInfo;
import io.doov.core.dsl.lang.*;
import io.doov.core.dsl.meta.MetadataVisitor;

public class StaticMappingRule<I, O, S extends FieldId & DslId, T extends FieldId & DslId>
                implements SimpleMappingRule<I, O, S, T> {

    private final Supplier<I> inputObject;
    private final BaseFieldInfo<O, T> outFieldInfo;
    private final StaticTypeConverter<I, O> typeConverter;

    public StaticMappingRule(Supplier<I> inputObject,
                    BaseFieldInfo<O, T> outFieldInfo,
                    StaticTypeConverter<I, O> typeConverter) {
        this.inputObject = inputObject;
        this.outFieldInfo = outFieldInfo;
        this.typeConverter = typeConverter;
    }

    @Override
    public void executeOn(DslModel<S> inModel, DslModel<T> outModel) {
        outModel.set(outFieldInfo.id(), typeConverter.convert(inputObject.get()));
    }

    @Override
    public SimpleMappingRule<I, O, S, T> registerOn(MappingRegistry<S, T> registry) {
        registry.register(this);
        return this;
    }

    @Override
    public void accept(MetadataVisitor visitor, int depth) {

    }

    @Override
    public String readable() {
        return null;
    }

}
