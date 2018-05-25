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

import io.doov.core.serial.TypeAdapterRegistry;
import io.doov.core.serial.TypeAdapters;
import io.doov.core.virtual.VirtualFieldRegistry;
import io.doov.core.virtual.VirtualFields;

/**
 * {@code FieldModel} implementation based on {@code java.util.Map}
 */
public class BaseFieldModel implements FieldModel {

    private static TypeAdapterRegistry TYPE_ADAPTER_REGISTRY = new TypeAdapters();
    private static VirtualFieldRegistry DERIVED_FIELD_REGISTRY = new VirtualFields();

    protected Map<FieldId, Object> values;
    protected List<FieldInfo> fieldInfos;
    private VirtualFieldRegistry virtualFieldRegistry;

    public BaseFieldModel(List<FieldInfo> fieldInfos) {
        this(new HashMap<>(), fieldInfos, DERIVED_FIELD_REGISTRY);
    }

    public BaseFieldModel(List<FieldInfo> fieldInfos, VirtualFieldRegistry virtualFieldRegistry) {
        this(new HashMap<>(), fieldInfos, virtualFieldRegistry);
    }

    public BaseFieldModel(Map<FieldId, Object> values, List<FieldInfo> fieldInfos) {
        this(values, fieldInfos, DERIVED_FIELD_REGISTRY);
    }

    private BaseFieldModel(Map<FieldId, Object> values,
            List<FieldInfo> fieldInfos,
            VirtualFieldRegistry virtualFieldRegistry) {
        this.values = values;
        this.fieldInfos = fieldInfos;
        this.virtualFieldRegistry = virtualFieldRegistry;
    }

    public BaseFieldModel(FieldModel fieldModel) {
        this(fieldModel.getFieldInfos());
        setAll(fieldModel);
    }

    public Map<FieldId, Object> asMap() {
        return new HashMap<>(values);
    }

    @Override
    public List<FieldInfo> getFieldInfos() {
        return fieldInfos;
    }

    @Override
    public TypeAdapterRegistry getTypeAdapterRegistry() {
        return TYPE_ADAPTER_REGISTRY;
    }

    @Override
    public VirtualFieldRegistry getVirtualFieldRegistry() {
        return virtualFieldRegistry;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(FieldId fieldId) {
        return (T) values.get(fieldId);
    }

    @Override
    public void set(FieldId fieldId, Object value) {
        values.put(fieldId, value);

        Arrays.stream(siblingsOf(fieldId)).forEach(s -> values.put(s, value));

        setVirtualField(fieldId, value);
    }

    private static final FieldId[] NO_SIBLINGS = new FieldId[] {};

    private FieldId[] siblingsOf(FieldId fieldId) {
        return fieldInfos.stream()
                .filter(info -> info.id() == fieldId)
                .findFirst()
                .map(FieldInfo::siblings)
                .orElse(NO_SIBLINGS);
    }

    @Override
    public Iterator<Entry<FieldId, Object>> iterator() {
        return values.entrySet().iterator();
    }

    @Override
    public Spliterator<Entry<FieldId, Object>> spliterator() {
        return new HashSet<>(values.entrySet()).spliterator();
    }

    @Override
    public Stream<Entry<FieldId, Object>> stream() {
        return new HashSet<>(values.entrySet()).stream();
    }

    @Override
    public Stream<Entry<FieldId, Object>> parallelStream() {
        return new HashSet<>(values.entrySet()).parallelStream();
    }

}
