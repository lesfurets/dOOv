/*
 * Copyright 2017 Courtanet
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package io.doov.core.dsl.impl;

import static io.doov.core.dsl.meta.LeafMetadata.*;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import java.util.Optional;
import java.util.function.BiFunction;

import io.doov.core.FieldId;
import io.doov.core.dsl.*;
import io.doov.core.dsl.field.LogicalFieldInfo;
import io.doov.core.dsl.lang.Context;
import io.doov.core.dsl.lang.StepCondition;
import io.doov.core.dsl.meta.LeafMetadata;

public class BooleanCondition<F extends FieldId & DslId> extends DefaultCondition<Boolean, F> {

    public BooleanCondition(DslField<F> field) {
        super(field);
    }

    public BooleanCondition(DslField<F> field, LeafMetadata metadata,
            BiFunction<DslModel<F>, Context, Optional<Boolean>> value) {
        super(field, metadata, value);
    }

    // not

    public final StepCondition<F> not() {
        return predicate(notMetadata(field), value -> !value);
    }

    // and

    public final StepCondition<F> and(boolean value) {
        return predicate(andMetadata(field, value),
                (model, context) -> Optional.of(value),
                Boolean::logicalAnd);
    }

    public final StepCondition<F> and(LogicalFieldInfo<F> value) {
        return predicate(andMetadata(field, value),
                (model, context) -> valueModel(model, value),
                Boolean::logicalAnd);
    }

    // or

    public final StepCondition<F> or(boolean value) {
        return predicate(orMetadata(field, value),
                (model, context) -> Optional.of(value),
                Boolean::logicalOr);
    }

    public final StepCondition<F> or(LogicalFieldInfo<F> value) {
        return predicate(orMetadata(field, value),
                (model, context) -> valueModel(model, value),
                Boolean::logicalOr);
    }

    // xor

    public final StepCondition<F> xor(boolean value) {
        return predicate(xorMetadata(field, value),
                (model, context) -> Optional.of(value),
                Boolean::logicalXor);
    }

    public final StepCondition<F> xor(LogicalFieldInfo<F> value) {
        return predicate(xorMetadata(field, value),
                (model, context) -> valueModel(model, value),
                Boolean::logicalXor);
    }

    // true

    public final StepCondition<F> isTrue() {
        return predicate(isMetadata(field, true),
                (model, context) -> Optional.of(TRUE),
                Boolean::equals);
    }

    // false

    public final StepCondition<F> isFalse() {
        return predicate(isMetadata(field, false),
                (model, context) -> Optional.of(FALSE),
                Boolean::equals);
    }

}
