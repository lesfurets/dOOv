package io.doov.core.dsl.mapping;

import io.doov.core.FieldId;
import io.doov.core.dsl.DslId;
import io.doov.core.dsl.field.BaseFieldInfo;
import io.doov.core.dsl.lang.BiMappingRule;
import io.doov.core.dsl.lang.BiStepMapping;
import io.doov.core.dsl.lang.BiTypeConverter;
import io.doov.core.dsl.mapping.DefaultBiMappingRule;
import io.doov.core.dsl.meta.MetadataVisitor;

public class DefaultBiStepMapping<I, J, O, S extends FieldId & DslId> implements BiStepMapping<I, J, O, S> {
    private final BaseFieldInfo<I, S> inFieldInfo;
    private final BaseFieldInfo<J, S> in2FieldInfo;
    private final BiTypeConverter<I, J, O, S> typeConverter;

    public DefaultBiStepMapping(BaseFieldInfo<I, S> inFieldInfo, BaseFieldInfo<J, S> in2FieldInfo,
                                BiTypeConverter<I, J, O, S> typeConverter) {

        this.inFieldInfo = inFieldInfo;
        this.in2FieldInfo = in2FieldInfo;
        this.typeConverter = typeConverter;
    }

    @Override
    public <T extends FieldId & DslId> BiMappingRule<I, J, O, S, T> to(BaseFieldInfo<O, T> outFieldInfo) {
        return new DefaultBiMappingRule<>(inFieldInfo, in2FieldInfo, outFieldInfo, typeConverter);
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
