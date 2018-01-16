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

import static io.doov.core.dsl.meta.UnaryMetadata.notMetadata;

import java.util.function.BiPredicate;

import io.doov.core.FieldId;
import io.doov.core.dsl.DslId;
import io.doov.core.dsl.DslModel;
import io.doov.core.dsl.lang.Context;
import io.doov.core.dsl.lang.StepCondition;
import io.doov.core.dsl.meta.UnaryMetadata;

public class LogicalUnaryCondition<F extends FieldId & DslId> extends AbstractStepCondition<F> {

    private LogicalUnaryCondition(UnaryMetadata metadata, BiPredicate<DslModel<F>, Context> predicate) {
        super(metadata, predicate);
    }

    public static <F extends FieldId & DslId> LogicalUnaryCondition<F> negate(StepCondition<F> step) {
        return new LogicalUnaryCondition<>(notMetadata(step.getMetadata()),
                (model, context) -> step.predicate().negate().test(model, context));
    }

}
