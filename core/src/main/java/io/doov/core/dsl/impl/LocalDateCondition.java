/*
 * Copyright 2017 Courtanet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.doov.core.dsl.impl;

import java.time.LocalDate;
import java.time.temporal.*;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import io.doov.core.FieldId;
import io.doov.core.dsl.*;
import io.doov.core.dsl.lang.Context;
import io.doov.core.dsl.meta.PredicateMetadata;

public class LocalDateCondition<F extends FieldId & DslId> extends TemporalCondition<LocalDate, F> {

    public LocalDateCondition(DslField<F> field) {
        super(field);
    }

    public LocalDateCondition(DslField<F> field, PredicateMetadata metadata,
                    BiFunction<DslModel<F>, Context, Optional<LocalDate>> value) {
        super(field, metadata, value);
    }

    @Override
    protected TemporalCondition<LocalDate, F> temporalCondition(DslField<F> field, PredicateMetadata metadata,
                    BiFunction<DslModel<F>, Context, Optional<LocalDate>> value) {
        return new LocalDateCondition<>(field, metadata, value);
    }

    @Override
    protected Function<LocalDate, LocalDate> minusFunction(int value, TemporalUnit unit) {
        return date -> date.minus(value, unit);
    }

    @Override
    protected Function<LocalDate, LocalDate> plusFunction(int value, TemporalUnit unit) {
        return date -> date.plus(value, unit);
    }

    @Override
    protected Function<LocalDate, LocalDate> withFunction(TemporalAdjuster ajuster) {
        return date -> date.with(ajuster);
    }

    @Override
    protected BiFunction<LocalDate, LocalDate, Boolean> afterFunction() {
        return LocalDate::isAfter;
    }

    @Override
    protected BiFunction<LocalDate, LocalDate, Boolean> afterOrEqualsFunction() {
        return (date1, date2) -> date1.isAfter(date2) || date1.equals(date2);
    }

    @Override
    protected BiFunction<LocalDate, LocalDate, Boolean> beforeFunction() {
        return LocalDate::isBefore;
    }

    @Override
    protected BiFunction<LocalDate, LocalDate, Boolean> beforeOrEqualsFunction() {
        return (date1, date2) -> date1.isBefore(date2) || date1.equals(date2);
    }

    @Override
    protected BiFunction<LocalDate, LocalDate, Long> betweenFunction(ChronoUnit unit) {
        return unit::between;
    }

}
