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

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

import io.doov.core.FieldId;
import io.doov.core.dsl.*;
import io.doov.core.dsl.lang.Context;
import io.doov.core.dsl.meta.PredicateMetadata;

public class LongCondition<F extends FieldId & DslId> extends NumericCondition<Long, F> {

    public LongCondition(DslField<F> field) {
        super(field);
    }

    public LongCondition(DslField<F> field, PredicateMetadata metadata,
                    BiFunction<DslModel<F>, Context, Optional<Long>> value) {
        super(field, metadata, value);
    }

    @Override
    NumericCondition<Long, F> numericCondition(DslField<F> field, PredicateMetadata metadata,
                    BiFunction<DslModel<F>, Context, Optional<Long>> value) {
        return new LongCondition<>(field, metadata, value);
    }

    @Override
    public BiFunction<Long, Long, Boolean> lesserThanFunction() {
        return (l, r) -> l < r;
    }

    @Override
    public BiFunction<Long, Long, Boolean> lesserOrEqualsFunction() {
        return (l, r) -> l <= r;
    }

    @Override
    public BiFunction<Long, Long, Boolean> greaterThanFunction() {
        return (l, r) -> l > r;
    }

    @Override
    public BiFunction<Long, Long, Boolean> greaterOrEqualsFunction() {
        return (l, r) -> l >= r;
    }

    @Override
    BinaryOperator<Long> minFunction() {
        return Long::min;
    }

    @Override
    BinaryOperator<Long> sumFunction() {
        return Long::sum;
    }

    @Override
    BiFunction<Long, Integer, Long> timesFunction() {
        return (l, r) -> l * r;
    }

    @Override
    Long identity() {
        return 0L;
    }

}
