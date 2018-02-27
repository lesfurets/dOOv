/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.core.dsl.field;

import java.util.List;

import io.doov.core.FieldId;
import io.doov.core.FieldInfo;

public class FieldInfoBuilder {

    public static FieldInfoBuilder fieldInfo() {
        return new FieldInfoBuilder();
    }

    private FieldId fieldId;
    private String readable;
    private Class<?> type;
    private boolean _transient = false;
    private boolean codeValuable = false;
    private boolean codeLookup = false;
    private Class<?>[] genericTypes = new Class<?>[] {};
    private FieldId[] siblings = new FieldId[] {};

    public FieldInfoBuilder fieldId(FieldId fieldId) {
        this.fieldId = fieldId;
        return this;
    }

    public FieldInfoBuilder readable(String readable) {
        this.readable = readable;
        return this;
    }

    public FieldInfoBuilder type(Class<?> type) {
        this.type = type;
        return this;
    }

    public FieldInfoBuilder _transient(boolean _transient) {
        this._transient = _transient;
        return this;
    }

    public FieldInfoBuilder codeValuable(boolean codeValuable) {
        this.codeValuable = codeValuable;
        return this;
    }

    public FieldInfoBuilder codeLookup(boolean codeLookup) {
        this.codeLookup = codeLookup;
        return this;
    }

    public FieldInfoBuilder genericTypes(Class<?>... genericTypes) {
        this.genericTypes = genericTypes;
        return this;
    }

    public FieldInfoBuilder siblings(FieldId... siblings) {
        this.siblings = siblings;
        return this;
    }

    public <T> FieldInfo build(List<FieldInfo> allFields) {
        DefaultFieldInfo<T> info = build();
        allFields.add(info);
        return info;
    }

    public <T> DefaultFieldInfo<T> build() {
        return new DefaultFieldInfo<>(fieldId, readable, type, _transient, codeValuable,
                            codeLookup, genericTypes, siblings);
    }

}
