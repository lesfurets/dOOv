package io.doov.core.dsl.mapping;

import io.doov.core.FieldId;
import io.doov.core.dsl.DslId;
import io.doov.core.dsl.field.BaseFieldInfo;
import io.doov.core.dsl.lang.SimpleMappingRule;
import io.doov.core.dsl.lang.StepMap;
import io.doov.core.dsl.lang.StepMapping;
import io.doov.core.dsl.lang.TypeConverter;
import io.doov.core.dsl.meta.MetadataVisitor;

public class DefaultStepMap<I, S extends FieldId & DslId> implements StepMap<I, S> {

    private final BaseFieldInfo<I, S> inFieldInfo;

    public DefaultStepMap(BaseFieldInfo<I, S> inFieldInfo) {
        this.inFieldInfo = inFieldInfo;
    }

    @Override
    public String readable() {
        return "map";
    }

    @Override
    public void accept(MetadataVisitor visitor, int depth) {
        // TODO
    }

    @Override
    public <O> StepMapping<I, O, S> using(TypeConverter<I, O, S> typeConverter) {
        return new DefaultStepMapping<>(inFieldInfo, typeConverter);
    }

    @Override
    public <T extends FieldId & DslId> SimpleMappingRule<I, I, S, T> to(BaseFieldInfo<I, T> outFieldInfo) {
        return new DefaultMappingRule<>(inFieldInfo, outFieldInfo, DefaultTypeConverter.identity());
    }
}
