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
package io.doov.core;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Stream;

import io.doov.core.dsl.DslId;

/**
 * {@code FieldModel} implementation based on {@code java.util.Map}
 */
public class BaseFieldModel<F extends FieldId & DslId> implements FieldModel<F> {

    private final Map<F, Object> values;
    private final List<FieldInfo<F>> fieldInfos;

    public BaseFieldModel(List<FieldInfo<F>> fieldInfos) {
        this(new HashMap<>(), fieldInfos);
    }

    public BaseFieldModel(Map<F, Object> values, List<FieldInfo<F>> fieldInfos) {
        this.values = values;
        this.fieldInfos = fieldInfos;
    }

    public BaseFieldModel(FieldModel<F> fieldModel) {
        this(fieldModel.getFieldInfos());
        setAll(fieldModel);
    }

    public Map<FieldId, Object> asMap() {
        return new HashMap<>(values);
    }

    @Override
    public List<FieldInfo<F>> getFieldInfos() {
        return fieldInfos;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(F fieldId) {
        return (T) values.get(fieldId);
    }

    @Override
    public <T> void set(F fieldId, T value) {
        values.put(fieldId, value);
        Arrays.stream(siblingsOf(fieldId)).forEach(s -> values.put(s, value));
    }

    private static final FieldId[] NO_SIBLINGS = new FieldId[] {};

    private F[] siblingsOf(F fieldId) {
        Optional<FieldInfo<F>> sublings = fieldInfos.stream().filter(info -> info.id() == fieldId).findFirst();
        return sublings.map(FieldInfo::siblings).orElse((F[]) NO_SIBLINGS);
    }

    @Override
    public Iterator<Entry<F, Object>> iterator() {
        return values.entrySet().iterator();
    }

    @Override
    public Spliterator<Entry<F, Object>> spliterator() {
        return new HashSet<>(values.entrySet()).spliterator();
    }

    @Override
    public Stream<Entry<F, Object>> stream() {
        return new HashSet<>(values.entrySet()).stream();
    }

    @Override
    public Stream<Entry<F, Object>> parallelStream() {
        return new HashSet<>(values.entrySet()).parallelStream();
    }

}
