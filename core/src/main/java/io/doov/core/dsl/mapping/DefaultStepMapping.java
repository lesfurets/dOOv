package io.doov.core.dsl.mapping;

import io.doov.core.FieldId;
import io.doov.core.dsl.DslId;
import io.doov.core.dsl.field.BaseFieldInfo;
import io.doov.core.dsl.lang.SimpleMappingRule;
import io.doov.core.dsl.lang.StepMapping;
import io.doov.core.dsl.lang.TypeConverter;
import io.doov.core.dsl.meta.MetadataVisitor;

public class DefaultStepMapping<I, O, S extends FieldId & DslId> implements StepMapping<I, O, S> {

    private final BaseFieldInfo<I, S> inFieldInfo;
    private final TypeConverter<I, O, S> typeConverter;

    public DefaultStepMapping(BaseFieldInfo<I, S> inFieldInfo, TypeConverter<I, O, S> typeConverter) {
        this.inFieldInfo = inFieldInfo;
        this.typeConverter = typeConverter;
    }

    @Override
    public <T extends FieldId & DslId> SimpleMappingRule<I, O, S, T> to(BaseFieldInfo<O, T> outFieldInfo) {
        return new DefaultMappingRule<>(inFieldInfo, outFieldInfo, typeConverter);
    }

    @Override
    public String readable() {
        return "to";
    }

    @Override
    public void accept(MetadataVisitor visitor, int depth) {
        // TODO
    }
}
