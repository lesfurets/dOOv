package io.doov.sample.validation;

import org.junit.jupiter.api.Test;

import io.doov.sample.field.dsl.DslSampleModel.SampleModelRule;
import io.doov.sample.model.SampleModel;
import io.doov.sample.model.SampleModels;

public class RulesOCodeTest {
    SampleModel sample = SampleModels.sample();
    /**
     * @see RulesOld#validateAccount(io.doov.sample.model.User, io.doov.sample.model.Account,
     *      io.doov.sample.model.Configuration)
     */
    SampleModelRule demoRule = null;
    
    @Test
    public void test_account() {
        System.out.println("hello");
    }
}
