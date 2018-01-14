package io.doov.core.dsl.mapping;

import java.util.List;

import io.doov.core.FieldId;
import io.doov.core.dsl.DslId;
import io.doov.core.dsl.field.BaseFieldInfo;
import io.doov.core.dsl.lang.GenericTypeConverter;
import io.doov.core.dsl.lang.NaryStepMap;
import io.doov.core.dsl.lang.NaryStepMapping;
import io.doov.core.dsl.meta.MetadataVisitor;

public class DefaultNaryStepMap<S extends FieldId & DslId> implements NaryStepMap<S> {

    private final List<BaseFieldInfo> fieldInfos;

    public DefaultNaryStepMap(List<BaseFieldInfo> fieldInfos) {
        this.fieldInfos = fieldInfos;
    }

    @Override
    public <O> NaryStepMapping<O, S> using(GenericTypeConverter<O, S> typeConverter) {
        return new DefaultNaryStepMapping<>(fieldInfos, typeConverter);
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
