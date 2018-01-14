package io.doov.core.dsl.mapping;

import java.util.List;
import java.util.function.BiFunction;

import io.doov.core.FieldId;
import io.doov.core.dsl.DslId;
import io.doov.core.dsl.DslModel;
import io.doov.core.dsl.field.BaseFieldInfo;
import io.doov.core.dsl.lang.GenericTypeConverter;
import io.doov.core.dsl.meta.MetadataVisitor;

public class DefaultGenericTypeConverter<O, S extends FieldId & DslId> implements GenericTypeConverter<O, S> {

    private final BiFunction<DslModel<S>, List<BaseFieldInfo>, O> function;
    private final String description;

    public static <O, S extends FieldId & DslId> GenericTypeConverter<O, S> nConverter(
                    BiFunction<DslModel<S>, List<BaseFieldInfo>, O> function, String description) {
        return new DefaultGenericTypeConverter<>(function, description);
    }

    public DefaultGenericTypeConverter(BiFunction<DslModel<S>, List<BaseFieldInfo>, O> function, String description) {
        this.function = function;
        this.description = description;
    }

    @Override
    public final O convert(DslModel<S> fieldModel, List<BaseFieldInfo> fieldInfos) {
        return function.apply(fieldModel, fieldInfos);
    }

    @Override
    public String readable() {
        return description;
    }

    @Override
    public void accept(MetadataVisitor visitor, int depth) {
        // TODO
    }
}
