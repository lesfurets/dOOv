package io.doov.core.dsl.mapping;

import io.doov.core.FieldId;
import io.doov.core.dsl.DslId;
import io.doov.core.dsl.DslModel;
import io.doov.core.dsl.field.BaseFieldInfo;
import io.doov.core.dsl.lang.TypeConverter;
import io.doov.core.dsl.meta.MetadataVisitor;

public abstract class AbstractTypeConverter<I, O, S extends FieldId & DslId> implements TypeConverter<I, O, S> {

    abstract O convert(I in);

    public abstract String readable();

    @Override
    public O convert(DslModel<S> fieldModel, BaseFieldInfo<I, S> in) {
        return convert(fieldModel.get(in.id()));
    }

    @Override
    public void accept(MetadataVisitor visitor, int depth) {
        // TODO
    }
}
