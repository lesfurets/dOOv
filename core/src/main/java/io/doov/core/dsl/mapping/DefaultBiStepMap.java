package io.doov.core.dsl.mapping;

import io.doov.core.FieldId;
import io.doov.core.dsl.DslId;
import io.doov.core.dsl.field.BaseFieldInfo;
import io.doov.core.dsl.lang.BiStepMap;
import io.doov.core.dsl.lang.BiStepMapping;
import io.doov.core.dsl.lang.BiTypeConverter;
import io.doov.core.dsl.meta.MetadataVisitor;

public class DefaultBiStepMap<I, J, S extends FieldId & DslId> implements BiStepMap<I, J, S> {

    private final BaseFieldInfo<I, S> inFieldInfo;
    private final BaseFieldInfo<J, S> in2FieldInfo;

    public DefaultBiStepMap(BaseFieldInfo<I, S> inFieldInfo, BaseFieldInfo<J, S> in2FieldInfo) {
        this.inFieldInfo = inFieldInfo;
        this.in2FieldInfo = in2FieldInfo;
    }

    @Override
    public <O> BiStepMapping<I, J, O, S> using(BiTypeConverter<I, J, O, S> typeConverter) {
        return new DefaultBiStepMapping<>(inFieldInfo, in2FieldInfo, typeConverter);
    }

    @Override
    public String readable() {
        return null;
    }

    @Override
    public void accept(MetadataVisitor visitor, int depth) {
        // TODO
    }
}
