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
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.YEARS;

import java.time.temporal.*;
import java.util.Optional;
import java.util.function.*;

import io.doov.core.FieldId;
import io.doov.core.dsl.*;
import io.doov.core.dsl.field.NumericFieldInfo;
import io.doov.core.dsl.field.TemporalFieldInfo;
import io.doov.core.dsl.lang.Context;
import io.doov.core.dsl.lang.StepCondition;
import io.doov.core.dsl.meta.PredicateMetadata;
import io.doov.core.dsl.time.TemporalAdjuster;

public abstract class TemporalCondition<N extends Temporal, F extends FieldId & DslId> extends DefaultCondition<N, F> {

    public TemporalCondition(DslField<F> field) {
        super(field);
    }

    public TemporalCondition(DslField<F> field, PredicateMetadata metadata,
                    BiFunction<DslModel<F>, Context, Optional<N>> value) {
        super(field, metadata, value);
    }

    protected abstract TemporalCondition<N, F> temporalCondition(DslField<F> field, PredicateMetadata metadata,
                    BiFunction<DslModel<F>, Context, Optional<N>> value);

    // with

    public final TemporalCondition<N, F> with(TemporalAdjuster adjuster) {
        return temporalCondition(field, metadata.merge(withMetadata(field, adjuster.getMetadata())),
                        (model, context) -> value(model, field)
                                        .map(v -> withFunction(adjuster.getAdjuster()).apply(v)));
    }

    protected abstract Function<N, N> withFunction(java.time.temporal.TemporalAdjuster adjuster);

    // minus

    public final TemporalCondition<N, F> minus(int value, TemporalUnit unit) {
        return temporalCondition(field, metadata.merge(minusMetadata(field, value, unit)),
                        (model, context) -> value(model, field)
                                        .map(v -> minusFunction(value, unit).apply(v)));
    }

    public final TemporalCondition<N, F> minus(NumericFieldInfo<Integer, F> value, TemporalUnit unit) {
        return temporalCondition(field, metadata.merge(minusMetadata(field, value, unit)),
                        (model, context) -> value(model, field)
                                        .flatMap(l -> Optional.ofNullable(model.<Integer> get(value.id()))
                                                        .map(r -> minusFunction(r, unit).apply(l))));
    }

    protected abstract Function<N, N> minusFunction(int value, TemporalUnit unit);

    // plus

    public final TemporalCondition<N, F> plus(int value, TemporalUnit unit) {
        return temporalCondition(field, metadata.merge(plusMetadata(field, value, unit)),
                        (model, context) -> value(model, field)
                                        .map(v -> plusFunction(value, unit).apply(v)));
    }

    public final TemporalCondition<N, F> plus(NumericFieldInfo<Integer, F> value, TemporalUnit unit) {
        return temporalCondition(field, metadata.merge(plusMetadata(field, value, unit)),
                        (model, context) -> value(model, field)
                                        .flatMap(l -> Optional.ofNullable(model.<Integer> get(value.id()))
                                                        .map(r -> plusFunction(r, unit).apply(l))));
    }

    protected abstract Function<N, N> plusFunction(int value, TemporalUnit unit);

    // eq

    public final StepCondition<F> eq(TemporalCondition<N, F> value) {
        return predicate(equalsMetadata(field, value),
                        value.function,
                        Object::equals);
    }

    // before

    public final StepCondition<F> before(N value) {
        return predicate(beforeValueMetadata(this, value),
                        (model, context) -> Optional.ofNullable(value),
                        (l, r) -> beforeFunction().apply(l, r));
    }

    public final StepCondition<F> before(TemporalFieldInfo<N, F> value) {
        return predicate(beforeTemporalFieldMetadata(this, value),
                        (model, context) -> value(model, value),
                        (l, r) -> beforeFunction().apply(l, r));
    }

    public final StepCondition<F> before(Supplier<N> value) {
        return predicate(beforeSupplierMetadata(this, value),
                        (model, context) -> Optional.ofNullable(value.get()),
                        (l, r) -> beforeFunction().apply(l, r));
    }

    public final StepCondition<F> before(TemporalCondition<N, F> value) {
        return predicate(beforeTemporalConditionMetadata(this, value),
                        value.function,
                        (l, r) -> beforeFunction().apply(l, r));
    }

    public final StepCondition<F> beforeOrEq(N value) {
        return predicate(beforeOrEqualsValueMetadata(this, value),
                        (model, context) -> Optional.ofNullable(value),
                        (l, r) -> beforeOrEqualsFunction().apply(l, r));
    }

