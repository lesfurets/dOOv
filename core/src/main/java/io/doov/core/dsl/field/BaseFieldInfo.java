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

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

import java.util.Collection;
import java.util.function.*;

import io.doov.core.FieldId;
import io.doov.core.dsl.DslField;
import io.doov.core.dsl.DslId;
import io.doov.core.dsl.impl.DefaultCondition;
import io.doov.core.dsl.impl.IntegerCondition;
import io.doov.core.dsl.lang.StepCondition;

public interface BaseFieldInfo<T, F extends FieldId & DslId> extends DslField<F> {

    // null

    default StepCondition<F> isNull() {
        return getDefaultCondition().isNull();
    }

    default StepCondition<F> isNotNull() {
        return getDefaultCondition().isNotNull();
    }

    // eq

    default StepCondition<F> eq(T value) {
        return getDefaultCondition().eq(value);
    }

    default StepCondition<F> eq(BaseFieldInfo<T, F> value) {
        return getDefaultCondition().eq(value);
    }

    default StepCondition<F> eq(Supplier<T> value) {
        return getDefaultCondition().eq(value);
    }

    default StepCondition<F> notEq(T value) {
        return getDefaultCondition().notEq(value);
    }

    default StepCondition<F> notEq(BaseFieldInfo<T, F> value) {
        return getDefaultCondition().notEq(value);
    }

    // any match

    default StepCondition<F> anyMatch(Predicate<T> value) {
        return getDefaultCondition().anyMatch(singletonList(value));
    }

    @SuppressWarnings("unchecked")
    default StepCondition<F> anyMatch(T... values) {
        return getDefaultCondition().anyMatch(asList(values));
    }

    default StepCondition<F> anyMatch(Collection<T> values) {
        return getDefaultCondition().anyMatch(values);
    }

    // all match

    default StepCondition<F> allMatch(Predicate<T> value) {
        return getDefaultCondition().allMatch(singletonList(value));
    }

    @SuppressWarnings("unchecked")
    default StepCondition<F> allMatch(T... values) {
        return getDefaultCondition().allMatch(asList(values));
    }

    default StepCondition<F> allMatch(Collection<T> values) {
        return getDefaultCondition().allMatch(values);
    }

    // none match

    default StepCondition<F> noneMatch(Predicate<T> value) {
        return getDefaultCondition().noneMatch(singletonList(value));
    }

    @SuppressWarnings("unchecked")
    default StepCondition<F> noneMatch(T... values) {
        return getDefaultCondition().noneMatch(asList(values));
    }

    default StepCondition<F> noneMatch(Collection<T> values) {
        return getDefaultCondition().noneMatch(values);
    }

    // map

    default IntegerCondition<F> mapToInt(Function<T, Integer> mapper) {
        return getDefaultCondition().mapToInt(mapper);
    }

    // implementation

    DefaultCondition<T, F> getDefaultCondition();

}
