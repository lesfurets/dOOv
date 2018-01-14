package io.doov.core.dsl.lang;

import io.doov.core.FieldId;
import io.doov.core.dsl.DslId;
import io.doov.core.dsl.field.BaseFieldInfo;
import io.doov.core.dsl.meta.SyntaxTree;

public interface StepMapping<I, O, S extends FieldId & DslId> extends Readable, SyntaxTree {

     <T extends FieldId & DslId> SimpleMappingRule<I, O, S, T> to(BaseFieldInfo<O, T> outFieldInfo);
}
