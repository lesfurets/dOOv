package io.doov.core.dsl.mapping;

import java.util.Optional;
import java.util.function.Function;

import io.doov.core.FieldId;
import io.doov.core.dsl.DslId;
import io.doov.core.dsl.lang.TypeConverter;

public class DefaultTypeConverter<I, O, S extends FieldId & DslId> extends AbstractTypeConverter<I, O, S> {

    private final Function<Optional<I>, O> converter;
    private final String description;

    public static <T, S extends FieldId & DslId> TypeConverter<T, T, S> identity() {
        return converter(Function.identity(), null, "identity");
    }

    public static <I, O, S extends FieldId & DslId> TypeConverter<I, O, S> converter(
                    Function<Optional<I>, O> converter, String description) {
        return new DefaultTypeConverter<>(converter, description);
    }

    public static <I, O, S extends FieldId & DslId> TypeConverter<I, O, S> converter(
                    Function<I, O> converter, O nullCase, String description) {
        return new DefaultTypeConverter<>(i -> i.map(converter).orElse(nullCase), description);
    }

    private DefaultTypeConverter(Function<Optional<I>, O> converter, String description) {
        this.converter = converter;
        this.description = description;
    }

    @Override
    O convert(I in) {
        return converter.apply(Optional.ofNullable(in));
    }

    @Override
    public String readable() {
        return description;
    }

}
