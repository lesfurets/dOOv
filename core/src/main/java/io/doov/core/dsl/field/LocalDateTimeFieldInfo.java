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

import java.time.LocalDateTime;

import io.doov.core.FieldId;
import io.doov.core.dsl.DslId;
import io.doov.core.dsl.impl.LocalDateTimeCondition;
import io.doov.core.dsl.impl.TemporalCondition;

public class LocalDateTimeFieldInfo<F extends FieldId & DslId> extends DefaultFieldInfo<LocalDateTime, F>
        implements TemporalFieldInfo<LocalDateTime, F> {

    public LocalDateTimeFieldInfo(F fieldId, String readable, F[] siblings) {
        super(fieldId, readable, LocalDateTime.class, new Class[] {}, siblings);
    }

    @Override
    public TemporalCondition<LocalDateTime, F> getTemporalCondition() {
        return new LocalDateTimeCondition<>(this);
    }

}