    public final StepCondition<F> beforeOrEq(Supplier<N> value) {
        return predicate(beforeOrEqualsSupplierMetadata(this, value),
                        (model, context) -> Optional.ofNullable(value.get()),
                        (l, r) -> beforeOrEqualsFunction().apply(l, r));
    }

    public final StepCondition<F> beforeOrEq(TemporalCondition<N, F> value) {
        return predicate(beforeOrEqualsTemporalConditionMetadata(this, value),
                        value.function,
                        (l, r) -> beforeOrEqualsFunction().apply(l, r));
    }

    protected abstract BiFunction<N, N, Boolean> beforeFunction();

    protected abstract BiFunction<N, N, Boolean> beforeOrEqualsFunction();

    // after

    public final StepCondition<F> after(N value) {
        return predicate(afterValueMetadata(this, value),
                        (model, context) -> Optional.ofNullable(value),
                        (l, r) -> afterFunction().apply(l, r));
    }

    public final StepCondition<F> after(TemporalFieldInfo<N, F> value) {
        return predicate(afterTemporalFieldMetadata(this, value),
                        (model, context) -> value(model, value),
                        (l, r) -> afterFunction().apply(l, r));
    }

    public final StepCondition<F> after(Supplier<N> value) {
        return predicate(afterSupplierMetadata(this, value),
                        (model, context) -> Optional.ofNullable(value.get()),
                        (l, r) -> afterFunction().apply(l, r));
    }

    public final StepCondition<F> after(TemporalCondition<N, F> value) {
        return predicate(afterTemporalConditionMetadata(this, value),
                        value.function,
                        (l, r) -> afterFunction().apply(l, r));
    }

    public final StepCondition<F> afterOrEq(N value) {
        return predicate(afterOrEqualsValueMetadata(this, value),
                        (model, context) -> Optional.ofNullable(value),
                        (l, r) -> afterOrEqualsFunction().apply(l, r));
    }

    public final StepCondition<F> afterOrEq(Supplier<N> value) {
        return predicate(afterOrEqualsSupplierMetadata(this, value),
                        (model, context) -> Optional.ofNullable(value.get()),
                        (l, r) -> afterOrEqualsFunction().apply(l, r));
    }

    public final StepCondition<F> afterOrEq(TemporalCondition<N, F> value) {
        return predicate(afterOrEqualsTemporalConditionMetadata(this, value),
                        value.function,
                        (l, r) -> afterOrEqualsFunction().apply(l, r));
    }

    protected abstract BiFunction<N, N, Boolean> afterFunction();

    protected abstract BiFunction<N, N, Boolean> afterOrEqualsFunction();

    // between

    public final StepCondition<F> between(N minInclusive, N maxExclusive) {
        return LogicalBinaryCondition.and(beforeOrEq(maxExclusive), afterOrEq(minInclusive));
    }

    public final StepCondition<F> between(Supplier<N> minInclusive, Supplier<N> maxExclusive) {
        return LogicalBinaryCondition.and(beforeOrEq(maxExclusive), afterOrEq(minInclusive));
    }

    public final StepCondition<F> between(TemporalCondition<N, F> minInclusive, TemporalCondition<N, F> maxExclusive) {
        return LogicalBinaryCondition.and(beforeOrEq(maxExclusive), afterOrEq(minInclusive));
    }

    // not between

    public final StepCondition<F> notBetween(N minInclusive, N maxExclusive) {
        return LogicalUnaryCondition.negate(between(minInclusive, maxExclusive));
    }

    public final StepCondition<F> notBetween(Supplier<N> minInclusive, Supplier<N> maxExclusive) {
        return LogicalUnaryCondition.negate(between(minInclusive, maxExclusive));
    }

    public final StepCondition<F> notBetween(TemporalCondition<N, F> minInclusive, TemporalCondition<N, F> maxExclusive) {
        return LogicalUnaryCondition.negate(between(minInclusive, maxExclusive));
    }

    // age

    public final NumericCondition<Integer, F> ageAt(N value) {
        return new IntegerCondition<>(timeBetween(ageAtValueMetadata(this, value), YEARS, value));
    }

    public final NumericCondition<Integer, F> ageAt(TemporalFieldInfo<N, F> value) {
        return new IntegerCondition<>(timeBetween(ageAtTemporalFieldMetadata(this, value), YEARS, value));
    }

