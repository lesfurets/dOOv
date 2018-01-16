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

import io.doov.core.FieldId;
import io.doov.core.FieldInfo;
import io.doov.core.dsl.DslId;
import io.doov.core.dsl.impl.DefaultCondition;

public class DefaultFieldInfo<T, F extends FieldId & DslId> implements FieldInfo<F>, BaseFieldInfo<T, F> {

    private final F fieldId;
    private final String readable;
    private final Class<?> type;
    private final Class<?>[] genericTypes;
    private final F[] siblings;

    public DefaultFieldInfo(F fieldId, String readable, Class<?> type, Class<?>[] genericTypes, F[] siblings) {
        this.fieldId = fieldId;
        this.readable = readable;
        this.type = type;
        this.genericTypes = genericTypes;
        this.siblings = siblings;
    }

    @Override
    public F id() {
        return fieldId;
    }

    @Override
    public String readable() {
        return readable;
    }

    @Override
    public Class<?> type() {
        return type;
    }

    @Override
    public F[] siblings() {
        return siblings;
    }

    @Override
    public Class<?>[] genericTypes() {
        return genericTypes;
    }

    @Override
    public DefaultCondition<T, F> getDefaultCondition() {
        return new DefaultCondition<>(this);
    }

}
