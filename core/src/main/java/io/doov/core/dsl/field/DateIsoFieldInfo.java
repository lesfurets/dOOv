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

import static java.time.format.DateTimeFormatter.BASIC_ISO_DATE;

import java.time.LocalDate;
import java.util.Optional;
import java.util.function.BiFunction;

import io.doov.core.FieldId;
import io.doov.core.dsl.DslField;
import io.doov.core.dsl.DslId;
import io.doov.core.dsl.DslModel;
import io.doov.core.dsl.impl.DefaultCondition;
import io.doov.core.dsl.impl.LocalDateCondition;
import io.doov.core.dsl.impl.TemporalCondition;
import io.doov.core.dsl.lang.Context;
import io.doov.core.dsl.meta.PredicateMetadata;

public class DateIsoFieldInfo<F extends FieldId & DslId> extends DefaultFieldInfo<LocalDate, F> implements TemporalFieldInfo<LocalDate, F> {

    DateIsoFieldInfo(F fieldId, String readable, F[] siblings) {
        super(fieldId, readable, String.class, new Class<?>[] {}, siblings);
    }

    @Override
    public DefaultCondition<LocalDate, F> getDefaultCondition() {
        return new DateIsoCondition<>(this);
    }

    @Override
    public TemporalCondition<LocalDate, F> getTemporalCondition() {
        return new DateIsoCondition<>(this);
    }

    public static <T extends FieldId & DslId> Optional<LocalDate> parse(DslModel<T> model, T id) {
        return Optional.ofNullable(model.<String> get(id)).map(v -> LocalDate.parse(v, BASIC_ISO_DATE));
    }

    private class DateIsoCondition<T extends FieldId & DslId> extends LocalDateCondition<T> {

        private DateIsoCondition(DslField<T> field) {
            super(field);
        }

        private DateIsoCondition(DslField<T> field, PredicateMetadata metadata,
                        BiFunction<DslModel<T>, Context, Optional<LocalDate>> value) {
            super(field, metadata, value);
        }

        @Override
        protected TemporalCondition<LocalDate, T> temporalCondition(DslField<T> field, PredicateMetadata metadata,
                        BiFunction<DslModel<T>, Context, Optional<LocalDate>> value) {
            return new DateIsoCondition<>(field, metadata, value);
        }

        @Override
        protected Optional<LocalDate> valueModel(DslModel<T> model, DslField<T> field) {
            return parse(model, field.id());
        }

    }

}
