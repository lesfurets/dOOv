/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.core.dsl.lang;

import io.doov.core.FieldId;
import io.doov.core.dsl.DslId;
import io.doov.core.dsl.field.BaseFieldInfo;
import io.doov.core.dsl.meta.SyntaxTree;

public interface StaticStepMap<I, S extends FieldId & DslId> extends Readable, SyntaxTree {

    <O> StepMapping<I, O, S> using(StaticTypeConverter<I, O> typeConverter);

    <T extends FieldId & DslId> SimpleMappingRule<I, I, S, T> to(BaseFieldInfo<I, T> outFieldInfo);
}