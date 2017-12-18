/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.core.dsl.path;

@FunctionalInterface
public interface ReadMethodRef<T, R> {
    R call(T t);
}