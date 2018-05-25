/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.sample.model;

import static io.doov.sample.field.SampleFieldId.EMAIL;
import static io.doov.sample.field.SampleFieldId.EMAIL_HASH;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import io.doov.core.BaseFieldModel;
import io.doov.core.FieldModel;
import io.doov.sample.field.SampleComputedField;
import io.doov.sample.field.SampleFieldInfo;
import io.doov.sample.wrapper.SampleModelWrapper;

public class SampleModelComputedFieldTest {

    @Test
    public void should_set_email_hash_when_creating_model_wrapper() {
        SampleModel sample = new SampleModel();
        sample.getAccount().setEmail("foo@bar.com");

        FieldModel model = new SampleModelWrapper(sample);

        assertThat(model.<String> get(EMAIL_HASH))
                .isNotNull()
                .isEqualTo(SampleComputedField.sha256("foo@bar.com"));
    }

    @Test
    public void should_update_email_hash_when_using_field_model() {
        FieldModel model = new BaseFieldModel(SampleFieldInfo.fieldInfos(), new SampleComputedField());

        should_update_email_hash_when_updating_email(model);
    }

    @Test
    public void should_update_email_hash_when_using_model_wrapper() {
        SampleModel sample = new SampleModel();
        FieldModel model = new SampleModelWrapper(sample);

        should_update_email_hash_when_updating_email(model);
    }

    private static void should_update_email_hash_when_updating_email(FieldModel model) {
        model.set(EMAIL, null);
        assertThat(model.<String> get(EMAIL_HASH)).isNull();

        model.set(EMAIL, "foo");
        assertThat(model.<String> get(EMAIL_HASH))
                .isNotNull()
                .isEqualTo(SampleComputedField.sha256("foo"));

        model.set(EMAIL, null);
        assertThat(model.<String> get(EMAIL_HASH)).isNull();

        model.set(EMAIL, "bar");
        assertThat(model.<String> get(EMAIL_HASH))
                .isNotNull()
                .isEqualTo(SampleComputedField.sha256("bar"));
    }
}
