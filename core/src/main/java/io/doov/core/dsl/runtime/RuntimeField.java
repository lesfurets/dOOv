/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.core.dsl.runtime;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

import io.doov.core.CodeLookup;
import io.doov.core.CodeValuable;
import io.doov.core.FieldId;
import io.doov.core.FieldInfo;
import io.doov.core.dsl.DslField;
import io.doov.core.dsl.impl.DefaultCondition;

public class RuntimeField<B, R> implements DslField<R>, FieldInfo, Function<B, R>, BiConsumer<B, R> {

    private final List<PathMethod<Object, Object>> chain;
    private final PathMethod<Object, R> lastLink;
    private final FieldId id;
    private final String readable;
    private FieldId[] siblings;
    private Class<R> type;
    private boolean isCodeLookup;
    private boolean isCodeValuable;
    private boolean isTransient;

    public RuntimeField(List<PathMethod<Object, Object>> chain,
                        PathMethod<Object, R> lastLink,
                        FieldId id,
                        String readable,
                        FieldId[] siblings,
                        Class<R> type,
                        boolean isTransient) {
        this.chain = chain;
        this.lastLink = lastLink;
        this.id = id;
        this.readable = readable;
        this.siblings = siblings;
        this.type = type;
        this.isCodeLookup = CodeLookup.class.isAssignableFrom(type);
        this.isCodeValuable = CodeValuable.class.isAssignableFrom(type);
        this.isTransient = isTransient;
    }


    @Override
    public FieldId id() {
        return id;
    }

    @Override
    public String readable() {
        return readable;
    }

    @Override
    public DefaultCondition<R> getDefaultCondition() {
        return new DefaultCondition<>(this);
    }

    @Override
    public R apply(B b) {
        return get(b);
    }

    public R get(B model) {
        if (model == null) {
            return null;
        }
        Object next = model;
        for (PathMethod<Object, Object> m : chain) {
            next = m.get(next);
            if (next == null) {
                return null;
            }
        }
        return lastLink.get(next);
    }

    @Override
    public void accept(B b, R r) {
        set(b, r);
    }

    public void set(B model, R value) {
        if (model == null && value == null) {
            return;
        }
        Object next = model;
        for (PathMethod<Object, Object> method : chain) {
            Object o = method.get(next);
            if (o == null) {
                if (value != null) {
                    next = method.create(next);
                } else {
                    return;
                }
            } else {
                next = o;
            }
        }
        lastLink.set(next, value);
    }

    @SuppressWarnings("unchecked")
    public RuntimeField<B, R> register(List<RuntimeField<B, Object>> registry) {
        registry.add((RuntimeField<B, Object>) this);
        return this;
    }

    @Override
    public FieldId[] siblings() {
        return siblings;
    }

    @Override
    public Class<R> type() {
        return type;
    }

    @Override
    public Class<?>[] genericTypes() {
        throw new UnsupportedOperationException("Runtime model does not capture generic types");
    }

    @Override
    public boolean isCodeLookup() {
        return isCodeLookup;
    }

    @Override
    public boolean isCodeValuable() {
        return isCodeValuable;
    }

    @Override
    public boolean isTransient() {
        return isTransient;
    }

}
