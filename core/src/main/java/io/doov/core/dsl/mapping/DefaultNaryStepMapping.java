package io.doov.core.dsl.mapping;

import java.util.List;

import io.doov.core.FieldId;
import io.doov.core.dsl.DslId;
import io.doov.core.dsl.field.BaseFieldInfo;
import io.doov.core.dsl.lang.GenericTypeConverter;
import io.doov.core.dsl.lang.NaryMappingRule;
import io.doov.core.dsl.lang.NaryStepMapping;
import io.doov.core.dsl.meta.MetadataVisitor;

public class DefaultNaryStepMapping<O, S extends FieldId & DslId> implements NaryStepMapping<O, S> {

    private final List<BaseFieldInfo> fieldInfos;
    private final GenericTypeConverter<O, S> typeConverter;

    public DefaultNaryStepMapping(List<BaseFieldInfo> fieldInfos, GenericTypeConverter<O, S> typeConverter) {
        this.fieldInfos = fieldInfos;
        this.typeConverter = typeConverter;
    }

    @Override
    public <T extends FieldId & DslId> NaryMappingRule<O, S, T> to(BaseFieldInfo<O, T> outFieldInfo) {
        return new DefaultNaryMappingRule<>(fieldInfos, outFieldInfo, typeConverter);
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
