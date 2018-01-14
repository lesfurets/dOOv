package io.doov.core.dsl.mapping;

import io.doov.core.*;
import io.doov.core.dsl.DslId;
import io.doov.core.dsl.DslModel;
import io.doov.core.dsl.field.BaseFieldInfo;
import io.doov.core.dsl.lang.MappingRegistry;
import io.doov.core.dsl.lang.SimpleMappingRule;
import io.doov.core.dsl.lang.TypeConverter;
import io.doov.core.dsl.meta.MetadataVisitor;

public class DefaultMappingRule<I, O, S extends FieldId & DslId, T extends FieldId & DslId>
                implements SimpleMappingRule<I, O, S, T> {

    private final BaseFieldInfo<I, S> inFieldInfo;
    private final BaseFieldInfo<O, T> outFieldInfo;
    private final TypeConverter<I, O, S> typeConverter;

    public DefaultMappingRule(BaseFieldInfo<I, S> inFieldInfo,
                    BaseFieldInfo<O, T> outFieldInfo,
                    TypeConverter<I, O, S> typeConverter) {
        this.inFieldInfo = inFieldInfo;
        this.outFieldInfo = outFieldInfo;
        this.typeConverter = typeConverter;
    }

    @Override
    public String readable() {
        return ".";
    }

    @Override
    public void accept(MetadataVisitor visitor, int depth) {
        // TODO
    }

    @Override
    public void executeOn(DslModel<S> inModel, DslModel<T> outModel) {
        outModel.set(outFieldInfo.id(), typeConverter.convert(inModel, inFieldInfo));
    }

    @Override
    public SimpleMappingRule<I, O, S, T> registerOn(MappingRegistry<S, T> registry) {
        registry.register(this);
        return this;
    }

}