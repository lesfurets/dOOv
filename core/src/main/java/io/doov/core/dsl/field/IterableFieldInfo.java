/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.core.dsl.field;

import io.doov.core.FieldId;
import io.doov.core.dsl.DslId;
import io.doov.core.dsl.impl.IterableCondition;
import io.doov.core.dsl.lang.StepCondition;

public class IterableFieldInfo<T, C extends Iterable<T>, F extends FieldId & DslId> extends DefaultFieldInfo<C, F> {

    public IterableFieldInfo(F fieldId, String readable, Class<?> type, Class<?>[] genericTypes, F[] siblings) {
        super(fieldId, readable, type, genericTypes, siblings);
    }

    public StepCondition<F> contains(T value) {
        return new IterableCondition<>(this).contains(value);
    }

    @SafeVarargs
    public final StepCondition<F> containsAll(T... values) {
        return new IterableCondition<>(this).containsAll(values);
    }

    public StepCondition<F> isEmpty() {
        return new IterableCondition<>(this).isEmpty();
    }

    public StepCondition<F> isNotEmpty() {
        return new IterableCondition<>(this).isNotEmpty();
    }

    public StepCondition<F> hasSize(int size) {
        return new IterableCondition<>(this).hasSize(size);
    }

    public StepCondition<F> hasNotSize(int size) {
        return new IterableCondition<>(this).hasNotSize(size);
    }

}
