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

import static io.doov.core.dsl.meta.NaryMetadata.countMetadata;
import static io.doov.core.dsl.meta.NaryMetadata.matchAllMetadata;
import static io.doov.core.dsl.meta.NaryMetadata.matchAnyMetadata;
import static io.doov.core.dsl.meta.NaryMetadata.matchNoneMetadata;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;

import io.doov.core.FieldId;
import io.doov.core.dsl.DslId;
import io.doov.core.dsl.DslModel;
import io.doov.core.dsl.lang.Context;
import io.doov.core.dsl.lang.StepCondition;
import io.doov.core.dsl.meta.Metadata;
import io.doov.core.dsl.meta.NaryMetadata;

public class LogicalNaryCondition<F extends FieldId & DslId> extends AbstractStepCondition<F> {

    private LogicalNaryCondition(NaryMetadata metadata, BiPredicate<DslModel<F>, Context> predicate) {
        super(metadata, predicate);
    }

    // count

    public static <F extends FieldId & DslId> IntegerCondition<F> count(List<StepCondition<F>> steps) {
        return new IntegerCondition<>(null, countMetadata(getMetadatas(steps)),
                (model, context) -> Optional.of((int) steps.stream()
                        .filter(s -> s.predicate().test(model, context))
                        .count()));
    }

    // match any

    public static <F extends FieldId & DslId> LogicalNaryCondition<F> matchAny(List<StepCondition<F>> steps) {
        return new LogicalNaryCondition<>(matchAnyMetadata(getMetadatas(steps)),
                (model, context) -> context.isShortCircuit()
                        ? matchAnyShortCircuit(steps, model, context)
                        : matchAny(steps, model, context));
    }

    private static <F extends FieldId & DslId> boolean matchAnyShortCircuit(List<StepCondition<F>> steps, DslModel<F> model, Context context) {
        return steps.stream().anyMatch(s -> s.predicate().test(model, context));
    }

    private static <F extends FieldId & DslId> boolean matchAny(List<StepCondition<F>> steps, DslModel<F> model, Context context) {
        List<Boolean> results = steps.stream().map(s -> s.predicate().test(model, context)).collect(toList());
        return results.stream().anyMatch(Boolean::booleanValue);
    }

    // match all

    public static <F extends FieldId & DslId> LogicalNaryCondition<F> matchAll(List<StepCondition<F>> steps) {
        return new LogicalNaryCondition<>(matchAllMetadata(getMetadatas(steps)),
                (model, context) -> context.isShortCircuit()
                        ? matchAllShortCircuit(steps, model, context)
                        : matchAll(steps, model, context));
    }

    private static <F extends FieldId & DslId> boolean matchAllShortCircuit(List<StepCondition<F>> steps, DslModel<F> model, Context context) {
        return steps.stream().allMatch(s -> s.predicate().test(model, context));
    }

    private static <F extends FieldId & DslId> boolean matchAll(List<StepCondition<F>> steps, DslModel<F> model, Context context) {
        List<Boolean> results = steps.stream().map(s -> s.predicate().test(model, context)).collect(toList());
        return results.stream().allMatch(Boolean::booleanValue);
    }

    // match none

    public static <F extends FieldId & DslId> LogicalNaryCondition<F> matchNone(List<StepCondition<F>> steps) {
        return new LogicalNaryCondition<>(matchNoneMetadata(getMetadatas(steps)),
                (model, context) -> context.isShortCircuit()
                        ? matchNoneShortCircuit(steps, model, context)
                        : matchNone(steps, model, context));
    }

    private static <F extends FieldId & DslId> boolean matchNoneShortCircuit(List<StepCondition<F>> steps, DslModel<F> model, Context context) {
        return steps.stream().noneMatch(s -> s.predicate().test(model, context));
    }

    private static <F extends FieldId & DslId> boolean matchNone(List<StepCondition<F>> steps, DslModel<F> model, Context context) {
        List<Boolean> results = steps.stream().map(s -> s.predicate().test(model, context)).collect(toList());
        return results.stream().noneMatch(Boolean::booleanValue);
    }

    // static

    private static <F extends FieldId & DslId> List<Metadata> getMetadatas(List<StepCondition<F>> steps) {
        return steps.stream().map(StepCondition::getMetadata).collect(toList());
    }

}
