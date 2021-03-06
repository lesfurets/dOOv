/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.core.dsl.mapping;

import static io.doov.core.dsl.meta.MappingMetadata.mapping;
import static io.doov.core.dsl.meta.ast.AstVisitorUtils.astToString;

import java.util.Locale;
import java.util.function.Supplier;

import io.doov.core.FieldModel;
import io.doov.core.dsl.DslField;
import io.doov.core.dsl.lang.MappingRule;
import io.doov.core.dsl.lang.StaticTypeConverter;
import io.doov.core.dsl.meta.MappingMetadata;
import io.doov.core.dsl.meta.MetadataVisitor;

public class StaticMappingRule<I, O> implements MappingRule {

    private final Supplier<I> inputObject;
    private final DslField<O> outFieldInfo;
    private final StaticTypeConverter<I, O> typeConverter;
    private final MappingMetadata metadata;

    StaticMappingRule(Supplier<I> inputObject, DslField<O> outFieldInfo, StaticTypeConverter<I, O> typeConverter) {
        this.inputObject = inputObject;
        this.metadata = mapping(inputObject, outFieldInfo);
        this.outFieldInfo = outFieldInfo;
        this.typeConverter = typeConverter;
    }

    @Override
    public boolean validate(FieldModel inModel, FieldModel outModel) {
        return outModel.getFieldInfos().stream().anyMatch(f -> f.id().equals(outFieldInfo.id()));
    }

    @Override
    public void executeOn(FieldModel inModel, FieldModel outModel) {
        outModel.set(outFieldInfo.id(), typeConverter.convert(inputObject.get()));
    }

    @Override
    public void accept(MetadataVisitor visitor, int depth) {
        metadata.accept(visitor, depth);
        typeConverter.accept(visitor, depth);
    }

    @Override
    public String readable(Locale locale) {
        return astToString(this, locale);
    }

}
