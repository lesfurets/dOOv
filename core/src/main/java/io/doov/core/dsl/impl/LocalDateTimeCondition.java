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

import java.time.LocalDateTime;
import java.time.temporal.*;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import io.doov.core.FieldId;
import io.doov.core.dsl.*;
import io.doov.core.dsl.lang.Context;
import io.doov.core.dsl.meta.PredicateMetadata;

public class LocalDateTimeCondition<F extends FieldId & DslId> extends TemporalCondition<LocalDateTime, F> {

    public LocalDateTimeCondition(DslField<F> field) {
        super(field);
    }

    public LocalDateTimeCondition(DslField<F> field, PredicateMetadata metadata,
                    BiFunction<DslModel<F>, Context, Optional<LocalDateTime>> value) {
        super(field, metadata, value);
    }

    @Override
    protected TemporalCondition<LocalDateTime, F> temporalCondition(DslField<F> field, PredicateMetadata metadata,
                    BiFunction<DslModel<F>, Context, Optional<LocalDateTime>> value) {
        return new LocalDateTimeCondition<>(field, metadata, value);
    }

    @Override
    protected Function<LocalDateTime, LocalDateTime> minusFunction(int value, TemporalUnit unit) {
        return dateTime -> dateTime.minus(value, unit);
    }

    @Override
    protected Function<LocalDateTime, LocalDateTime> plusFunction(int value, TemporalUnit unit) {
        return dateTime -> dateTime.plus(value, unit);
    }

    @Override
    protected Function<LocalDateTime, LocalDateTime> withFunction(TemporalAdjuster ajuster) {
        return dateTime -> dateTime.with(ajuster);
    }

    @Override
    protected BiFunction<LocalDateTime, LocalDateTime, Boolean> afterFunction() {
        return LocalDateTime::isAfter;
    }

    @Override
    protected BiFunction<LocalDateTime, LocalDateTime, Boolean> afterOrEqualsFunction() {
        return (dateTime1, dateTime2) -> dateTime1.isAfter(dateTime2) || dateTime1.equals(dateTime2);
    }

    @Override
    protected BiFunction<LocalDateTime, LocalDateTime, Boolean> beforeFunction() {
        return LocalDateTime::isBefore;
    }

    @Override
    protected BiFunction<LocalDateTime, LocalDateTime, Boolean> beforeOrEqualsFunction() {
        return (dateTime1, dateTime2) -> dateTime1.isBefore(dateTime2) || dateTime1.equals(dateTime2);
    }

    @Override
    protected BiFunction<LocalDateTime, LocalDateTime, Long> betweenFunction(ChronoUnit unit) {
        return unit::between;
    }

}
