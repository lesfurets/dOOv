package io.doov.core.dsl.lang;

import io.doov.core.FieldId;
import io.doov.core.dsl.DslId;
import io.doov.core.dsl.meta.SyntaxTree;

public interface NaryStepMap<S extends FieldId & DslId> extends Readable, SyntaxTree {

    <O> NaryStepMapping<O, S> using(GenericTypeConverter<O, S> typeConverter);
}
