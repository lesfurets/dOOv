/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.sample.field;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.stream.Stream;

import io.doov.core.virtual.VirtualField;
import io.doov.core.virtual.VirtualFieldRegistry;

public class SampleVirtualField implements VirtualFieldRegistry {

    public static String sha256(String text) {
        if (text == null) {
            return null;
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private final VirtualField emailHash;

    public SampleVirtualField() {
        this.emailHash = new VirtualField(
                SampleFieldId.EMAIL::equals,
                key -> SampleFieldId.EMAIL_HASH,
                value -> sha256((String) value));
    }

    @Override
    public Stream<VirtualField> stream() {
        return Stream.of(emailHash);
    }
}