    public final NumericCondition<Integer, F> ageAt(TemporalCondition<N, F> value) {
        return new IntegerCondition<>(timeBetween(ageAtTemporalConditionMetadata(this, value), YEARS, value));
    }

    public final NumericCondition<Integer, F> ageAt(Supplier<N> value) {
        return new IntegerCondition<>(timeBetween(ageAtSupplierMetadata(this, value), YEARS, value));
    }

    // days between

    public final NumericCondition<Integer, F> daysBetween(N value) {
        return new IntegerCondition<>(timeBetween(ageAtValueMetadata(this, value), DAYS, value));
    }

    public final NumericCondition<Integer, F> daysBetween(TemporalFieldInfo<N, F> value) {
        return new IntegerCondition<>(timeBetween(ageAtTemporalFieldMetadata(this, value), DAYS, value));
    }

    public final NumericCondition<Integer, F> daysBetween(TemporalCondition<N, F> value) {
        return new IntegerCondition<>(timeBetween(ageAtTemporalConditionMetadata(this, value), DAYS, value));
    }

    public final NumericCondition<Integer, F> daysBetween(Supplier<N> value) {
        return new IntegerCondition<>(timeBetween(ageAtSupplierMetadata(this, value), DAYS, value));
    }

    // months between

    public final NumericCondition<Integer, F> monthsBetween(N value) {
        return new IntegerCondition<>(timeBetween(ageAtValueMetadata(this, value), MONTHS, value));
    }

    public final NumericCondition<Integer, F> monthsBetween(TemporalFieldInfo<N, F> value) {
        return new IntegerCondition<>(timeBetween(ageAtTemporalFieldMetadata(this, value), MONTHS, value));
    }

    public final NumericCondition<Integer, F> monthsBetween(TemporalCondition<N, F> value) {
        return new IntegerCondition<>(timeBetween(ageAtTemporalConditionMetadata(this, value), MONTHS, value));
    }

    public final NumericCondition<Integer, F> monthsBetween(Supplier<N> value) {
        return new IntegerCondition<>(timeBetween(ageAtSupplierMetadata(this, value), MONTHS, value));
    }

    // years between

    public final NumericCondition<Integer, F> yearsBetween(N value) {
        return new IntegerCondition<>(timeBetween(ageAtValueMetadata(this, value), YEARS, value));
    }

    public final NumericCondition<Integer, F> yearsBetween(TemporalFieldInfo<N, F> value) {
        return new IntegerCondition<>(timeBetween(ageAtTemporalFieldMetadata(this, value), YEARS, value));
    }

    public final NumericCondition<Integer, F> yearsBetween(TemporalCondition<N, F> value) {
        return new IntegerCondition<>(timeBetween(ageAtTemporalConditionMetadata(this, value), YEARS, value));
    }

    public final NumericCondition<Integer, F> yearsBetween(Supplier<N> value) {
        return new IntegerCondition<>(timeBetween(ageAtSupplierMetadata(this, value), YEARS, value));
    }

    // time between

    protected final NumericCondition<Long, F> timeBetween(PredicateMetadata metadata, ChronoUnit unit, N value) {
        return new LongCondition<>(field, metadata,
                        (model, context) -> value(model, field)
                                        .flatMap(l -> Optional.ofNullable(value)
                                                        .map(r -> betweenFunction(unit).apply(l, r))));
    }

    protected final NumericCondition<Long, F> timeBetween(PredicateMetadata metadata, ChronoUnit unit,
                    TemporalFieldInfo<N, F> value) {
        return new LongCondition<>(field, metadata,
                        (model, context) -> value(model, field)
                                        .flatMap(l -> valueModel(model, value)
                                                        .map(r -> betweenFunction(unit).apply(l, r))));
    }

    protected final NumericCondition<Long, F> timeBetween(PredicateMetadata metadata, ChronoUnit unit,
                    TemporalCondition<N, F> value) {
        return new LongCondition<>(field, metadata,
                        (model, context) -> value(model, field)
                                        .flatMap(l -> value.function.apply(model, context)
                                                        .map(r -> betweenFunction(unit).apply(l, r))));
    }

    protected final NumericCondition<Long, F> timeBetween(PredicateMetadata metadata, ChronoUnit unit, Supplier<N> value) {
        return new LongCondition<>(field, metadata,
                        (model, context) -> value(model, field)
                                        .flatMap(l -> Optional.ofNullable(value.get())
                                                        .map(r -> betweenFunction(unit).apply(l, r))));
    }

    protected abstract BiFunction<N, N, Long> betweenFunction(ChronoUnit unit);

}
