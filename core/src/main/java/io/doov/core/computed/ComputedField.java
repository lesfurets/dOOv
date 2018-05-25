/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.core.computed;

import java.util.function.Function;
import java.util.function.Predicate;

import io.doov.core.FieldId;

public class ComputedField {

    public static Builder when(Predicate<FieldId> predicate) {
        return new Builder().when(predicate);
    }

    private final Predicate<FieldId> predicate;
    private final Function<FieldId, FieldId> keyFunction;
    private final Function<Object, Object> valueFunction;

    private ComputedField(Predicate<FieldId> predicate,
            Function<FieldId, FieldId> keyFunction,
            Function<Object, Object> valueFunction) {
        this.predicate = predicate;
        this.keyFunction = keyFunction;
        this.valueFunction = valueFunction;
    }

    public Predicate<FieldId> getPredicate() {
        return predicate;
    }

    public Function<FieldId, FieldId> getKeyFunction() {
        return keyFunction;
    }

    public Function<Object, Object> getValueFunction() {
        return valueFunction;
    }

    public static final class Builder {

        private Predicate<FieldId> predicate;
        private Function<FieldId, FieldId> keyFunction;
        private Function<Object, Object> valueFunction;

        public Builder when(Predicate<FieldId> predicate) {
            this.predicate = predicate;
            return this;
        }

        public Builder compute(Function<FieldId, FieldId> keyFunction) {
            this.keyFunction = keyFunction;
            return this;
        }

        public <T> Builder with(Function<T, T> valueFunction) {
            //noinspection unchecked
            this.valueFunction = (Function<Object, Object>) valueFunction;
            return this;
        }

        public ComputedField build() {
            return new ComputedField(predicate, keyFunction, valueFunction);
        }
    }
}
