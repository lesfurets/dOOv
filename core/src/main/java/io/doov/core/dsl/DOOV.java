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
package io.doov.core.dsl;

import static io.doov.core.dsl.meta.LeafMetadata.falseMetadata;
import static io.doov.core.dsl.meta.LeafMetadata.trueMetadata;
import static java.util.Arrays.asList;

import java.util.Arrays;
import java.util.Objects;

import io.doov.core.FieldId;
import io.doov.core.dsl.field.BaseFieldInfo;
import io.doov.core.dsl.field.NumericFieldInfo;
import io.doov.core.dsl.impl.*;
import io.doov.core.dsl.lang.StepCondition;
import io.doov.core.dsl.lang.StepWhen;

public class DOOV {

    private DOOV() {
        // static
    }

    // when

    public static <F extends FieldId & DslId> StepWhen<F> when(StepCondition<F> condition) {
        return new DefaultStepWhen<>(condition);
    }

    // always

    public static <F extends FieldId & DslId> StepCondition<F> alwaysTrue() {
        return new DefaultStepCondition<>(trueMetadata(), (model, context) -> true);
    }

    public static <F extends FieldId & DslId> StepCondition<F> alwaysFalse() {
        return new DefaultStepCondition<>(falseMetadata(), (model, context) -> false);
    }

    // count

    @SafeVarargs
    public static <F extends FieldId & DslId> IntegerCondition<F> count(StepCondition<F>... steps) {
        return LogicalNaryCondition.count(asList(steps));
    }

    // match

    @SafeVarargs
    public static <F extends FieldId & DslId> StepCondition<F> matchAny(StepCondition<F>... steps) {
        return LogicalNaryCondition.matchAny(asList(steps));
    }

    @SafeVarargs
    public static <F extends FieldId & DslId> StepCondition<F> matchAll(StepCondition<F>... steps) {
        return LogicalNaryCondition.matchAll(asList(steps));
    }

    @SafeVarargs
    public static <F extends FieldId & DslId> StepCondition<F> matchNone(StepCondition<F>... steps) {
        return LogicalNaryCondition.matchNone(asList(steps));
    }

    // min

    @SafeVarargs
    public static <N extends Number, F extends FieldId & DslId> NumericCondition<N, F> min(NumericFieldInfo<N, F>... fields) {
        return Arrays.stream(fields)
                .filter(Objects::nonNull)
                .findFirst()
                .map(NumericFieldInfo::getNumericCondition)
                .map(c -> c.min(Arrays.asList(fields)))
                .orElseThrow(IllegalArgumentException::new);
    }

    // sum

    @SafeVarargs
    public static <N extends Number, F extends FieldId & DslId> NumericCondition<N, F> sum(NumericFieldInfo<N, F>... fields) {
        return Arrays.stream(fields)
                .filter(Objects::nonNull)
                .findFirst()
                .map(NumericFieldInfo::getNumericCondition)
                .map(c -> c.sum(Arrays.asList(fields)))
                .orElseThrow(IllegalArgumentException::new);
    }

    @SafeVarargs
    public static <N extends Number, F extends FieldId & DslId> NumericCondition<N, F> sum(NumericCondition<N, F>... conditions) {
        return Arrays.stream(conditions)
                .filter(Objects::nonNull)
                .findFirst()
                .map(c -> c.sumConditions(Arrays.asList(conditions)))
                .orElseThrow(IllegalArgumentException::new);
    }

}
