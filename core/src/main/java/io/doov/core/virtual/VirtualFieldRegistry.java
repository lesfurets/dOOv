/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.core.virtual;

import java.util.function.Function;
import java.util.stream.Stream;

import io.doov.core.FieldId;

/**
 * Registry to register {@link FieldId} computed from another {@link FieldId}.
 * The {@link io.doov.core.FieldModel#set(FieldId, Object)} will call the {@link Function} in order to update another
 * {@link FieldId}.
 */
public interface VirtualFieldRegistry {

    /**
     * The stream of type adapters
     *
     * @return the stream of type adapters
     */
    Stream<VirtualField> stream();
}
