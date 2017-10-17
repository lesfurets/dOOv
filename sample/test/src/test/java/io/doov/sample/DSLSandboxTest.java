/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.sample;

import static io.doov.sample.field.SampleFieldIdInfo.accountId;
import static io.doov.sample.field.SampleFieldIdInfo.accountPreferencesMail;
import static io.doov.sample.field.SampleFieldIdInfo.accountTimezone;
import static io.doov.sample.field.SampleFieldIdInfo.userBirthdate;
import static io.doov.sample.model.EmailType.ADMINISTRATOR;
import static io.doov.sample.model.EmailType.PRIVATE;
import static io.doov.sample.model.Timezone.ETC_GMT;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.EnumSet;

import org.junit.Test;

import io.doov.core.FieldModel;
import io.doov.core.dsl.DSL;
import io.doov.core.dsl.lang.ValidationRule;
import io.doov.sample.model.SampleModels;

public class DSLSandboxTest {

    private FieldModel model = SampleModels.wrapper();

    @Test
    public void sample1() {
        ValidationRule step = DSL.when(accountId().eq(1L)).validate().withMessage("incorrect account id");
        System.out.println(step.readable());
        assertThat(step.executeOn(model).isValid()).isFalse();
        assertThat(step.executeOn(model).getMessage()).isEqualTo("incorrect account id");
    }

    @Test
    public void sample2() {
        ValidationRule step = DSL.when(accountId().eq(1L)).validate();
        System.out.println(step.readable());
        assertThat(step.executeOn(model).isValid()).isFalse();
        assertThat(step.executeOn(model).getMessage()).isEqualTo("account id equals '1'");
    }

    @Test
    public void sample3() {
        ValidationRule step = DSL.when(accountId().eq(1L).not()).validate().withMessage("incorrect account id");
        System.out.println(step.readable());
        assertThat(step.executeOn(model).isValid()).isTrue();
        assertThat(step.executeOn(model).getMessage()).isNull();
    }

    @Test
    public void sample4() {
        ValidationRule step = DSL.when(userBirthdate().eq(LocalDate.of(1980, 8, 1))).validate()
                        .withMessage("valid birthdate is August 1, 1980");
        System.out.println(step.readable());
        assertThat(step.executeOn(model).isValid()).isTrue();
        assertThat(step.executeOn(model).getMessage()).isNull();
    }

    @Test
    public void sample5() {
        ValidationRule step = DSL.when(userBirthdate().between(LocalDate.of(1980, 1, 1), LocalDate.of(1980, 12, 31)))
                        .validate().withMessage("valid birthdate is in year 1980");
        System.out.println(step.readable());
        assertThat(step.executeOn(model).isValid()).isTrue();
        assertThat(step.executeOn(model).getMessage()).isNull();
    }

    @Test
    public void sample6() {
        ValidationRule step = DSL
                        .when(userBirthdate().between(LocalDate.of(1980, 1, 1), LocalDate.of(1980, 12, 31))
                                        .and(accountId().notEq(9L)).or((accountTimezone()).eq(ETC_GMT)))
                        .validate()
                        .withMessage("valid birthdate is in year 1980, " +
                                        "valid ID is different than 9, and " +
                                        "valid timezone is ETC_GMT");
        System.out.println(step.readable());
        assertThat(step.executeOn(model).isValid()).isTrue();
        assertThat(step.executeOn(model).getMessage()).isNull();
    }

    @Test
    public void sample7() {
        ValidationRule step = DSL.when(accountPreferencesMail().eq(EnumSet.of(ADMINISTRATOR, PRIVATE))).validate();
        System.out.println(step.readable());
        assertThat(step.executeOn(model).isValid()).isTrue();
        assertThat(step.executeOn(model).getMessage()).isNull();
    }

}
