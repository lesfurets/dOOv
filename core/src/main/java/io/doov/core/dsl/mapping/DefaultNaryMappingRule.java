package io.doov.core.dsl.mapping;

import java.util.Collections;
import java.util.List;

import io.doov.core.*;
import io.doov.core.dsl.DslId;
import io.doov.core.dsl.DslModel;
import io.doov.core.dsl.field.BaseFieldInfo;
import io.doov.core.dsl.lang.GenericTypeConverter;
import io.doov.core.dsl.lang.MappingRegistry;
import io.doov.core.dsl.lang.NaryMappingRule;
import io.doov.core.dsl.meta.MetadataVisitor;

public class DefaultNaryMappingRule<O, S extends FieldId & DslId, T extends FieldId & DslId>
                implements NaryMappingRule<O, S, T> {

    private final List<BaseFieldInfo> fieldInfos;
    private final BaseFieldInfo<O, T> outFieldInfo;
    private final GenericTypeConverter<O, S> typeConverter;

    public DefaultNaryMappingRule(List<BaseFieldInfo> fieldInfos, BaseFieldInfo<O, T> outFieldInfo,
                                  GenericTypeConverter<O, S> typeConverter) {
        this.fieldInfos = Collections.unmodifiableList(fieldInfos);
        this.outFieldInfo = outFieldInfo;
        this.typeConverter = typeConverter;
    }

    @Override
    public void executeOn(DslModel<S> inModel, DslModel<T> outModel) {
        outModel.set(outFieldInfo.id(), typeConverter.convert(inModel, fieldInfos));

    }

    @Override
    public NaryMappingRule<O, S, T> registerOn(MappingRegistry<S, T> registry) {
        registry.register(this);
        return this;
    }

    @Override
    public String readable() {
        return null;
    }

    @Override
    public void accept(MetadataVisitor visitor, int depth) {

    }
}
