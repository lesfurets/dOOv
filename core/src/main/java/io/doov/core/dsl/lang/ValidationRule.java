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
package io.doov.core.dsl.lang;

import io.doov.core.FieldId;
import io.doov.core.dsl.DslId;
import io.doov.core.dsl.DslModel;
import io.doov.core.dsl.meta.Metadata;
import io.doov.core.dsl.meta.SyntaxTree;

public interface ValidationRule<F extends FieldId & DslId> extends Readable, SyntaxTree {

    ValidationRule<F> withMessage(String message);

    ValidationRule<F> withShortCircuit(boolean shortCircuit);

    Result executeOn(DslModel<F> model);

    ValidationRule<F> registerOn(RuleRegistry<F> registry);

    String getMessage();
    
    Metadata getRootMetadata();

}
