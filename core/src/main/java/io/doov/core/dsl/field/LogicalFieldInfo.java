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
import io.doov.core.dsl.impl.BooleanCondition;
import io.doov.core.dsl.lang.StepCondition;

public interface LogicalFieldInfo<F extends FieldId & DslId> extends BaseFieldInfo<Boolean, F> {

    // not

    default StepCondition<F> not() {
        return getBooleanCondition().not();
    }

    // and

    default StepCondition<F> and(boolean value) {
        return getBooleanCondition().and(value);
    }

    default StepCondition<F> and(LogicalFieldInfo<F> value) {
        return getBooleanCondition().and(value);
    }

    // or

    default StepCondition<F> or(boolean value) {
        return getBooleanCondition().or(value);
    }

    default StepCondition<F> or(LogicalFieldInfo<F> value) {
        return getBooleanCondition().or(value);
    }

    // xor

    default StepCondition<F> xor(boolean value) {
        return getBooleanCondition().xor(value);
    }

    default StepCondition<F> xor(LogicalFieldInfo<F> value) {
        return getBooleanCondition().xor(value);
    }

    // true

    default StepCondition<F> isTrue() {
        return getBooleanCondition().isTrue();
    }

    // false

    default StepCondition<F> isFalse() {
        return getBooleanCondition().isFalse();
    }

    // implementation

    BooleanCondition<F> getBooleanCondition();

}
