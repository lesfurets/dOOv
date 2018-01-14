package io.doov.core.dsl.lang;

import io.doov.core.FieldId;
import io.doov.core.dsl.DslId;
import io.doov.core.dsl.meta.SyntaxTree;

public interface BiStepMap<I, J, S extends FieldId & DslId> extends Readable, SyntaxTree {

    <O> BiStepMapping<I, J, O, S> using(BiTypeConverter<I, J, O, S> typeConverter);

}
