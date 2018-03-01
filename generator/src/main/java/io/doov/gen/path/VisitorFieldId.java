/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.gen.path;

import java.util.Collections;
import java.util.List;

import io.doov.core.FieldId;
import io.doov.core.TagId;

public class VisitorFieldId implements FieldId {

    private final String code;
    private final int position;

    public VisitorFieldId(String code, int position) {
        this.code = code;
        this.position = position;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public int position() {
        return position;
    }

    @Override
    public List<TagId> tags() {
        return Collections.emptyList();
    }
}
