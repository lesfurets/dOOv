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
package io.doov.core.dsl.field;

import io.doov.core.FieldId;
import io.doov.core.dsl.DslId;
import io.doov.core.dsl.impl.NumericCondition;
import io.doov.core.dsl.lang.StepCondition;

public interface NumericFieldInfo<N extends Number, F extends FieldId & DslId> extends BaseFieldInfo<N, F> {

    // lesser than

    default StepCondition<F> lesserThan(N value) {
        return getNumericCondition().lesserThan(value);
    }

    default StepCondition<F> lesserOrEquals(N value) {
        return getNumericCondition().lesserOrEquals(value);
    }

    default StepCondition<F> lesserThan(NumericFieldInfo<N, F> field) {
        return getNumericCondition().lesserThan(field);
    }

    default StepCondition<F> lesserOrEquals(NumericFieldInfo<N, F> field) {
        return getNumericCondition().lesserOrEquals(field);
    }

    // greater than

    default StepCondition<F> greaterThan(N value) {
        return getNumericCondition().greaterThan(value);
    }

    default StepCondition<F> greaterThan(NumericFieldInfo<N, F> field) {
        return getNumericCondition().greaterThan(field);
    }

    default StepCondition<F> greaterOrEquals(N value) {
        return getNumericCondition().greaterOrEquals(value);
    }

    default StepCondition<F> greaterOrEquals(NumericFieldInfo<N, F> field) {
        return getNumericCondition().greaterOrEquals(field);
    }

    // between

    default StepCondition<F> between(N minIncluded, N maxExcluded) {
        return getNumericCondition().between(minIncluded, maxExcluded);
    }

    default StepCondition<F> between(NumericFieldInfo<N, F> minIncluded, NumericFieldInfo<N, F> maxExcluded) {
        return getNumericCondition().between(minIncluded, maxExcluded);
    }

    // times

    default NumericCondition<N, F> times(int multiplier) {
        return getNumericCondition().times(multiplier);
    }

    // when

    default NumericCondition<N, F> when(StepCondition<F> condition) {
        return getNumericCondition().when(condition);
    }

    // abstract

    NumericCondition<N, F> getNumericCondition();

}
