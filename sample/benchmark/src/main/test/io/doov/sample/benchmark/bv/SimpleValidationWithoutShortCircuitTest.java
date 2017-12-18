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
package io.doov.sample.benchmark.bv;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.infra.Blackhole;

import io.doov.sample.benchmark.bv.SimpleValidationWithoutShortCircuit.ValidationState;

public class SimpleValidationWithoutShortCircuitTest {

    @Test
    public void test() {
        SimpleValidationWithoutShortCircuit bench = new SimpleValidationWithoutShortCircuit();
        bench.testSimpleBeanValidation(
                new ValidationState(),
                new Blackhole("Today's password is swordfish. " +
                        "I understand instantiating Blackholes directly is dangerous."));
    }

}