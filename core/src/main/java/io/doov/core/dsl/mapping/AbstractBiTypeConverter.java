package io.doov.core.dsl.mapping;

import io.doov.core.FieldId;
import io.doov.core.dsl.DslId;
import io.doov.core.dsl.DslModel;
import io.doov.core.dsl.field.BaseFieldInfo;
import io.doov.core.dsl.lang.BiTypeConverter;
import io.doov.core.dsl.meta.MetadataVisitor;

public abstract class AbstractBiTypeConverter<I, J, O, S extends FieldId & DslId>
                implements BiTypeConverter<I, J, O, S> {

    abstract O convert(I in, J in2);

    public abstract String readable();

    @Override
    public O convert(DslModel<S> fieldModel, BaseFieldInfo<I, S> in, BaseFieldInfo<J, S> in2) {
        return convert(fieldModel.get(in.id()), fieldModel.get(in2.id()));
    }

    @Override
    public void accept(MetadataVisitor visitor, int depth) {
        // TODO
    }
}
