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

import java.util.List;

import io.doov.core.FieldId;
import io.doov.core.FieldInfo;
import io.doov.core.dsl.DslId;

public class FieldInfoProvider {

    protected static <T, F extends FieldId & DslId> DefaultFieldInfoBuilder<T, F> defaultField(F fieldId) {
        return new DefaultFieldInfoBuilder<>(fieldId);
    }

    protected static <F extends FieldId & DslId> StringFieldInfoBuilder<F> stringField(F fieldId) {
        return new StringFieldInfoBuilder<>(fieldId);
    }

    protected static <F extends FieldId & DslId> CharacterFieldInfoBuilder<F> characterField(F fieldId) {
        return new CharacterFieldInfoBuilder<>(fieldId);
    }

    protected static <F extends FieldId & DslId> BooleanFieldInfoBuilder<F> booleanField(F fieldId) {
        return new BooleanFieldInfoBuilder<>(fieldId);
    }

    protected static <F extends FieldId & DslId> IntegerFieldInfoBuilder<F> integerField(F fieldId) {
        return new IntegerFieldInfoBuilder<>(fieldId);
    }

    protected static <F extends FieldId & DslId> LongFieldInfoBuilder<F> longField(F fieldId) {
        return new LongFieldInfoBuilder<>(fieldId);
    }

    protected static <F extends FieldId & DslId> DoubleFieldInfoBuilder<F> doubleField(F fieldId) {
        return new DoubleFieldInfoBuilder<>(fieldId);
    }

    protected static <F extends FieldId & DslId> FloatFieldInfoBuilder<F> floatField(F fieldId) {
        return new FloatFieldInfoBuilder<>(fieldId);
    }

    protected static <F extends FieldId & DslId> DateIsoFieldInfoBuilder<F> dateIsoField(F fieldId) {
        return new DateIsoFieldInfoBuilder<>(fieldId);
    }

    protected static <F extends FieldId & DslId> TimeIsoFieldInfoBuilder<F> timeIsoField(F fieldId) {
        return new TimeIsoFieldInfoBuilder<>(fieldId);
    }

    protected static <F extends FieldId & DslId> LocalDateFieldInfoBuilder<F> localDateField(F fieldId) {
        return new LocalDateFieldInfoBuilder<>(fieldId);
    }

    protected static <F extends FieldId & DslId> LocalDateTimeFieldInfoBuilder<F> localDateTimeField(F fieldId) {
        return new LocalDateTimeFieldInfoBuilder<>(fieldId);
    }

    protected static <F extends FieldId & DslId> LocalTimeFieldInfoBuilder<F> localTimeField(F fieldId) {
        return new LocalTimeFieldInfoBuilder<>(fieldId);
    }

    protected static <T extends Enum<T>, F extends FieldId & DslId> EnumFieldInfoBuilder<T, F> enumField(F fieldId) {
        return new EnumFieldInfoBuilder<>(fieldId);
    }

    protected static <T, C extends Iterable<T>, F extends FieldId & DslId> IterableFieldInfoBuilder<T, C, F>
    iterableField(F fieldId) {
        return new IterableFieldInfoBuilder<>(fieldId);
    }

    @SuppressWarnings("unchecked")
    protected static abstract class BaseFieldInfoBuilder<I extends FieldId & DslId, F extends DefaultFieldInfo<?, I>,
                    B extends BaseFieldInfoBuilder<I, F, B>> {

        protected final I fieldId;
        protected String readable;
        protected Class<?> type;
        protected Class<?>[] genericTypes = new Class<?>[] {};
        protected I[] siblings = (I[]) new FieldId[] {};

        BaseFieldInfoBuilder(I fieldId) {
            this.fieldId = fieldId;
        }

        public B readable(String readable) {
            this.readable = readable;
            return (B) this;
        }

        public B type(Class<?> type) {
            this.type = type;
            return (B) this;
        }

        public B genericTypes(Class<?>... genericTypes) {
            this.genericTypes = genericTypes;
            return (B) this;
        }

        public B siblings(I... siblings) {
            this.siblings = siblings;
            return (B) this;
        }

        public abstract F build(List<FieldInfo<I>> allFields);

    }

