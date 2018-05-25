/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.core.virtual;

import java.util.function.Function;
import java.util.function.Predicate;

import io.doov.core.FieldId;

public class VirtualField {

    private final Predicate<FieldId> predicate;
    private final Function<FieldId, FieldId> keyFunction;
    private final Function<Object, Object> valueFunction;

    public VirtualField(Predicate<FieldId> predicate,
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
}
