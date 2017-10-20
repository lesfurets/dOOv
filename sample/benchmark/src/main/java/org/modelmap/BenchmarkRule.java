/*
 * Copyright 2017 Courtanet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/
package org.modelmap;

import static io.doov.core.dsl.DSL.matchAll;
import static io.doov.sample.field.SampleFieldIdInfo.accountCountry;
import static io.doov.sample.field.SampleFieldIdInfo.accountLanguage;
import static io.doov.sample.field.SampleFieldIdInfo.accountPhoneNumber;
import static io.doov.sample.validation.Rules.REGISTRY_ACCOUNT;
import static io.doov.sample.validation.id.AccountRulesId.VALID_EMAIL;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.openjdk.jmh.annotations.GenerateMicroBenchmark;
import org.openjdk.jmh.logic.BlackHole;

import io.doov.core.dsl.DSL;
import io.doov.core.dsl.lang.*;
import io.doov.sample.model.*;
import io.doov.sample.validation.id.AccountRulesId;

public class BenchmarkRule {

    private static final ValidationRule ACCOUNT_VALID_COUNTRY_20 = DSL.when(matchAll(conditions(20))).validate();
    private static final ValidationRule ACCOUNT_VALID_COUNTRY_40 = DSL.when(matchAll(conditions(40))).validate();
    private static final ValidationRule ACCOUNT_VALID_COUNTRY_60 = DSL.when(matchAll(conditions(60))).validate();
    private static final ValidationRule ACCOUNT_VALID_COUNTRY_80 = DSL.when(matchAll(conditions(80))).validate();
    private static final ValidationRule ACCOUNT_VALID_COUNTRY_100 = DSL.when(matchAll(conditions(100))).validate();

    @GenerateMicroBenchmark
    public void valid_email(BlackHole blackhole) {
        boolean valid = executeOn(REGISTRY_ACCOUNT, VALID_EMAIL).isValid();
        if (blackhole != null) {
            blackhole.consume(valid);
        }
    }

    @GenerateMicroBenchmark
    public void valid_country(BlackHole blackhole) {
        boolean valid = executeOn(REGISTRY_ACCOUNT, VALID_EMAIL).isValid();
        if (blackhole != null) {
            blackhole.consume(valid);
        }
    }

    public void valid_country_20(BlackHole blackhole) {
        boolean valid = ACCOUNT_VALID_COUNTRY_20.executeOn(SampleModels.wrapper()).isValid();
        if (blackhole != null) {
            blackhole.consume(valid);
        }
    }

    public void valid_country_40(BlackHole blackhole) {
        boolean valid = ACCOUNT_VALID_COUNTRY_40.executeOn(SampleModels.wrapper()).isValid();
        if (blackhole != null) {
            blackhole.consume(valid);
        }
    }

    public void valid_country_60(BlackHole blackhole) {
        boolean valid = ACCOUNT_VALID_COUNTRY_60.executeOn(SampleModels.wrapper()).isValid();
        if (blackhole != null) {
            blackhole.consume(valid);
        }
    }

    public void valid_country_80(BlackHole blackhole) {
        boolean valid = ACCOUNT_VALID_COUNTRY_80.executeOn(SampleModels.wrapper()).isValid();
        if (blackhole != null) {
            blackhole.consume(valid);
        }
    }

    public void valid_country_100(BlackHole blackhole) {
        boolean valid = ACCOUNT_VALID_COUNTRY_100.executeOn(SampleModels.wrapper()).isValid();
        if (blackhole != null) {
            blackhole.consume(valid);
        }
    }

    private static StepCondition[] conditions(int count) {
        return IntStream.range(0, count)
                        .mapToObj(operand -> accountCountry().eq(Country.FR)
                                        .and(accountLanguage().eq(Language.FR))
                                        .and(accountPhoneNumber().startsWith("+33")))
                        .collect(Collectors.toList())
                        .toArray(new StepCondition[] {});
    }

    private Result executeOn(RuleRegistry registry, AccountRulesId id) {
        return registry.get(id).map(rule -> rule.executeOn(SampleModels.wrapper()))
                        .orElseThrow(IllegalArgumentException::new);
    }

}
