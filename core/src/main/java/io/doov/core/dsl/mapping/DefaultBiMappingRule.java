package io.doov.core.dsl.mapping;

import io.doov.core.FieldId;
import io.doov.core.dsl.DslId;
import io.doov.core.dsl.DslModel;
import io.doov.core.dsl.field.BaseFieldInfo;
import io.doov.core.dsl.lang.*;
import io.doov.core.dsl.meta.MetadataVisitor;

public class DefaultBiMappingRule<I, J, O, S extends FieldId & DslId, T extends FieldId & DslId>
                implements BiMappingRule<I, J, O, S, T> {

    private final BaseFieldInfo<I, S> inFieldInfo;
    private final BaseFieldInfo<J, S> in2FieldInfo;
    private final BaseFieldInfo<O, T> outFieldInfo;
    private final BiTypeConverter<I, J, O, S> typeConverter;

    public DefaultBiMappingRule(BaseFieldInfo<I, S> inFieldInfo, BaseFieldInfo<J, S> in2FieldInfo,
                                BaseFieldInfo<O, T> outFieldInfo, BiTypeConverter<I, J, O, S> typeConverter) {
        this.inFieldInfo = inFieldInfo;
        this.in2FieldInfo = in2FieldInfo;
        this.outFieldInfo = outFieldInfo;
        this.typeConverter = typeConverter;
    }

    @Override
    public void executeOn(DslModel<S> inModel, DslModel<T> outModel) {
        outModel.set(outFieldInfo.id(), typeConverter.convert(inModel, inFieldInfo, in2FieldInfo));
    }

    @Override
    public BiMappingRule<I, J, O, S, T> registerOn(MappingRegistry<S, T> registry) {
        registry.register(this);
        return this;
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
