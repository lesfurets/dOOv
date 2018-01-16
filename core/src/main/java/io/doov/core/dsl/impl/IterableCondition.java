/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.core.dsl.impl;

import static io.doov.core.dsl.meta.LeafMetadata.*;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.StreamSupport.stream;

import java.util.Optional;
import java.util.function.BiFunction;

import io.doov.core.FieldId;
import io.doov.core.dsl.*;
import io.doov.core.dsl.lang.Context;
import io.doov.core.dsl.lang.StepCondition;
import io.doov.core.dsl.meta.LeafMetadata;

public class IterableCondition<T, C extends Iterable<T>, F extends FieldId & DslId> extends DefaultCondition<C, F> {

    public IterableCondition(DslField<F> field) {
        super(field);
    }

    public IterableCondition(DslField<F> field, LeafMetadata metadata, BiFunction<DslModel<F>, Context, Optional<C>> value) {
        super(field, metadata, value);
    }

    public StepCondition<F> contains(T value) {
        return predicate(containsMetadata(field, value),
                collection -> stream(collection.spliterator(), false)
                        .anyMatch(value::equals));
    }

    @SafeVarargs
    public final StepCondition<F> containsAll(T... values) {
        return predicate(containsMetadata(field, (Object[]) values),
                iterable -> stream(iterable.spliterator(), false)
                        .collect(toSet()).containsAll(asList(values)));
    }

    public StepCondition<F> isEmpty() {
        return predicate(isEmptyMetadata(field), iterable -> !iterable.iterator().hasNext());
    }

    public StepCondition<F> isNotEmpty() {
        return predicate(isNotEmptyMetadata(field), iterable -> iterable.iterator().hasNext());
    }

    public StepCondition<F> hasSize(int size) {
        return predicate(hasSizeMetadata(field, size),
                iterable -> stream(iterable.spliterator(), false).count() == size);
    }

    public StepCondition<F> hasNotSize(int size) {
        return predicate(hasNotSizeMetadata(field, size),
                iterable -> stream(iterable.spliterator(), false).count() != size);
    }

}
