package io.doov.core.dsl.lang;

import io.doov.core.FieldId;
import io.doov.core.dsl.DslId;
import io.doov.core.dsl.field.BaseFieldInfo;
import io.doov.core.dsl.meta.SyntaxTree;

public interface StepMap<I, S extends FieldId & DslId> extends Readable, SyntaxTree {

    <O> StepMapping<I, O, S> using(TypeConverter<I, O, S> typeConverter);

    <T extends FieldId & DslId> SimpleMappingRule<I, I, S, T> to(BaseFieldInfo<I, T> outFieldInfo);
}
