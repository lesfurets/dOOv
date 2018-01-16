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
package io.doov.core.dsl.field;

import static java.time.temporal.ChronoUnit.YEARS;

import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;
import java.util.function.Supplier;

import io.doov.core.FieldId;
import io.doov.core.dsl.DslId;
import io.doov.core.dsl.impl.NumericCondition;
import io.doov.core.dsl.impl.TemporalCondition;
import io.doov.core.dsl.lang.StepCondition;
import io.doov.core.dsl.time.TemporalAdjuster;

public interface TemporalFieldInfo<N extends Temporal, F extends FieldId & DslId> extends BaseFieldInfo<N, F> {
    
    // eq
    
    default StepCondition<F> eq(TemporalCondition<N, F> value) {
        return getTemporalCondition().eq(value);
    }

    // with

    default TemporalCondition<N, F> with(TemporalAdjuster ajuster) {
        return getTemporalCondition().with(ajuster);
    }

    // minus

    default TemporalCondition<N, F> minus(int value, TemporalUnit unit) {
        return getTemporalCondition().minus(value, unit);
    }

    default TemporalCondition<N, F> minus(NumericFieldInfo<Integer, F> value, TemporalUnit unit) {
        return getTemporalCondition().minus(value, unit);
    }

    default TemporalCondition<N, F> minusYears(int value) {
        return getTemporalCondition().minus(value, YEARS);
    }

    // plus

    default TemporalCondition<N, F> plus(int value, TemporalUnit unit) {
        return getTemporalCondition().plus(value, unit);
    }

    default TemporalCondition<N, F> plus(NumericFieldInfo<Integer, F> value, TemporalUnit unit) {
        return getTemporalCondition().plus(value, unit);
    }

    default TemporalCondition<N, F> plusYears(int value) {
        return getTemporalCondition().plus(value, YEARS);
    }

    // before

    default StepCondition<F> before(N value) {
        return getTemporalCondition().before(value);
    }

    default StepCondition<F> before(TemporalFieldInfo<N, F> value) {
        return getTemporalCondition().before(value);
    }

    default StepCondition<F> before(Supplier<N> value) {
        return getTemporalCondition().before(value);
    }

    default StepCondition<F> before(TemporalCondition<N, F> value) {
        return getTemporalCondition().before(value);
    }

    default StepCondition<F> beforeOrEq(N value) {
        return getTemporalCondition().beforeOrEq(value);
    }

    default StepCondition<F> beforeOrEq(Supplier<N> value) {
        return getTemporalCondition().beforeOrEq(value);
    }

    default StepCondition<F> beforeOrEq(TemporalCondition<N, F> value) {
        return getTemporalCondition().beforeOrEq(value);
    }

    // after

    default StepCondition<F> after(N value) {
        return getTemporalCondition().after(value);
    }

    default StepCondition<F> after(TemporalFieldInfo<N, F> value) {
        return getTemporalCondition().after(value);
    }

    default StepCondition<F> after(Supplier<N> value) {
        return getTemporalCondition().after(value);
    }

    default StepCondition<F> after(TemporalCondition<N, F> value) {
        return getTemporalCondition().after(value);
    }

    default StepCondition<F> afterOrEq(Supplier<N> value) {
        return getTemporalCondition().afterOrEq(value);
    }

    default StepCondition<F> afterOrEq(N value) {
        return getTemporalCondition().afterOrEq(value);
    }

    default StepCondition<F> afterOrEq(TemporalCondition<N, F> value) {
        return getTemporalCondition().afterOrEq(value);
    }

    // beetween

    default StepCondition<F> between(N minValueInclusive, N maxValueExclusive) {
        return getTemporalCondition().between(minValueInclusive, maxValueExclusive);
    }

    default StepCondition<F> between(Supplier<N> minValueInclusive, Supplier<N> maxValueExclusive) {
        return getTemporalCondition().between(minValueInclusive, maxValueExclusive);
    }

    default StepCondition<F> notBetween(N minValueInclusive, N maxValueExclusive) {
        return getTemporalCondition().notBetween(minValueInclusive, maxValueExclusive);
    }

    // age

    default NumericCondition<Integer, F> ageAt(N value) {
        return getTemporalCondition().ageAt(value);
    }

    default NumericCondition<Integer, F> ageAt(TemporalFieldInfo<N, F> value) {
        return getTemporalCondition().ageAt(value);
    }

    default NumericCondition<Integer, F> ageAt(TemporalCondition<N, F> value) {
        return getTemporalCondition().ageAt(value);
    }

    default NumericCondition<Integer, F> ageAt(Supplier<N> value) {
        return getTemporalCondition().ageAt(value);
    }

    // days between

    default NumericCondition<Integer, F> daysBetween(N value) {
        return getTemporalCondition().daysBetween(value);
    }

    default NumericCondition<Integer, F> daysBetween(TemporalFieldInfo<N, F> value) {
        return getTemporalCondition().daysBetween(value);
    }

    default NumericCondition<Integer, F> daysBetween(TemporalCondition<N, F> value) {
        return getTemporalCondition().daysBetween(value);
    }

    default NumericCondition<Integer, F> daysBetween(Supplier<N> value) {
        return getTemporalCondition().daysBetween(value);
    }

    // month between

    default NumericCondition<Integer, F> monthsBetween(N value) {
        return getTemporalCondition().monthsBetween(value);
    }

    default NumericCondition<Integer, F> monthsBetween(TemporalFieldInfo<N, F> value) {
        return getTemporalCondition().monthsBetween(value);
    }

    default NumericCondition<Integer, F> monthsBetween(TemporalCondition<N, F> value) {
        return getTemporalCondition().monthsBetween(value);
    }

    default NumericCondition<Integer, F> monthsBetween(Supplier<N> value) {
        return getTemporalCondition().monthsBetween(value);
    }

    // year between

    default NumericCondition<Integer, F> yearsBetween(N value) {
        return getTemporalCondition().yearsBetween(value);
    }

    default NumericCondition<Integer, F> yearsBetween(TemporalFieldInfo<N, F> value) {
        return getTemporalCondition().yearsBetween(value);
    }

    default NumericCondition<Integer, F> yearsBetween(TemporalCondition<N, F> value) {
        return getTemporalCondition().yearsBetween(value);
    }

    default NumericCondition<Integer, F> yearsBetween(Supplier<N> value) {
        return getTemporalCondition().yearsBetween(value);
    }

    // abstract

    TemporalCondition<N, F> getTemporalCondition();

}