    protected static class DefaultFieldInfoBuilder<T, F extends FieldId & DslId>
                    extends BaseFieldInfoBuilder<F, DefaultFieldInfo<T, F>, DefaultFieldInfoBuilder<T, F>> {

        DefaultFieldInfoBuilder(F fieldId) {
            super(fieldId);
        }

        @Override
        public DefaultFieldInfo<T, F> build(List<FieldInfo<F>> allFields) {
            DefaultFieldInfo<T, F> info = new DefaultFieldInfo<>(fieldId, readable, type, genericTypes, siblings);
            allFields.add(info);
            return info;
        }

    }

    protected static class BooleanFieldInfoBuilder<F extends FieldId & DslId>
                    extends BaseFieldInfoBuilder<F, BooleanFieldInfo<F>, BooleanFieldInfoBuilder<F>> {

        BooleanFieldInfoBuilder(F fieldId) {
            super(fieldId);
        }

        @Override
        public BooleanFieldInfo<F> build(List<FieldInfo<F>> allFields) {
            BooleanFieldInfo<F> info = new BooleanFieldInfo<>(fieldId, readable, type, siblings);
            allFields.add(info);
            return info;
        }

    }

    protected static class StringFieldInfoBuilder<F extends FieldId & DslId>
                    extends BaseFieldInfoBuilder<F, StringFieldInfo<F>, StringFieldInfoBuilder<F>> {

        StringFieldInfoBuilder(F fieldId) {
            super(fieldId);
        }

        @Override
        public StringFieldInfo<F> build(List<FieldInfo<F>> allFields) {
            StringFieldInfo<F> info = new StringFieldInfo<>(fieldId, readable, siblings);
            allFields.add(info);
            return info;
        }

    }

    protected static class CharacterFieldInfoBuilder<F extends FieldId & DslId>
                    extends BaseFieldInfoBuilder<F, CharacterFieldInfo<F>, CharacterFieldInfoBuilder<F>> {

        CharacterFieldInfoBuilder(F fieldId) {
            super(fieldId);
        }

        @Override
        public CharacterFieldInfo<F> build(List<FieldInfo<F>> allFields) {
            CharacterFieldInfo<F> info = new CharacterFieldInfo<>(fieldId, readable, type, siblings);
            allFields.add(info);
            return info;
        }

    }

    protected static class DoubleFieldInfoBuilder<F extends FieldId & DslId>
                    extends BaseFieldInfoBuilder<F, DoubleFieldInfo<F>, DoubleFieldInfoBuilder<F>> {

        DoubleFieldInfoBuilder(F fieldId) {
            super(fieldId);
        }

        @Override
        public DoubleFieldInfo<F> build(List<FieldInfo<F>> allFields) {
            DoubleFieldInfo<F> info = new DoubleFieldInfo<>(fieldId, readable, type, siblings);
            allFields.add(info);
            return info;
        }

    }

    protected static class FloatFieldInfoBuilder<F extends FieldId & DslId>
                    extends BaseFieldInfoBuilder<F, FloatFieldInfo<F>, FloatFieldInfoBuilder<F>> {

        FloatFieldInfoBuilder(F fieldId) {
            super(fieldId);
        }

        @Override
        public FloatFieldInfo<F> build(List<FieldInfo<F>> allFields) {
            FloatFieldInfo<F> info = new FloatFieldInfo<>(fieldId, readable, type, siblings);
            allFields.add(info);
            return info;
        }

    }

    protected static class IntegerFieldInfoBuilder<F extends FieldId & DslId>
                    extends BaseFieldInfoBuilder<F, IntegerFieldInfo<F>, IntegerFieldInfoBuilder<F>> {

        IntegerFieldInfoBuilder(F fieldId) {
            super(fieldId);
        }

        @Override
        public IntegerFieldInfo<F> build(List<FieldInfo<F>> allFields) {
            IntegerFieldInfo<F> info = new IntegerFieldInfo<>(fieldId, readable, type, siblings);
            allFields.add(info);
            return info;
        }

    }

    protected static class LongFieldInfoBuilder<T extends FieldId & DslId>
                    extends BaseFieldInfoBuilder<T, LongFieldInfo<T>, LongFieldInfoBuilder<T>> {

        LongFieldInfoBuilder(T fieldId) {
            super(fieldId);
        }

        @Override
        public LongFieldInfo<T> build(List<FieldInfo<T>> allFields) {
            LongFieldInfo<T> info = new LongFieldInfo<>(fieldId, readable, type, siblings);
            allFields.add(info);
            return info;
        }

    }

