/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.core.computed;

import java.util.stream.Stream;

public class ComputedFields implements ComputedFieldRegistry {

    public static final ComputedFields INSTANCE = new ComputedFields();

    @Override
    public Stream<ComputedField> stream() {
        return Stream.empty();
    }
}
