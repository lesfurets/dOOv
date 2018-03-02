/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.core.dsl.runtime;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.function.Function;
import java.util.stream.Stream;

import io.doov.core.FieldId;
import io.doov.core.FieldInfo;
import io.doov.core.FieldModel;
import io.doov.core.serial.TypeAdapterRegistry;
import io.doov.core.serial.TypeAdapters;

public class RuntimeModel<M> implements FieldModel {

    private RuntimeFieldRegistry<M> fieldRegistry;
    private M model;

    public RuntimeModel(RuntimeFieldRegistry<M> fieldRegistry, M model) {
        this.fieldRegistry = fieldRegistry;
        this.model = model;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(FieldId id) {
        RuntimeField<M, Object> runtimeField = fieldRegistry.get(id);
        if (runtimeField == null) {
            return null;
        }
        return (T) runtimeField.get(model);
    }

    @Override
    public <T> void set(FieldId fieldId, T value) {
        RuntimeField<M, Object> runtimeField = fieldRegistry.get(fieldId);
        if (runtimeField != null) {
            runtimeField.set(model, value);
        }
    }

    @Override
    public Stream<Map.Entry<FieldId, Object>> stream() {
        return fieldRegistry.stream().map(e -> new SupplierEntry<>(e.id(), model, e));
    }

    @Override
    public Iterator<Map.Entry<FieldId, Object>> iterator() {
        return new Iterator<Map.Entry<FieldId, Object>>() {
            int position = 0;

            @Override
            public boolean hasNext() {
                return position < fieldRegistry.runtimeFields().size();
            }

            @Override
            public Map.Entry<FieldId, Object> next() {
                try {
                    RuntimeField<M, Object> mObjectRuntimeField = fieldRegistry.runtimeFields().get(position);
                    return new SupplierEntry<>(mObjectRuntimeField.id(), model, mObjectRuntimeField);
                } finally {
                    position++;
                }
            }
        };
    }

    @Override
    @SuppressWarnings("unchecked")
    public Spliterator<Map.Entry<FieldId, Object>> spliterator() {
        Map.Entry<FieldId, Object>[] entries = new Map.Entry[fieldRegistry.runtimeFields().size()];
        for (int i = 0; i < fieldRegistry.runtimeFields().size(); i++) {
            RuntimeField<M, Object> runtimeField = fieldRegistry.runtimeFields().get(i);
            entries[i] = new SupplierEntry<>(runtimeField.id(), model, runtimeField);
        }
        return Arrays.spliterator(entries, 0, entries.length);
    }

    @Override
    public Stream<Map.Entry<FieldId, Object>> parallelStream() {
        return fieldRegistry.stream().parallel().map(e -> new SupplierEntry<>(e.id(), model, e));
    }

    @Override
    public List<FieldInfo> getFieldInfos() {
        return fieldRegistry.fieldInfos();
    }

    private static class SupplierEntry<M, T> implements Map.Entry<FieldId, Object> {

        final FieldId fieldId;
        final M model;
        final Function<M, T> supplier;

        SupplierEntry(FieldId fieldId, M model, Function<M, T> supplier) {
            super();
            this.fieldId = fieldId;
            this.model = model;
            this.supplier = supplier;
        }

        @Override
        public FieldId getKey() {
            return fieldId;
        }

        @Override
        public Object getValue() {
            return supplier.apply(model);
        }

        @Override
        public Object setValue(Object value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public String toString() {
            return fieldId + ", " + String.valueOf(getValue());
        }
    }

    @Override
    public TypeAdapterRegistry getTypeAdapterRegistry() {
        return TypeAdapters.INSTANCE;
    }

}
