package io.doov.core.dsl.lang;

import java.util.List;

import io.doov.core.FieldId;
import io.doov.core.dsl.DslId;
import io.doov.core.dsl.DslModel;
import io.doov.core.dsl.field.BaseFieldInfo;
import io.doov.core.dsl.meta.SyntaxTree;

public interface GenericTypeConverter<O, S extends FieldId & DslId> extends Readable, SyntaxTree {

    O convert(DslModel<S> fieldModel, List<BaseFieldInfo> ins);
}
