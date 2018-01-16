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

import java.util.Optional;
import java.util.function.BiFunction;

import io.doov.core.FieldId;
import io.doov.core.dsl.*;
import io.doov.core.dsl.lang.Context;
import io.doov.core.dsl.lang.StepCondition;
import io.doov.core.dsl.meta.LeafMetadata;

public class StringCondition<F extends FieldId & DslId> extends DefaultCondition<String, F> {

    public StringCondition(DslField<F> field) {
        super(field);
    }

    public StringCondition(DslField<F> field, LeafMetadata metadata,
            BiFunction<DslModel<F>, Context, Optional<String>> value) {
        super(field, metadata, value);
    }

    public final StepCondition<F> contains(String value) {
        return predicate(containsMetadata(field, value),
                (model, context) -> Optional.ofNullable(value),
                String::contains);
    }

    public final StepCondition<F> matches(String value) {
        return predicate(matchesMetadata(field, value),
                (model, context) -> Optional.ofNullable(value),
                String::matches);
    }

    public final StepCondition<F> startsWith(String value) {
        return predicate(startsWithMetadata(field, value),
                (model, context) -> Optional.ofNullable(value),
                String::startsWith);
    }

    public final StepCondition<F> endsWith(String value) {
        return predicate(endsWithMetadata(field, value),
                (model, context) -> Optional.ofNullable(value),
                String::endsWith);
    }

    public IntegerCondition<F> length() {
        return new IntegerCondition<>(field, lengthIsMetadata(field),
                (model, context) -> Optional.ofNullable(model.<String> get(field.id())).map(String::length));
    }

    public IntegerCondition<F> parseInt() {
        return new IntegerCondition<>(field, fieldMetadata(field),
                (model, context) -> Optional.ofNullable(model.<String> get(field.id())).map(Integer::parseInt));
    }

}
