/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.core.virtual;

import java.util.stream.Stream;

public class VirtualFields implements VirtualFieldRegistry {

    public static final VirtualFields INSTANCE = new VirtualFields();

    @Override
    public Stream<VirtualField> stream() {
        return Stream.empty();
    }
}
