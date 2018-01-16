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

import static io.doov.core.dsl.meta.BinaryMetadata.andMetadata;
import static io.doov.core.dsl.meta.BinaryMetadata.orMetadata;

import java.util.function.BiPredicate;

import io.doov.core.FieldId;
import io.doov.core.dsl.DslId;
import io.doov.core.dsl.DslModel;
import io.doov.core.dsl.lang.Context;
import io.doov.core.dsl.lang.StepCondition;
import io.doov.core.dsl.meta.BinaryMetadata;

public class LogicalBinaryCondition<F extends FieldId & DslId> extends AbstractStepCondition<F> {

    private LogicalBinaryCondition(BinaryMetadata metadata, BiPredicate<DslModel<F>, Context> predicate) {
        super(metadata, predicate);
    }

    // and

    public static <F extends FieldId & DslId> LogicalBinaryCondition<F> and(StepCondition<F> left, StepCondition<F> right) {
        return new LogicalBinaryCondition<>(andMetadata(left.getMetadata(), right.getMetadata()),
                (model, context) -> context.isShortCircuit()
                        ? andShortCircuit(left, right, model, context)
                        : and(left, right, model, context));
    }

    private static <F extends FieldId & DslId> boolean and(StepCondition<F> left, StepCondition<F> right, DslModel<F> model, Context context) {
        boolean leftResult = left.predicate().test(model, context);
        boolean rightResult = right.predicate().test(model, context);
        return leftResult && rightResult;
    }

    private static <F extends FieldId & DslId> boolean andShortCircuit(StepCondition<F> left, StepCondition<F> right, DslModel<F> model, Context context) {
        return left.predicate().and(right.predicate()).test(model, context);
    }

    // or

    public static <F extends FieldId & DslId> LogicalBinaryCondition<F> or(StepCondition<F> left, StepCondition<F> right) {
        return new LogicalBinaryCondition<>(orMetadata(left.getMetadata(), right.getMetadata()),
                (model, context) -> context.isShortCircuit()
                        ? orShortCircuit(left, right, model, context)
                        : or(left, right, model, context));
    }

    private static <F extends FieldId & DslId> boolean or(StepCondition<F> left, StepCondition<F> right, DslModel<F> model, Context context) {
        boolean leftResult = left.predicate().test(model, context);
        boolean rightResult = right.predicate().test(model, context);
        return leftResult || rightResult;
    }

    private static <F extends FieldId & DslId> boolean orShortCircuit(StepCondition<F> left, StepCondition<F> right, DslModel<F> model, Context context) {
        return left.predicate().or(right.predicate()).test(model, context);
    }

}
