package io.doov.core.dsl.mapping;

import java.util.Optional;
import java.util.function.BiFunction;

import io.doov.core.FieldId;
import io.doov.core.dsl.DslId;
import io.doov.core.dsl.lang.BiTypeConverter;

public class DefaultBiTypeConverter<I, J, O, S extends FieldId & DslId> extends AbstractBiTypeConverter<I, J, O, S> {

    private final BiFunction<Optional<I>, Optional<J>, O> converter;
    private final String description;

    public static <I, J, O, S extends FieldId & DslId> BiTypeConverter<I, J, O, S> converter(BiFunction<Optional<I>, Optional<J>, O> converter,
                                                               String description) {
        return new DefaultBiTypeConverter<>(converter, description);
    }

    public static <I, J, O, S extends FieldId & DslId> BiTypeConverter<I, J, O, S> converter(BiFunction<I, J, O> converter, O nullCase,
                                                               String description) {
        return new DefaultBiTypeConverter<>(
                (i, j) -> (i.isPresent() && j.isPresent()) ? converter.apply(i.get(), j.get()) : nullCase, description);
    }

    public static <I, J, O, S extends FieldId & DslId> BiTypeConverter<I, J, O, S> converter(BiFunction<I, J, O> converter, I nullIn, J nullIn2,
                                                               String description) {
        return new DefaultBiTypeConverter<>((i, j) -> converter.apply(i.orElse(nullIn), j.orElse(nullIn2)), description);
    }

    public DefaultBiTypeConverter(BiFunction<Optional<I>, Optional<J>, O> converter, String description) {
        this.converter = converter;
        this.description = description;
    }

    @Override
    O convert(I in, J in2) {
        return converter.apply(Optional.ofNullable(in), Optional.ofNullable(in2));
    }

    @Override
    public String readable() {
        return description;
    }
}