    protected static class DateIsoFieldInfoBuilder<F extends FieldId & DslId>
                    extends BaseFieldInfoBuilder<F, DateIsoFieldInfo<F>, DateIsoFieldInfoBuilder<F>> {

        DateIsoFieldInfoBuilder(F fieldId) {
            super(fieldId);
        }

        @Override
        public DateIsoFieldInfo<F> build(List<FieldInfo<F>> allFields) {
            DateIsoFieldInfo<F> info = new DateIsoFieldInfo<>(fieldId, readable, siblings);
            allFields.add(info);
            return info;
        }

    }

    protected static class TimeIsoFieldInfoBuilder<F extends FieldId & DslId>
                    extends BaseFieldInfoBuilder<F, TimeIsoFieldInfo<F>, TimeIsoFieldInfoBuilder<F>> {

        TimeIsoFieldInfoBuilder(F fieldId) {
            super(fieldId);
        }

        @Override
        public TimeIsoFieldInfo<F> build(List<FieldInfo<F>> allFields) {
            TimeIsoFieldInfo<F> info = new TimeIsoFieldInfo<>(fieldId, readable, siblings);
            allFields.add(info);
            return info;
        }

    }

    protected static class LocalDateFieldInfoBuilder<F extends FieldId & DslId>
                    extends BaseFieldInfoBuilder<F, LocalDateFieldInfo<F>, LocalDateFieldInfoBuilder<F>> {

        LocalDateFieldInfoBuilder(F fieldId) {
            super(fieldId);
        }

        @Override
        public LocalDateFieldInfo<F> build(List<FieldInfo<F>> allFields) {
            LocalDateFieldInfo<F> info = new LocalDateFieldInfo<>(fieldId, readable, siblings);
            allFields.add(info);
            return info;
        }

    }

    protected static class LocalDateTimeFieldInfoBuilder<F extends FieldId & DslId>
                    extends BaseFieldInfoBuilder<F, LocalDateTimeFieldInfo<F>, LocalDateTimeFieldInfoBuilder<F>> {

        LocalDateTimeFieldInfoBuilder(F fieldId) {
            super(fieldId);
        }

        @Override
        public LocalDateTimeFieldInfo<F> build(List<FieldInfo<F>> allFields) {
            LocalDateTimeFieldInfo<F> info = new LocalDateTimeFieldInfo<>(fieldId, readable, siblings);
            allFields.add(info);
            return info;
        }

    }

    protected static class LocalTimeFieldInfoBuilder<F extends FieldId & DslId>
                    extends BaseFieldInfoBuilder<F, LocalTimeFieldInfo<F>, LocalTimeFieldInfoBuilder<F>> {

        LocalTimeFieldInfoBuilder(F fieldId) {
            super(fieldId);
        }

        @Override
        public LocalTimeFieldInfo<F> build(List<FieldInfo<F>> allFields) {
            LocalTimeFieldInfo<F> info = new LocalTimeFieldInfo<>(fieldId, readable, siblings);
            allFields.add(info);
            return info;
        }

    }

    protected static class EnumFieldInfoBuilder<T extends Enum<T>, F extends FieldId & DslId>
                    extends BaseFieldInfoBuilder<F, EnumFieldInfo<T, F>, EnumFieldInfoBuilder<T, F>> {

        EnumFieldInfoBuilder(F fieldId) {
            super(fieldId);
        }

        @Override
        public EnumFieldInfo<T, F> build(List<FieldInfo<F>> allFields) {
            EnumFieldInfo<T, F> info = new EnumFieldInfo<>(fieldId, readable, type, siblings);
            allFields.add(info);
            return info;
        }

    }

    protected static class IterableFieldInfoBuilder<T, C extends Iterable<T>, F extends FieldId & DslId>
                    extends BaseFieldInfoBuilder<F, IterableFieldInfo<T, C, F>, IterableFieldInfoBuilder<T, C, F>> {

        IterableFieldInfoBuilder(F fieldId) {
            super(fieldId);
        }

        @Override
        public IterableFieldInfo<T, C, F> build(List<FieldInfo<F>> allFields) {
            IterableFieldInfo<T, C, F> info = new IterableFieldInfo<>(fieldId, readable, type, genericTypes, siblings);
            allFields.add(info);
            return info;
        }

    }
}
